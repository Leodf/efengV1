CREATE OR REPLACE PROCEDURE GERAR_RELATORIO_DIARIO
IS
    v_total_kwh NUMBER;
    v_localizacao VARCHAR2(100);
    v_relatorio VARCHAR2(4000);
    v_data_relatorio DATE;
BEGIN
    -- Obtém a data do primeiro registro de CONSUMO_ENERGIA para usar como base do relatório
    SELECT TRUNC(MIN(DATA_HORA))
    INTO v_data_relatorio
    FROM CONSUMO_ENERGIA;

    -- Se não houver registros, define uma mensagem padrão
    IF v_data_relatorio IS NULL THEN
        v_relatorio := 'Relatório Diário de Consumo Energético - Nenhum dado disponível' || CHR(10);
    ELSE
        v_relatorio := 'Relatório Diário de Consumo Energético - ' || TO_CHAR(v_data_relatorio, 'DD/MM/YYYY') || CHR(10);
        
        -- Gera o relatório com base na data dos registros
        FOR rec IN (
            SELECT d.LOCALIZACAO, SUM(ce.CONSUMO_KWH) as TOTAL_KWH
            FROM CONSUMO_ENERGIA ce
            JOIN DISPOSITIVO d ON ce.ID_DISPOSITIVO = d.ID_DISPOSITIVO
            WHERE TRUNC(ce.DATA_HORA) = v_data_relatorio
            GROUP BY d.LOCALIZACAO
        ) LOOP
            v_relatorio := v_relatorio || 'Localização: ' || rec.LOCALIZACAO || 
                          ', Consumo: ' || NVL(TO_CHAR(rec.TOTAL_KWH, 'FM99990.00'), '0.00') || ' kWh' || CHR(10);
        END LOOP;
    END IF;
    
    -- Simula envio de e-mail (em ambiente real, usaria UTL_MAIL ou similar)
    DBMS_OUTPUT.PUT_LINE('Enviando relatório: ' || v_relatorio);
    
    INSERT INTO ALERTA_ENERGIA (
        ID_ALERTA,
        ID_CONSUMO,
        MENSAGEM,
        DATA_ALERTA,
        STATUS
    ) VALUES (
        SEQ_ALERTA.NEXTVAL,
        NULL,
        'Relatório diário gerado: ' || SUBSTR(v_relatorio, 1, 400),
        SYSTIMESTAMP,
        'RESOLVIDO'
    );
END;
