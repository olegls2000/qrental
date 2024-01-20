package ee.qrental.bonus.api.in.query;

import ee.qrental.bonus.api.in.response.ObligationResponse;
import java.util.List;

public interface GetObligationQuery {
    List<ObligationResponse> getAllByCalculationId(final Long calculationId);

    ObligationResponse getForCurrentWeekByDriverId(final Long driverId);
    ObligationResponse getByQWeekIdAndDriverId(final Long qWeekId, final Long driverId);
}
