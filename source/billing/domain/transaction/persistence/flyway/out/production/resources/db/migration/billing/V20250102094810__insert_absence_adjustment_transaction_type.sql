INSERT INTO transaction_type(name, description, transaction_kind_id)
VALUES ('absence adjustment',
        'Transaction Type for the reduction of Rent amount due to absence',
        (select id from transaction_kind where code = 'P'))
ON CONFLICT DO NOTHING;