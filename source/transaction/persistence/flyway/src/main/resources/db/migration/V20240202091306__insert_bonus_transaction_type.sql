INSERT INTO transaction_type(name, description, transaction_kind_id)
VALUES ('bonus',
        'Transaction Type for the assigned bonus',
        (select id from transaction_kind where code = 'P'))
ON CONFLICT DO NOTHING;