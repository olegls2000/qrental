ALTER TABLE insurance_case
    ADD COLUMN start_q_week_id INTEGER,
    ADD CONSTRAINT start_q_week_id
        FOREIGN KEY (start_q_week_id) REFERENCES q_week (id);