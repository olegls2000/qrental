ALTER TABLE transaction_type
    ADD COLUMN code varchar not null default 'SET ME';
ALTER TABLE transaction_type
    ADD COLUMN name_eng varchar not null default 'SET ME';
ALTER TABLE transaction_type
    ADD COLUMN name_rus varchar not null default 'SET ME';
ALTER TABLE transaction_type
    ADD COLUMN name_est varchar not null default 'SET ME';
