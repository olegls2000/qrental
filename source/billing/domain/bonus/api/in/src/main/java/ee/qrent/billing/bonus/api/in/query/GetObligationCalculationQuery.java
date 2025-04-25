package ee.qrent.billing.bonus.api.in.query;


import ee.qrent.billing.bonus.api.in.response.ObligationCalculationResponse;
import java.util.List;

public interface GetObligationCalculationQuery {
    List<ObligationCalculationResponse> getAll();

    ObligationCalculationResponse getById(final Long id);

    Long getLastCalculatedQWeekId();
}
