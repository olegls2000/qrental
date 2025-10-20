select driver_id, q_week_id, count(*) from invoice group by driver_id, q_week_id ;

--## Remove Invoice Calculations for week:
delete
from invoice inv
where        inv.q_week_id in (select qw.id from q_week qw where qw.number = 40 and qw.year = 2025);

delete
from invoice_calculation invc
where invc.start_q_week_id in (select qw.id from q_week qw where qw.number = 40 and qw.year = 2025)
   or invc.end_q_week_id in (select qw.id from q_week qw where qw.number = 40 and qw.year = 2025);
-- invoice items must be deleted by Cascade!
-- invoice_calculation_result must be deleted by Cascade!
-- invoice_transaction must be deleted by Cascade!



DELETE
FROM billing.invoice inv
WHERE inv.q_week_id IN (
    SELECT qw.id
    FROM billing.q_week qw
    WHERE qw.year = 2025
      AND qw.number BETWEEN 29 AND 31
);

DELETE
FROM billing.invoice_calculation invc
WHERE invc.start_q_week_id IN (
    SELECT qw.id
    FROM billing.q_week qw
    WHERE qw.year = 2025
      AND qw.number BETWEEN 29 AND 31
)
   OR invc.end_q_week_id IN (
    SELECT qw.id
    FROM billing.q_week qw
    WHERE qw.year = 2025
      AND qw.number BETWEEN 29 AND 31
);



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