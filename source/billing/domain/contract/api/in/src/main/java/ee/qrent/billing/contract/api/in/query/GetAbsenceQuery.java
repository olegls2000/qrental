package ee.qrent.billing.contract.api.in.query;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.contract.api.in.request.AbsenceUpdateRequest;
import ee.qrent.billing.contract.api.in.response.AbsenceResponse;

import java.util.List;

public interface GetAbsenceQuery extends BaseGetQuery<AbsenceUpdateRequest, AbsenceResponse> {
  List<AbsenceResponse> getActualAbsencesByDriverId(final Long driverId);

  Long getAbsencesDayCountsByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);
}
