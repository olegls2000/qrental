package ee.qrental.bonus.api.in.query;


import ee.qrental.bonus.api.in.response.ObligationCalculationResponse;
import java.util.List;

public interface GetObligationCalculationQuery {
    List<ObligationCalculationResponse> getAll();

    ObligationCalculationResponse getById(final Long id);

    Long getLastCalculatedQWeekId();
}
