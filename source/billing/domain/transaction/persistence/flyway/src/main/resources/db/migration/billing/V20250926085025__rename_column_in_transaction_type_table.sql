alter table transaction_type
    rename column visible_for_ui to ui_visible;

alter table transaction_type_aud
    rename column visible_for_ui to ui_visible;
