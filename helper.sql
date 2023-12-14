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
from balance_calculation_result bcr
where bcr.balance_id in
      (select bl.id from balance bl where q_week_id in (select qw.id from q_week qw where qw.number = ?));

delete
from balance_transaction btr
where btr.balance_id in
      (select bl.id from balance bl where q_week_id in (select qw.id from q_week qw where qw.number = ?));

delete from balance bl where q_week_id in (select qw.id from q_week qw where qw.number = ?);

delete
from transaction tx
where tx.transaction_type_id in (select distinct(id)
                              from transaction_type
                              where name in ('fee replenish', 'compensation', 'fee debt'))
and tx.date;



delete
from balance_calculation_result;
delete
from balance_calculation;
delete
from balance;


delete
from invoice_item;
delete
from invoice_calculation_result;
delete
from invoice_transaction;
delete
from invoice_calculation;
delete
from invoice;

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



select bl.*
from balance bl
where driver_id in (select id from driver where first_name = 'Driver2')
  and q_week_id in (select id
                    from q_week
                    where number = :?);

select *
from rent_calculation rc
         JOIN q_week qw on rc.q_week_id = qw.id
order by qw.year, qw.number desc
limit 1;


select *
from balance;






