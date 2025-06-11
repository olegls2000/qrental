INSERT INTO transaction_type(name, description, transaction_kind_id)
VALUES ('damage write off',
        'Transaction Type for the Insuranse Damage Write off',
        (select id from transaction_kind where code = 'FA')) ON CONFLICT DO NOTHING;