INSERT INTO notification (
    external_reference_id,
    recipient_email,
    recipient_name,
    subject,
    body_html,
    status,
    error_message,
    created_at,
    updated_at
) VALUES 
(
    '00000000-0000-0000-0000-000000000001',
    'cliente1@example.com',
    'Cliente Um',
    'Seu orçamento foi criado',
    '<h1>Olá, Cliente Um!</h1><p>Seu orçamento foi criado com sucesso.</p>',
    'PENDING',
    NULL,
    now(),
    now()
),
(
    '00000000-0000-0000-0000-000000000002',
    'cliente2@example.com',
    'Cliente Dois',
    'Seu orçamento foi atualizado',
    '<h1>Olá, Cliente Dois!</h1><p>Seu orçamento foi atualizado recentemente.</p>',
    'SENT',
    NULL,
    now() - interval '1 day',
    now() - interval '1 day'
),
(
    '00000000-0000-0000-0000-000000000003',
    'cliente3@example.com',
    'Cliente Três',
    'Falha ao enviar seu orçamento',
    '<h1>Olá, Cliente Três!</h1><p>Tivemos um problema ao enviar seu orçamento. Tente novamente mais tarde.</p>',
    'FAILED',
    'Simulação de erro de envio (SMTP indisponível).',
    now() - interval '2 days',
    now() - interval '2 days'
);