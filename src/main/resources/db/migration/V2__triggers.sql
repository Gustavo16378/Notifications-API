-- TRIGGERS PARA TABELA notification
-- Mantém o campo updated_at sempre atualizado em qualquer UPDATE

-- Função que atualiza o updated_at antes de cada UPDATE
CREATE OR REPLACE FUNCTION set_notification_updated_at()
RETURNS trigger AS $$
BEGIN
    NEW.updated_at := now();
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger que chama a função para cada linha atualizada
CREATE TRIGGER trg_notification_set_updated_at
BEFORE UPDATE ON notification
FOR EACH ROW
EXECUTE FUNCTION set_notification_updated_at();