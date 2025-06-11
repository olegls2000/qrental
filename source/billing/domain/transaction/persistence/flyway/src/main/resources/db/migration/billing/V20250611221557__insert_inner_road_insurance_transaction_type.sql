INSERT INTO transaction_type(name, description, transaction_kind_id)
VALUES ('inner road insurance',
        'Transaction Type for the Inner Road Insurance weekly payments',
        (select id from transaction_kind where code = 'FA')) ON CONFLICT DO NOTHING;