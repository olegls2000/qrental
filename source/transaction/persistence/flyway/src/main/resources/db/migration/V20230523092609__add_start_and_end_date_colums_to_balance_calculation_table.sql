ALTER TABLE balance_calculation
    ADD COLUMN start_date date not null;

ALTER TABLE balance_calculation
    ADD COLUMN end_date date not null;