ALTER TABLE invoice_item
    DROP CONSTRAINT invoice_item_invoice_id_fkey;
ALTER TABLE invoice_transaction
    DROP CONSTRAINT invoice_transaction_invoice_id_fkey;
ALTER TABLE invoice_calculation_result
    DROP CONSTRAINT invoice_calculation_result_invoice_id_fkey;
ALTER TABLE invoice_calculation_result
    DROP CONSTRAINT invoice_calculation_result_calculation_id_fkey;

ALTER TABLE invoice_item
    ADD CONSTRAINT invoice_item_invoice_id_fkey_delete_cascade
        FOREIGN KEY (invoice_id)
            REFERENCES invoice (id)
            ON DELETE CASCADE;

ALTER TABLE invoice_transaction
    ADD CONSTRAINT invoice_transaction_invoice_id_fkey_delete_cascade
        FOREIGN KEY (invoice_id)
            REFERENCES invoice (id)
            ON DELETE CASCADE;

ALTER TABLE invoice_calculation_result
    ADD CONSTRAINT invoice_calculation_result_invoice_id_fkey_delete_cascade
        FOREIGN KEY (invoice_id)
            REFERENCES invoice (id)
            ON DELETE CASCADE;

ALTER TABLE invoice_calculation_result
    ADD CONSTRAINT invoice_calculation_result_calculation_id_fkey_delete_cascade
        FOREIGN KEY (calculation_id)
            REFERENCES invoice_calculation (id)
            ON DELETE CASCADE;