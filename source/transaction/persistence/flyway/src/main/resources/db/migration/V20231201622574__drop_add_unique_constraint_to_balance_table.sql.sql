ALTER TABLE balance
    drop constraint driver_q_week_u;

ALTER TABLE balance
    add constraint driver_q_week_derived_u UNIQUE (q_week_id, driver_id, derived);
