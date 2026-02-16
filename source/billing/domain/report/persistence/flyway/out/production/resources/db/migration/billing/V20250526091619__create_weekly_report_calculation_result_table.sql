create table if not exists weekly_report_calculation_result
(
    id               serial primary key,
    calculation_id   integer not null,
    FOREIGN KEY (calculation_id) REFERENCES weekly_report_calculation (id),
    weekly_report_id integer not null,
    FOREIGN KEY (weekly_report_id) REFERENCES weekly_report (id)
);