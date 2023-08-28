ALTER TABLE driver
    RENAME COLUMN iban1 TO company;
ALTER TABLE driver
    RENAME COLUMN iban2 TO reg_number;
ALTER TABLE driver
    RENAME COLUMN iban3 TO company_address;