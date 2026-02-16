create table if not exists q_email_notification
(
    id      serial primary key,
    sent_at timestamp not null,
    type    varchar   not null,
    payload jsonb     not null
);