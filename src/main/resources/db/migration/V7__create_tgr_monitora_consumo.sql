DELIMITER $$

CREATE TRIGGER trg_monitora_consumo
AFTER INSERT ON CONSUMO_ENERGIA
FOR EACH ROW
BEGIN
  DECLARE v_limite DECIMAL(10,2);
  DECLARE v_total_dia DECIMAL(10,2);
  DECLARE v_localizacao VARCHAR(100);
  DECLARE v_id_limite BIGINT;

  -- Buscar localização e ID_LIMITE do dispositivo
  SELECT LOCALIZACAO, ID_LIMITE
  INTO v_localizacao, v_id_limite
  FROM DISPOSITIVO
  WHERE ID_DISPOSITIVO = NEW.ID_DISPOSITIVO;

  -- Buscar limite diário de consumo
  SELECT LIMITE_KWH_DIA
  INTO v_limite
  FROM LIMITE_CONSUMO
  WHERE ID_LIMITE = v_id_limite
    AND CURDATE() BETWEEN DATA_INICIO AND IFNULL(DATA_FIM, CURDATE());

  -- Calcular consumo total do dia para a localização
  SELECT SUM(ce.CONSUMO_KWH)
  INTO v_total_dia
  FROM CONSUMO_ENERGIA ce
  JOIN DISPOSITIVO d ON ce.ID_DISPOSITIVO = d.ID_DISPOSITIVO
  WHERE d.LOCALIZACAO = v_localizacao
    AND DATE(ce.DATA_HORA) = CURDATE();

  -- Se ultrapassar o limite, gerar alerta
  IF v_total_dia > v_limite THEN
    INSERT INTO ALERTA_ENERGIA (
      ID_CONSUMO,
      MENSAGEM,
      DATA_ALERTA,
      STATUS
    ) VALUES (
      NEW.ID_CONSUMO,
      CONCAT('Consumo energético excedeu o limite em ', v_localizacao,
             '. Consumo: ', v_total_dia, ' kWh, Limite: ', v_limite, ' kWh'),
      NOW(),
      'PENDENTE'
    );
  END IF;
END$$

DELIMITER ;