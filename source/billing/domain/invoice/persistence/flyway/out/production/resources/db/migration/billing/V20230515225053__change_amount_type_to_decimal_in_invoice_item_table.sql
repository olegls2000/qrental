ALTER TABLE invoice_item
    DROP COLUMN amount;

ALTER TABLE invoice_item
    ADD COLUMN amount NUMERIC(10, 2) not null default 0.00;