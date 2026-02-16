ALTER TABLE weekly_report
    ADD COLUMN q_firm_id INTEGER,
    ADD CONSTRAINT q_firm_id
        FOREIGN KEY (q_firm_id) REFERENCES firm (id);