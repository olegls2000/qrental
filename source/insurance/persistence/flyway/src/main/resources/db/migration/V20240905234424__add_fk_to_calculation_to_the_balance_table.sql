ALTER TABLE insurance_case_balance
    ADD COLUMN insurance_calculation_id INTEGER,
    ADD CONSTRAINT insurance_calculation_id
        FOREIGN KEY (insurance_calculation_id) REFERENCES insurance_calculation (id);