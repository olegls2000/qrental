package ee.qrent.billing.insurance.core.service.kasko;

import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.insurance.api.in.query.GetQKaskoQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class QKaskoQueryService implements GetQKaskoQuery {
  private static final Integer MIN_WEEKS_CONTRACT_DURATION_REQUIRED_FOR_Q_KASKO = 12;

  private final GetContractQuery contractQuery;

  @Override
  public boolean hasQKasko(final Long driverId, final Long qWeekId) {
    final var activeContractOnRequestedWeek =
        contractQuery.getActiveContractByDriverIdAndQWeekId(driverId, qWeekId);
    if (activeContractOnRequestedWeek == null) {

      return false;
    }
    if (activeContractOnRequestedWeek.getDuration()
        < MIN_WEEKS_CONTRACT_DURATION_REQUIRED_FOR_Q_KASKO) {

      return false;
    }

    return true;
  }
}
