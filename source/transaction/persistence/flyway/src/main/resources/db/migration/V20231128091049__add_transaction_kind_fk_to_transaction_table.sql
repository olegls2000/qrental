ALTER TABLE transaction
    ADD COLUMN transaction_kind_id INT
        CONSTRAINT transaction_kind_fk REFERENCES transaction_kind (id)
            ON UPDATE CASCADE ON DELETE CASCADE;
