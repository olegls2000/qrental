delete
from weekly_report_transaction;
delete
from weekly_report_calculation_result;
delete
from weekly_report_calculation wrc;
delete
from weekly_report;

--update driver set  need_report  = false;

update weekly_report set type = 'TUESDAY_REPORT' where type = 'MONDAY_REPORT';
update weekly_report_calculation  set type = 'TUESDAY_REPORT' where type = 'MONDAY_REPORT';