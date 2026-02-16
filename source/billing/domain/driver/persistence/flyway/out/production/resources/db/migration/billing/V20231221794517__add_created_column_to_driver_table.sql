alter table driver
    add column created_date date NOT NULL DEFAULT to_date('2023-01-01', 'YYYY-MM-DD');

