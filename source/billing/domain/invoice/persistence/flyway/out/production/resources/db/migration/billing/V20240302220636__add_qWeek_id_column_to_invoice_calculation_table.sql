ALTER TABLE invoice_calculation
    ADD COLUMN q_week_id integer
        constraint q_week_fk references q_week (id);