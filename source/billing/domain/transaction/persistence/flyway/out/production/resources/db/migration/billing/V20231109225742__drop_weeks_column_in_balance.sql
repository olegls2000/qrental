ALTER TABLE balance
    DROP COLUMN week_number;
ALTER TABLE balance
    DROP COLUMN year;
ALTER TABLE balance
    ADD COLUMN q_week_id int not null;

ALTER TABLE balance
    ADD CONSTRAINT q_wek_id_fk
        FOREIGN KEY (q_week_id)
            REFERENCES q_week (id);