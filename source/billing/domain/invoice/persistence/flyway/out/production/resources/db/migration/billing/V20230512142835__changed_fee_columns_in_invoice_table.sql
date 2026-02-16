ALTER TABLE invoice
    RENAME COLUMN fee TO current_week_fee;

ALTER table invoice
    ADD COLUMN previous_week_balance_fee NUMERIC(10, 2) not null default 0;