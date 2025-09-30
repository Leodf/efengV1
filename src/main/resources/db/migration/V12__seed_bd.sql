-- Bloco PL/SQL para realizar as inserções de forma controlada
DECLARE
    -- Variáveis para armazenar os IDs gerados
    v_id_limite_1 NUMBER;
    v_id_limite_2 NUMBER;
    v_id_limite_3 NUMBER;

    v_id_dispositivo_1 NUMBER;
    v_id_dispositivo_2 NUMBER;
    v_id_dispositivo_3 NUMBER;
    v_id_dispositivo_4 NUMBER;
    v_id_dispositivo_5 NUMBER;

    v_id_sensor_1 NUMBER;
    v_id_sensor_2 NUMBER;
    v_id_sensor_3 NUMBER;
    v_id_sensor_4 NUMBER;
    v_id_sensor_5 NUMBER;
    v_id_sensor_6 NUMBER;

    v_id_consumo_1 NUMBER;
    v_id_consumo_2 NUMBER;
    v_id_consumo_3 NUMBER;
    v_id_consumo_4 NUMBER;
    v_id_consumo_5 NUMBER;
    v_id_consumo_6 NUMBER;
    v_id_consumo_7 NUMBER;
    v_id_consumo_8 NUMBER;

    v_id_alerta_1 NUMBER;
    v_id_alerta_2 NUMBER;

