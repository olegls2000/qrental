ALTER TABLE driver
    RENAME COLUMN driver_identifier TO bolt_driver_identifier;

ALTER TABLE driver
    RENAME COLUMN individual_identifier TO bolt_id;