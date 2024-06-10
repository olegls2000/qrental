create table if not exists friendship
(
    id         serial primary key,
    driver_id  integer not null,
    FOREIGN KEY (driver_id) REFERENCES driver (id),
    friend_id  integer not null,
    FOREIGN KEY (friend_id) REFERENCES driver (id),
    date_start date    not null
);