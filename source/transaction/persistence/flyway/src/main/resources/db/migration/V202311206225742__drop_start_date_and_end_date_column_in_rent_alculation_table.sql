alter table rent_calculation
    drop column start_date;
alter table rent_calculation
    drop column end_date;

ALTER TABLE rent_calculation
    ADD COLUMN q_week_id integer not null
        constraint q_week_fk references q_week (id);