ALTER TABLE absence
    ADD COLUMN date_start date not null default to_date('2000-01-01', 'YYYY-MM-DD');

ALTER TABLE absence
    ADD COLUMN date_end date;

ALTER TABLE absence
    ADD COLUMN with_car boolean not null default true;

ALTER TABLE absence
    ADD COLUMN reason varchar not null default 'VACATION';

ALTER TABLE absence
    DROP COLUMN q_week_id;