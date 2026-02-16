ALTER TABLE insurance_calculation
    ADD COLUMN q_week_id INTEGER,
    ADD CONSTRAINT q_week_id
        FOREIGN KEY (q_week_id) REFERENCES q_week (id);

ALTER TABLE insurance_calculation
    DROP COLUMN start_q_week_id;
ALTER TABLE insurance_calculation
    DROP COLUMN end_q_week_id;