ALTER TABLE balance
    ADD constraint driver_q_week_u UNIQUE (q_week_id, driver_id);
