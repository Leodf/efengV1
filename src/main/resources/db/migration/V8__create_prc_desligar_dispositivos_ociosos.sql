DELIMITER $$

CREATE PROCEDURE DESLIGAR_DISPOSITIVOS_OCIOSOS()
BEGIN
  DECLARE done INT DEFAULT FALSE;
  DECLARE v_id_dispositivo BIGINT;
  DECLARE v_nome VARCHAR(100);

  -- Cursor para dispositivos ativos com sensor de presença e sem consumo nos últimos 30 minutos
  DECLARE c_dispositivos CURSOR FOR
    SELECT d.ID_DISPOSITIVO, d.NOME
    FROM DISPOSITIVO d
    JOIN SENSOR_IOT s ON d.ID_DISPOSITIVO = s.ID_DISPOSITIVO
    WHERE d.STATUS = 'ATIVO'
      AND s.TIPO_SENSOR = 'PRESENCA'
      AND NOT EXISTS (
        SELECT 1
        FROM CONSUMO_ENERGIA ce
        WHERE ce.ID_DISPOSITIVO = d.ID_DISPOSITIVO
          AND ce.DATA_HORA >= NOW() - INTERVAL 30 MINUTE
      );

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  OPEN c_dispositivos;

  read_loop: LOOP
    FETCH c_dispositivos INTO v_id_dispositivo, v_nome;
    IF done THEN
      LEAVE read_loop;
    END IF;

    -- Atualiza status do dispositivo
    UPDATE DISPOSITIVO
    SET STATUS = 'INATIVO'
    WHERE ID_DISPOSITIVO = v_id_dispositivo;

    -- Insere alerta
    INSERT INTO ALERTA_ENERGIA (
      ID_CONSUMO,
      MENSAGEM,
      DATA_ALERTA,
      STATUS
    ) VALUES (
      NULL,
      CONCAT('Dispositivo ', v_nome, ' foi desligado automaticamente por inatividade.'),
      NOW(),
      'RESOLVIDO'
    );
  END LOOP;

  CLOSE c_dispositivos;
END$$

DELIMITER ;