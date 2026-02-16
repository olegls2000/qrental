ALTER TABLE transaction_type
    ADD COLUMN visible_for_ui boolean not null default true;