INSERT INTO transaction_type(name, description, comment, negative)
VALUES ('fee replenish',
        'Transaction Type is dedicated to replenish Fee Debt in Fee Balance',
        'Opposite to fee-debt Transaction Type',
        false);

INSERT INTO transaction_type(name, description, comment, negative)
VALUES ('compensation',
        'Transaction Type is dedicated to compensate Fee Debt replenishment',
        'Opposite to fee-debt Transaction Type',
        true);
