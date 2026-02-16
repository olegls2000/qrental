ALTER TABLE invoice
    ADD COLUMN q_week_id integer not null;

ALTER TABLE invoice
    ADD FOREIGN KEY (q_week_id) REFERENCES q_week (id);