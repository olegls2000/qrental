ALTER TABLE driver
    RENAME COLUMN driver_identifier TO bolt_driver_identifier;

ALTER TABLE driver
    RENAME COLUMN individual_identifier TO bolt_id;

ALTER TABLE driver
    ADD CONSTRAINT bolt_id_unique UNIQUE (bolt_id);