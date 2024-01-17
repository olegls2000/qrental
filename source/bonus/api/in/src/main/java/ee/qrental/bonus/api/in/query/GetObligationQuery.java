package ee.qrental.bonus.api.in.query;

import ee.qrental.bonus.api.in.response.ObligationResponse;

import java.util.List;

public interface GetObligationQuery {
    List<ObligationResponse> getAllByCalculationId(final Long calculationId);
}
