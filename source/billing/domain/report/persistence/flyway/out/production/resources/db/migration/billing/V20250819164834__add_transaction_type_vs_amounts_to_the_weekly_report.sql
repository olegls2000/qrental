alter table weekly_report
    add column transaction_types_vs_amount jsonb not null default '{}';
