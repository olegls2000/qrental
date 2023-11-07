--#### CLEAN-UPs

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



--#### SELECTS:

--## Rent Calculations:
--#Select all transactions created by the Rent Calculation process:
select *
from transaction
where transaction_type_id in (select distinct(id)
                              from transaction_type
                              where name in ('weekly rent', 'no label fine'));






