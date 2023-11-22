ALTER TABLE balance
    ADD COLUMN non_fee_able_amount integer NOT NULL;

ALTER TABLE balance
    RENAME COLUMN amount TO fee_able_amount;