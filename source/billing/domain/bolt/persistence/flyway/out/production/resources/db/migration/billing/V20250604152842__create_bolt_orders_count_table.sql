CREATE TABLE bolt_orders_count
(
    id                 SERIAL PRIMARY KEY,
    bolt_id            varchar not null,
    month              integer not null,
    year               integer not null,
    driver_id          integer not null,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    q_week_id          integer not null,
    FOREIGN KEY (q_week_id) REFERENCES q_week (id),
    month_orders_count int     not null,
    UNIQUE (q_week_id, driver_id)
);