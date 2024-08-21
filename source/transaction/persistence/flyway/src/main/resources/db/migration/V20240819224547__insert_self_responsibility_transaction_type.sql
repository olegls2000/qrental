INSERT INTO transaction_type(name, description, transaction_kind_id)
VALUES ('self responsibility',
        'Transaction Type for the insurance self responsibility payment',
        (select id from transaction_kind where code = 'SR'))
ON CONFLICT DO NOTHING;

INSERT INTO transaction_type(name, description, transaction_kind_id)
VALUES ('self responsibility overpayment',
        'Transaction Type for the overpayment compensation',
        (select id from transaction_kind where code = 'P'))
ON CONFLICT DO NOTHING;