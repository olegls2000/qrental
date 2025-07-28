alter table weekly_report
    add column balance_amount_sunday numeric not null default 1;

alter table weekly_report
    add column balance_Amount_at_calculation_moment numeric not null default 1;