BEGIN
    -- Inserções para LIMITE_CONSUMO
    v_id_limite_1 := SEQ_LIMITE.NEXTVAL;
    INSERT INTO LIMITE_CONSUMO (ID_LIMITE, LOCALIZACAO, LIMITE_KWH_DIA, DATA_INICIO, DATA_FIM) 
    VALUES (v_id_limite_1, 'Andar 1', 50, TO_DATE('2025-01-01', 'YYYY-MM-DD'), NULL);

    v_id_limite_2 := SEQ_LIMITE.NEXTVAL;
    INSERT INTO LIMITE_CONSUMO (ID_LIMITE, LOCALIZACAO, LIMITE_KWH_DIA, DATA_INICIO, DATA_FIM) 
    VALUES (v_id_limite_2, 'Andar 2', 100, TO_DATE('2025-01-01', 'YYYY-MM-DD'), NULL);
    
    v_id_limite_3 := SEQ_LIMITE.NEXTVAL;
    INSERT INTO LIMITE_CONSUMO (ID_LIMITE, LOCALIZACAO, LIMITE_KWH_DIA, DATA_INICIO, DATA_FIM) 
    VALUES (v_id_limite_3, 'Andar 3', 75, TO_DATE('2025-01-01', 'YYYY-MM-DD'), NULL);

    -- Inserções para DISPOSITIVO
    v_id_dispositivo_1 := SEQ_DISPOSITIVO.NEXTVAL;
    INSERT INTO DISPOSITIVO (ID_DISPOSITIVO, NOME, LOCALIZACAO, POTENCIA_WATTS, STATUS, ID_LIMITE)
    VALUES (v_id_dispositivo_1, 'Ar-condicionado Sala 1', 'Andar 1', 1200, 'ATIVO', v_id_limite_1);

    v_id_dispositivo_2 := SEQ_DISPOSITIVO.NEXTVAL;
    INSERT INTO DISPOSITIVO (ID_DISPOSITIVO, NOME, LOCALIZACAO, POTENCIA_WATTS, STATUS, ID_LIMITE)
    VALUES (v_id_dispositivo_2, 'Lâmpada Corredor', 'Andar 1', 60, 'ATIVO', v_id_limite_1);
    
    v_id_dispositivo_3 := SEQ_DISPOSITIVO.NEXTVAL;
    INSERT INTO DISPOSITIVO (ID_DISPOSITIVO, NOME, LOCALIZACAO, POTENCIA_WATTS, STATUS, ID_LIMITE)
    VALUES (v_id_dispositivo_3, 'Computador Recepção', 'Andar 1', 350, 'ATIVO', v_id_limite_1);
    
    v_id_dispositivo_4 := SEQ_DISPOSITIVO.NEXTVAL;
    INSERT INTO DISPOSITIVO (ID_DISPOSITIVO, NOME, LOCALIZACAO, POTENCIA_WATTS, STATUS, ID_LIMITE)
    VALUES (v_id_dispositivo_4, 'Ar-condicionado Sala 2', 'Andar 2', 1500, 'ATIVO', v_id_limite_2);
    
    v_id_dispositivo_5 := SEQ_DISPOSITIVO.NEXTVAL;
    INSERT INTO DISPOSITIVO (ID_DISPOSITIVO, NOME, LOCALIZACAO, POTENCIA_WATTS, STATUS, ID_LIMITE)
    VALUES (v_id_dispositivo_5, 'Servidor Principal', 'Andar 3', 800, 'ATIVO', v_id_limite_3);

    -- Inserções para SENSOR_IOT com a ordem correta das colunas (conforme V3 + V7)
    v_id_sensor_1 := SEQ_SENSOR.NEXTVAL;
    INSERT INTO SENSOR_IOT (ID_SENSOR, TIPO_SENSOR, DATA_INSTALACAO, ID_DISPOSITIVO)
    VALUES (v_id_sensor_1, 'PRESENCA', TO_DATE('2025-01-01', 'YYYY-MM-DD'), v_id_dispositivo_1);

    v_id_sensor_2 := SEQ_SENSOR.NEXTVAL;
    INSERT INTO SENSOR_IOT (ID_SENSOR, TIPO_SENSOR, DATA_INSTALACAO, ID_DISPOSITIVO)
    VALUES (v_id_sensor_2, 'CONSUMO', TO_DATE('2025-01-01', 'YYYY-MM-DD'), v_id_dispositivo_1);
    
    v_id_sensor_3 := SEQ_SENSOR.NEXTVAL;
    INSERT INTO SENSOR_IOT (ID_SENSOR, TIPO_SENSOR, DATA_INSTALACAO, ID_DISPOSITIVO)
    VALUES (v_id_sensor_3, 'TEMPERATURA', TO_DATE('2025-01-05', 'YYYY-MM-DD'), v_id_dispositivo_1);
    
    v_id_sensor_4 := SEQ_SENSOR.NEXTVAL;
    INSERT INTO SENSOR_IOT (ID_SENSOR, TIPO_SENSOR, DATA_INSTALACAO, ID_DISPOSITIVO)
    VALUES (v_id_sensor_4, 'CONSUMO', TO_DATE('2025-01-10', 'YYYY-MM-DD'), v_id_dispositivo_2);
    
    v_id_sensor_5 := SEQ_SENSOR.NEXTVAL;
    INSERT INTO SENSOR_IOT (ID_SENSOR, TIPO_SENSOR, DATA_INSTALACAO, ID_DISPOSITIVO)
    VALUES (v_id_sensor_5, 'CONSUMO', TO_DATE('2025-01-15', 'YYYY-MM-DD'), v_id_dispositivo_4);
    
    v_id_sensor_6 := SEQ_SENSOR.NEXTVAL;
    INSERT INTO SENSOR_IOT (ID_SENSOR, TIPO_SENSOR, DATA_INSTALACAO, ID_DISPOSITIVO)
    VALUES (v_id_sensor_6, 'TEMPERATURA', TO_DATE('2025-01-20', 'YYYY-MM-DD'), v_id_dispositivo_5);

    -- Inserções para CONSUMO_ENERGIA com a ordem correta das colunas (conforme V4 + V7)
    v_id_consumo_1 := SEQ_CONSUMO.NEXTVAL;
    INSERT INTO CONSUMO_ENERGIA (ID_CONSUMO, DATA_HORA, CONSUMO_KWH, ID_DISPOSITIVO)
    VALUES (v_id_consumo_1, SYSTIMESTAMP, 0.5, v_id_dispositivo_1);

    v_id_consumo_2 := SEQ_CONSUMO.NEXTVAL;
    INSERT INTO CONSUMO_ENERGIA (ID_CONSUMO, DATA_HORA, CONSUMO_KWH, ID_DISPOSITIVO)
    VALUES (v_id_consumo_2, SYSTIMESTAMP - INTERVAL '1' HOUR, 0.4, v_id_dispositivo_1);
    
    v_id_consumo_3 := SEQ_CONSUMO.NEXTVAL;
    INSERT INTO CONSUMO_ENERGIA (ID_CONSUMO, DATA_HORA, CONSUMO_KWH, ID_DISPOSITIVO)
    VALUES (v_id_consumo_3, SYSTIMESTAMP - INTERVAL '2' HOUR, 0.6, v_id_dispositivo_1);
    
    v_id_consumo_4 := SEQ_CONSUMO.NEXTVAL;
    INSERT INTO CONSUMO_ENERGIA (ID_CONSUMO, DATA_HORA, CONSUMO_KWH, ID_DISPOSITIVO)
    VALUES (v_id_consumo_4, SYSTIMESTAMP, 0.05, v_id_dispositivo_2);
    
    v_id_consumo_5 := SEQ_CONSUMO.NEXTVAL;
    INSERT INTO CONSUMO_ENERGIA (ID_CONSUMO, DATA_HORA, CONSUMO_KWH, ID_DISPOSITIVO)
    VALUES (v_id_consumo_5, SYSTIMESTAMP - INTERVAL '3' HOUR, 0.3, v_id_dispositivo_3);
    
    v_id_consumo_6 := SEQ_CONSUMO.NEXTVAL;
    INSERT INTO CONSUMO_ENERGIA (ID_CONSUMO, DATA_HORA, CONSUMO_KWH, ID_DISPOSITIVO)
    VALUES (v_id_consumo_6, SYSTIMESTAMP, 0.7, v_id_dispositivo_4);
    
    v_id_consumo_7 := SEQ_CONSUMO.NEXTVAL;
    INSERT INTO CONSUMO_ENERGIA (ID_CONSUMO, DATA_HORA, CONSUMO_KWH, ID_DISPOSITIVO)
    VALUES (v_id_consumo_7, SYSTIMESTAMP - INTERVAL '2' HOUR, 0.65, v_id_dispositivo_4);
    
    v_id_consumo_8 := SEQ_CONSUMO.NEXTVAL;
    INSERT INTO CONSUMO_ENERGIA (ID_CONSUMO, DATA_HORA, CONSUMO_KWH, ID_DISPOSITIVO)
    VALUES (v_id_consumo_8, SYSTIMESTAMP, 0.4, v_id_dispositivo_5);
    
    -- Inserções para ALERTA_ENERGIA
    v_id_alerta_1 := SEQ_ALERTA.NEXTVAL;
    INSERT INTO ALERTA_ENERGIA (ID_ALERTA, MENSAGEM, DATA_ALERTA, STATUS, ID_CONSUMO)
    VALUES (v_id_alerta_1, 'Consumo acima do limite no Ar-condicionado Sala 1', SYSTIMESTAMP, 'PENDENTE', v_id_consumo_3);
    
    v_id_alerta_2 := SEQ_ALERTA.NEXTVAL;
    INSERT INTO ALERTA_ENERGIA (ID_ALERTA, MENSAGEM, DATA_ALERTA, STATUS, ID_CONSUMO)
    VALUES (v_id_alerta_2, 'Consumo excessivo no Servidor Principal', SYSTIMESTAMP - INTERVAL '6' HOUR, 'RESOLVIDO', v_id_consumo_8);
END;