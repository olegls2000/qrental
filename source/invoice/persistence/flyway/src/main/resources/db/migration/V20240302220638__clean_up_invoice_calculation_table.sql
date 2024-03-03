ALTER TABLE invoice_calculation
    ALTER COLUMN q_week_id SET NOT NULL;

alter table invoice_calculation
    drop column IF EXISTS start_date;
alter table invoice_calculation
    drop column IF EXISTS end_date;