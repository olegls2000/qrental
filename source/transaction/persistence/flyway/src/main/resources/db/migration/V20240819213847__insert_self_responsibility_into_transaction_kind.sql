INSERT INTO transaction_kind(code, name, comment)
VALUES ('SR', 'self responsibility',
        'Transaction Type for the insurance self responsibility payment')
ON CONFLICT DO NOTHING;