ALTER TABLE balance
    DROP COLUMN amount;

ALTER TABLE balance
    ADD COLUMN amount NUMERIC(10, 2) not null default 0.00;