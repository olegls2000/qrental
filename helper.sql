--#### CLEAN-UPs
--## Q Week
delete
from q_week;


--------------------------------------------------------------------------------------------------------
--## Obligation Calculations:

delete
from obligation_calculation_result ocr
where ocr.obligation_calculation_id in
      (select id
       from obligation_calculation
       where q_week_id in
             (select id
              from q_week
              where year = 2024
                and number = 23));

delete
from obligation ob
where ob.q_week_id in (select id from q_week where year = 2024 and number = 23);

delete
from obligation_calculation
where q_week_id in (select id from q_week where year = 2024 and number = 23);

--------------------------------------------------------------------------------------------------------

--## Bonus Calculations:
delete
from bonus_calculation_result
where bonus_calculation_id in (select id
                               from bonus_calculation
                               where q_week_id in
                                     (select qw.id from q_week qw where qw.number = 26));
delete
from bonus_calculation
where q_week_id in (select qw.id from q_week qw where qw.number = 26);

delete
from transaction
where transaction_type_id in (select distinct(id)
                              from transaction_type
                              where name in ('bonus'));


--------------------------------------------------------------------------------------------------------
--## Rent Calculations:
delete
from rent_calculation_result;
delete
from rent_calculation;
delete
from transaction
where transaction_type_id in (select distinct(id)
                              from transaction_type
                              where name in ('weekly rent', 'no label fine'));

--##BALANCE CALCULATIONS:
--------------------------------------------------------------------------------------------------------
--## Remove Balance Calculations for week:
delete
from balance_calculation_result bcr
where bcr.balance_id in
      (select bl.id
       from balance bl
       where q_week_id in (select qw.id from q_week qw where qw.number = 39 and qw.year = 2024));

delete
from balance_transaction btr
where btr.balance_id in
      (select bl.id
       from balance bl
       where q_week_id in (select qw.id from q_week qw where qw.number = 39 and qw.year = 2024));

delete
from balance bl
where q_week_id in (select qw.id from q_week qw where qw.number = 39 and qw.year = 2024);

delete
from transaction tx
where tx.transaction_type_id in (select distinct(id)
                                 from transaction_type
                                 where name in ('fee replenish', 'compensation', 'fee debt'))
  and tx.date >= '2024-06-03'::date
  and tx.date <= '2024-06-09'::date;

--## Remove all Balance Calculations:
delete
from balance_calculation_result;
delete
from balance_calculation;
delete
from balance;
select *
from balance;
-------------------------------------------------

--##INVOICE CALCULATIONS:
------------------------------------------------------------------------------------------------------------------

--## Remove Invoice Calculations for week:
delete
from invoice inv
where inv.q_week_id in (select qw.id from q_week qw where qw.number = 31 and qw.year = 2024);

delete
from invoice_calculation invc
where invc.start_q_week_id in (select qw.id from q_week qw where qw.number = 31 and qw.year = 2024)
   or invc.end_q_week_id in (select qw.id from q_week qw where qw.number = 31 and qw.year = 2024);
-- invoice items must be deleted by Cascade!
-- invoice_calculation_result must be deleted by Cascade!
-- invoice_transaction must be deleted by Cascade!


--## Remove all Invoice Calculations:
delete
from invoice_calculation_result;
delete
from invoice_transaction;
delete
from invoice_calculation;
delete
from invoice inv;;
delete
from invoice_item;
--------------------------------------------------

--------------------------------------------------------------------------------------------------------
--## Insurance Calculations:

delete
from insurance_case_balance_x_transaction icbt;
delete
from insurance_case_balance icb;
delete
from insurance_calculation;

select *
from transaction
where transaction_type_id in (select distinct(id)
                              from transaction_type
                              where name in ('damage payment', 'self responsibility payment'));
--#### SELECTS:

--## Rent Calculations:
--#Select all transactions created by the Rent Calculation process:
select *
from transaction
where transaction_type_id in (select distinct(id)
                              from transaction_type
                              where name in ('weekly rent', 'no label fine'));



