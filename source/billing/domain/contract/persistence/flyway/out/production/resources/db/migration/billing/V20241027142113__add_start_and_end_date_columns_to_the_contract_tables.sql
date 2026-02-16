ALTER TABLE contract
    ADD COLUMN date_start date not null default to_date('2000-01-01', 'YYYY-MM-DD');

UPDATE contract
SET date_start = created;

ALTER TABLE contract
    ADD COLUMN date_end date;