ALTER TABLE contract RENAME COLUMN renter_name TO renter;
ALTER TABLE contract RENAME COLUMN renter_ceo_name TO renter_signer_name;
ALTER TABLE contract RENAME COLUMN renter_ceo_isikukood TO renter_signer_tax_number;
ALTER TABLE contract RENAME COLUMN driver_isikukood TO driver_tax_number;