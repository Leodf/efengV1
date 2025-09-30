CREATE OR REPLACE TRIGGER TRG_MONITORA_CONSUMO
FOR INSERT ON CONSUMO_ENERGIA
COMPOUND TRIGGER
    -- Declaração de variáveis globais para armazenar dados durante a inserção
    TYPE t_consumo_rec IS RECORD (
        id_consumo NUMBER,
        id_dispositivo NUMBER,
        consumo_kwh NUMBER,
        localizacao VARCHAR2(100),
        id_limite NUMBER
    );
    TYPE t_consumo_tab IS TABLE OF t_consumo_rec;
    g_consumo_data t_consumo_tab := t_consumo_tab();

    -- Fase AFTER EACH ROW: coleta os dados de cada linha inserida
    AFTER EACH ROW IS
    BEGIN
        -- Obtém a localização e o ID_LIMITE do dispositivo
        FOR rec IN (
            SELECT d.LOCALIZACAO, d.ID_LIMITE
            FROM DISPOSITIVO d
            WHERE d.ID_DISPOSITIVO = :NEW.ID_DISPOSITIVO
        ) LOOP
            g_consumo_data.EXTEND;
            g_consumo_data(g_consumo_data.LAST).id_consumo := :NEW.ID_CONSUMO;
            g_consumo_data(g_consumo_data.LAST).id_dispositivo := :NEW.ID_DISPOSITIVO;
            g_consumo_data(g_consumo_data.LAST).consumo_kwh := :NEW.CONSUMO_KWH;
            g_consumo_data(g_consumo_data.LAST).localizacao := rec.LOCALIZACAO;
            g_consumo_data(g_consumo_data.LAST).id_limite := rec.ID_LIMITE;
        END LOOP;
    END AFTER EACH ROW;

    -- Fase AFTER STATEMENT: processa os dados coletados após todas as inserções
    AFTER STATEMENT IS
    BEGIN
        FOR i IN 1..g_consumo_data.COUNT LOOP
            DECLARE
                v_limite NUMBER;
                v_total_dia NUMBER;
            BEGIN
                -- Verifica o limite de consumo associado ao dispositivo
                SELECT lc.LIMITE_KWH_DIA INTO v_limite
                FROM LIMITE_CONSUMO lc
                WHERE lc.ID_LIMITE = g_consumo_data(i).id_limite
                AND SYSDATE BETWEEN lc.DATA_INICIO AND NVL(lc.DATA_FIM, SYSDATE + 1);

                -- Calcula o consumo total do dia para a localização
                SELECT SUM(ce.CONSUMO_KWH) INTO v_total_dia
                FROM CONSUMO_ENERGIA ce
                JOIN DISPOSITIVO d ON ce.ID_DISPOSITIVO = d.ID_DISPOSITIVO
                WHERE d.LOCALIZACAO = g_consumo_data(i).localizacao
                AND TRUNC(ce.DATA_HORA) = TRUNC(SYSDATE);

                -- Se ultrapassar o limite, gera um alerta
                IF v_total_dia > v_limite THEN
                    INSERT INTO ALERTA_ENERGIA (
                        ID_ALERTA,
                        ID_CONSUMO,
                        MENSAGEM,
                        DATA_ALERTA,
                        STATUS
                    ) VALUES (
                        SEQ_ALERTA.NEXTVAL,
                        g_consumo_data(i).id_consumo,
                        'Consumo energético excedeu o limite em ' || g_consumo_data(i).localizacao || 
                        '. Consumo: ' || v_total_dia || ' kWh, Limite: ' || v_limite || ' kWh',
                        SYSTIMESTAMP,
                        'PENDENTE'
                    );
                END IF;
            EXCEPTION
                WHEN NO_DATA_FOUND THEN
                    NULL; -- Caso não haja limite definido, não gera alerta
            END;
        END LOOP;
        -- Limpa a coleção após o processamento
        g_consumo_data.DELETE;
    END AFTER STATEMENT;
END TRG_MONITORA_CONSUMO;

