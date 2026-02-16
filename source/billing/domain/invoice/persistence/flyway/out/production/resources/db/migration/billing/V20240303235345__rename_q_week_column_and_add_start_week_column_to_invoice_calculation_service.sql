ALTER TABLE invoice_calculation
    RENAME COLUMN q_week_id TO end_q_week_id;

ALTER TABLE invoice_calculation
    ADD COLUMN start_q_week_id integer
        constraint start_q_week_fk references q_week (id);