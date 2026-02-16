ALTER TABLE transaction_type
    ADD COLUMN invoice_included boolean not null default false;