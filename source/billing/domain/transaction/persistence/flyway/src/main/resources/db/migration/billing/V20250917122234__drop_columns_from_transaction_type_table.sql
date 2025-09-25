alter table transaction_type
    drop column description;

alter table transaction_type_aud
    drop column description;

alter table transaction_type
    drop column invoice_name;

alter table transaction_type_aud
    drop column invoice_name;
