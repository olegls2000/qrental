ALTER TABLE weekly_report_transaction
    DROP CONSTRAINT  weekly_report_transaction_transaction_id_fkey;

ALTER TABLE weekly_report_transaction
    ADD CONSTRAINT  weekly_report_transaction_transaction_id_fkey_delete_cascade
        FOREIGN KEY (transaction_id)
            REFERENCES transaction (id)
            ON DELETE CASCADE;