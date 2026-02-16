alter table weekly_report
    add column insurance_cases jsonb not null default '{}';
