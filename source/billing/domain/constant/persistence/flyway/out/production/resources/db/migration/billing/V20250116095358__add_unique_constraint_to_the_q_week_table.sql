ALTER TABLE q_week
    ADD CONSTRAINT uq_week_number_year UNIQUE(year, number);