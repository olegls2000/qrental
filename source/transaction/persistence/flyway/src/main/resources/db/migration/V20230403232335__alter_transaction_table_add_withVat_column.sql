ALTER TABLE transaction
    ADD COLUMN with_vat BOOLEAN NOT NULL DEFAULT FALSE;