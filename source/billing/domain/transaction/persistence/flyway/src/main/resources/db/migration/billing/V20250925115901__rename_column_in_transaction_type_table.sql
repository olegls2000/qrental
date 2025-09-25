alter table transaction_type
    rename column name to ui_name;

alter table transaction_type_aud
    rename column name to ui_name;
