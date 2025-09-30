CREATE PROCEDURE GERAR_RELATORIO_DIARIO()
BEGIN
  DECLARE v_total_kwh DECIMAL(10,2);
  DECLARE v_localizacao VARCHAR(100);
  DECLARE v_relatorio TEXT DEFAULT '';
  DECLARE v_data_relatorio DATE;

  DECLARE done INT DEFAULT FALSE;
  DECLARE cur CURSOR FOR
    SELECT d.LOCALIZACAO, SUM(ce.CONSUMO_KWH) AS TOTAL_KWH
    FROM CONSUMO_ENERGIA ce
    JOIN DISPOSITIVO d ON ce.ID_DISPOSITIVO = d.ID_DISPOSITIVO
    WHERE DATE(ce.DATA_HORA) = v_data_relatorio
    GROUP BY d.LOCALIZACAO;

  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;

  -- Agora pode usar lógica condicional
  SELECT DATE(MIN(DATA_HORA)) INTO v_data_relatorio FROM CONSUMO_ENERGIA;

  IF v_data_relatorio IS NULL THEN
    SET v_relatorio = 'Relatório Diário de Consumo Energético - Nenhum dado disponível\n';
  ELSE
    SET v_relatorio = CONCAT('Relatório Diário de Consumo Energético - ', DATE_FORMAT(v_data_relatorio, '%d/%m/%Y'), '\n');

    OPEN cur;

    read_loop: LOOP
      FETCH cur INTO v_localizacao, v_total_kwh;
      IF done THEN
        LEAVE read_loop;
      END IF;

      SET v_relatorio = CONCAT(v_relatorio,
        'Localização: ', v_localizacao,
        ', Consumo: ', IFNULL(FORMAT(v_total_kwh, 2), '0.00'), ' kWh\n');
    END LOOP;

    CLOSE cur;
  END IF;

  SELECT CONCAT('Enviando relatório:\n', v_relatorio) AS Relatorio;

  INSERT INTO ALERTA_ENERGIA (
    ID_CONSUMO,
    MENSAGEM,
    DATA_ALERTA,
    STATUS
  ) VALUES (
    NULL,
    CONCAT('Relatório diário gerado: ', LEFT(v_relatorio, 400)),
    NOW(),
    'RESOLVIDO'
  );
END;
