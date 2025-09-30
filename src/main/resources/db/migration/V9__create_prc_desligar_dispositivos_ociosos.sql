CREATE OR REPLACE PROCEDURE DESLIGAR_DISPOSITIVOS_OCIOSOS
IS
    CURSOR c_dispositivos IS
        SELECT d.ID_DISPOSITIVO, d.NOME
        FROM DISPOSITIVO d
        JOIN SENSOR_IOT s ON d.ID_DISPOSITIVO = s.ID_DISPOSITIVO
        WHERE d.STATUS = 'ATIVO'
        AND s.TIPO_SENSOR = 'PRESENCA'
        AND NOT EXISTS (
            SELECT 1
            FROM CONSUMO_ENERGIA ce
            WHERE ce.ID_DISPOSITIVO = d.ID_DISPOSITIVO
            AND ce.DATA_HORA >= SYSDATE - INTERVAL '30' MINUTE
        );
BEGIN
    FOR dispositivo IN c_dispositivos LOOP
        UPDATE DISPOSITIVO
        SET STATUS = 'INATIVO'
        WHERE ID_DISPOSITIVO = dispositivo.ID_DISPOSITIVO;
        
        INSERT INTO ALERTA_ENERGIA (
            ID_ALERTA,
            ID_CONSUMO,
            MENSAGEM,
            DATA_ALERTA,
            STATUS
        ) VALUES (
            SEQ_ALERTA.NEXTVAL,
            NULL,
            'Dispositivo ' || dispositivo.NOME || ' foi desligado automaticamente por inatividade.',
            SYSTIMESTAMP,
            'RESOLVIDO'
        );
    END LOOP;
    COMMIT;
END;
