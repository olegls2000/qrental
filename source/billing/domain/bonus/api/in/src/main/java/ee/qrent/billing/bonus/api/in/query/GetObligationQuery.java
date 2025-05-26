package ee.qrent.billing.bonus.api.in.query;

import ee.qrent.billing.bonus.api.in.response.ObligationResponse;
import java.math.BigDecimal;
import java.util.List;

public interface GetObligationQuery {
  List<ObligationResponse> getAllByCalculationId(final Long calculationId);

  BigDecimal getRawObligationAmountForCurrentWeekByDriverId(final Long driverId);

  ObligationResponse getObligationAmountForPreCurrentWeekByDriverId(final Long driverId);

  ObligationResponse getByDriverIdAndQWeekId(final Long driverId, final Long qWeekId);
}
