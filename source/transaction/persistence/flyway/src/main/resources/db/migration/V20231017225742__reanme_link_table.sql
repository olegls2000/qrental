ALTER TABLE link
    RENAME TO car_link;

ALTER TABLE rent_calculation
    ADD COLUMN start_date date ;

ALTER TABLE rent_calculation
    ADD COLUMN end_date date ;

ALTER TABLE rent_calculation_result
    RENAME COLUMN link_id TO car_link_id;

ALTER TABLE rent_calculation_result
    RENAME COLUMN calculation_id TO rent_calculation_id;