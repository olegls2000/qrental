create table if not exists weekly_report
(
    id                   serial primary key,
    q_week_id            integer        not null,
    FOREIGN KEY (q_week_id) REFERENCES q_week (id),
    driver_id            integer        not null,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    call_sign_id         integer        not null,
    FOREIGN KEY (call_sign_id) REFERENCES call_sign (id),
    car_id               integer        not null,
    FOREIGN KEY (car_id) REFERENCES car (id),
    q_firm_id            integer        not null,
    FOREIGN KEY (q_firm_id) REFERENCES firm (id),
    weeks_count_till_end integer        not null,
    deposit_obligation   numeric(10, 2) not null,
    deposit_paid         numeric(10, 2) not null,
    obligation_status    varchar        not null,
    comment              varchar
);