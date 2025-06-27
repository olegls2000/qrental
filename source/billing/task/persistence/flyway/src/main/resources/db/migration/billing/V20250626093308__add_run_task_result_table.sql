CREATE TABLE task_run_result
(
    id                 SERIAL PRIMARY KEY,
    task_name          varchar   not null,
    status             varchar   not null,
    started_at         timestamp not null,
    duration_in_millis integer   not null
);