insert into q_week (year, number, description)
values (2023, 1, '30-Oct ... 05-Nov');

select qw.*
from q_week qw
where qw.year <= (select year from q_week where id = 26)
  and qw.number <= (select number from q_week where id = 26)
;

select bl.*
from balance bl
         LEFT JOIN q_week qw on bl.q_week_id = qw.id
where bl.driver_id = :driverId
  and qw.year = :year
  and qw.number = :weekNumber;



select bl.*
from balance bl
where driver_id in (select id from driver where id = 172)
  and q_week_id in (select id
                    from q_week
                    where number = 49);

select *
from rent_calculation rc
         JOIN q_week qw on rc.q_week_id = qw.id
order by qw.year, qw.number desc
limit 1;


select *
from invoice;

UPDATE driver
SET created_date = '2023-01-01';

SELECT qw.*
FROM q_week qw
WHERE (
    qw.year = (SELECT year FROM q_week WHERE id = 67)
        AND qw.number < (SELECT number FROM q_week WHERE id = 67)
    )
   OR (qw.year < (SELECT year FROM q_week WHERE id = 67));


SELECT qw.*
FROM q_week qw
WHERE ((qw.year = (SELECT year FROM q_week WHERE id = 59) AND
        qw.number > (SELECT number FROM q_week WHERE id = 59)))
   OR (qw.year > (SELECT year FROM q_week WHERE id = 59))
    AND
      ((qw.year = (SELECT year FROM q_week WHERE id = 67)
          AND qw.number < (SELECT number FROM q_week WHERE id = 67)) OR
       (qw.year < (SELECT year FROM q_week WHERE id = 67)));

select *
from role;

select count(*)
from driver;

SELECT qw.*
FROM q_week qw
WHERE qw.year = (SELECT year FROM q_week WHERE id = :startWeekId) AND
      qw.number > (SELECT number FROM q_week WHERE id = :startWeekId)
   OR qw.year > (SELECT year FROM q_week WHERE id = :startWeekId)
    AND
      qw.year = (SELECT year FROM q_week WHERE id = :endWeekId) AND
      qw.number < (SELECT number FROM q_week WHERE id = :endWeekId)
   OR qw.year < (SELECT year FROM q_week WHERE id = :startWeekId);

select qw.*
from q_week qw
WHERE (qw.year * 100 + qw.number) >
      (select (q_week.year * 100 + q_week.number) from q_week WHERE q_week.id = :startWeekId)
  AND (qw.year * 100 + qw.number) <
      (select (q_week.year * 100 + q_week.number) from q_week WHERE q_week.id = :endWeekId);

select *
from q_week;

--Total DB clean up:
delete
from firm_link
where driver_id not in (28, 103, 95, 5);
delete
from invoice_transaction
where transaction_id in (select id from transaction where driver_id not in (28, 103, 95, 5));
delete
from bonus_calculation_result
where transaction_id in (select id from transaction where driver_id not in (28, 103, 95, 5));
delete
from transaction
where driver_id not in (28, 103, 95, 5);
delete
from call_sign_link
where driver_id not in (28, 103, 95, 5);
delete
from invoice
where driver_id not in (28, 103, 95, 5);
delete
from car_link
where driver_id not in (28, 103, 95, 5);
delete
from friendship
where driver_id not in (28, 103, 95, 5);
delete
from contract
where driver_id not in (28, 103, 95, 5);
delete
from authorization_bolt
where driver_id not in (28, 103, 95, 5);
delete
from driver
where id not in (28, 103, 95, 5);


-- Detect duplicates:
SELECT name, COUNT(*)
FROM transaction_type
GROUP BY name
HAVING COUNT(*) > 1;


--Insert User:
insert into user_account (username, email, first_name, last_name, password)
values ('user',
        'user@gmail.com', 'first users name', 'last users name',
        '$2a$10$4l78FBqvZu90KAD7R8F1EuxLkjmzMITgGdFZOZ48rN5nW8RcABkgq') ;

select  from transaction_aud ta
LEFT JOIN revinfo ri ON ta.rev = ri.rev
--where ta.id = 244466
;