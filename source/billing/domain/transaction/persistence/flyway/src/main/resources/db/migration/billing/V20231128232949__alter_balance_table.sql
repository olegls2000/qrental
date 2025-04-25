ALTER TABLE balance
    ADD COLUMN positive_amount numeric(10, 2);

ALTER TABLE balance
    ALTER COLUMN non_fee_able_amount TYPE numeric(10, 2);

ALTER TABLE balance
    RENAME COLUMN fee TO fee_amount;

ALTER TABLE balance
    ADD COLUMN derived boolean;