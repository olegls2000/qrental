ALTER TABLE transaction_type
    ADD COLUMN fee_able boolean not null default false;

ALTER TABLE transaction_type
    ADD COLUMN description_rus varchar;