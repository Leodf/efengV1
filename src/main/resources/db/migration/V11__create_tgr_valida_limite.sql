CREATE OR REPLACE TRIGGER TRG_VALIDA_LIMITE
BEFORE INSERT OR UPDATE ON LIMITE_CONSUMO
FOR EACH ROW
BEGIN
    -- Garante que o limite seja positivo
    IF :NEW.LIMITE_KWH_DIA <= 0 THEN
        RAISE_APPLICATION_ERROR(-20002, 
            'O limite de consumo deve ser maior que zero');
    END IF;
END;
