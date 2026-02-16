INSERT INTO transaction_type(name, description, comment, negative)
VALUES ('weekly rent',
        'Transaction Type for weekly rent amount',
        'Monday transaction for the Weekly Rent preapyment',
        true)
ON CONFLICT DO NOTHING;

INSERT INTO transaction_type(name, description, comment, negative)
VALUES ('no label fine',
        'Transaction Type for weekly No Labele fine amount',
        'Monday transaction for the Weekly No Label fine preapyment',
        true)
ON CONFLICT DO NOTHING;