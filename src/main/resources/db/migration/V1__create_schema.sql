CREATE EXTENSION IF NOT EXISTS "pgcrypto";

-- Enum de status de notificação
DO $$
BEGIN 
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'notification_status') THEN
        CREATE TYPE notification_status AS ENUM ('PENDING', 'SENT', 'FAILED');
    END IF;
END $$;

CREATE TABLE IF NOT EXISTS notification (
    id uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    
    -- Referência ao orçamento (quote_requests.id)
    external_reference_id uuid NOT NULL,
    
    -- Dados do destinatário
    recipient_email varchar(254) NOT NULL,
    recipient_name  varchar(150) NOT NULL,
    
    -- Dados do e-mail
    subject   text NOT NULL,
    body_html text NOT NULL,
    
    -- Situação da notificação
    status VARCHAR(30) NOT NULL DEFAULT 'PENDING',
    
    -- Debug de erro (opcional)
    error_message text NULL,
    
    -- Auditoria
    created_at timestamptz NOT NULL DEFAULT now(),
    updated_at timestamptz NOT NULL DEFAULT now(),
    deleted_at timestamptz NULL
);

CREATE INDEX IF NOT EXISTS idx_notification_external_reference
    ON notification (external_reference_id);

CREATE INDEX IF NOT EXISTS idx_notification_status
    ON notification (status);

CREATE INDEX IF NOT EXISTS idx_notification_deleted_at
    ON notification (deleted_at);