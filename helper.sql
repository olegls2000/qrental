--#### CLEAN-UPs
--## Q Week
delete
from q_week;

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

--------------------------------------------------------------------------------------------------------
--## Balance Calculations:
delete
from balance_calculation_result;
delete
from balance_calculation;
delete
from balance_transaction;
delete
from balance;
delete
from transaction
where transaction_type_id in (select distinct(id)
                              from transaction_type
                              where name in ('fee replenish', 'compensation', 'fee debt'));

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



select bl.* from balance bl where driver_id = 1;

            --where bl.driver_id = :driverId and bl.q_week_id = :qWeekId;







