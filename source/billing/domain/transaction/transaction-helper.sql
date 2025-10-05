delete
from rent_calculation_result
where rent_calculation_id in (select id
                              from rent_calculation
                              where q_week_id in
                                    (select qw.id from q_week qw where qw.year = 2024 and qw.number = 26));
delete
from rent_calculation
where q_week_id in (select qw.id from q_week qw where qw.year = 2024 and qw.number = 26);;
delete
from transaction
where transaction_type_id in (select distinct(id)
                              from transaction_type
                              where name in ('weekly rent', 'no label fine'));