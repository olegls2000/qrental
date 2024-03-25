package ee.qrental.bonus.api.in.query;


import ee.qrental.bonus.api.in.response.BonusCalculationResponse;
import java.util.List;

public interface GetBonusCalculationQuery {
    List<BonusCalculationResponse> getAll();

    BonusCalculationResponse getById(final Long id);

    Long getLastCalculatedQWeekId();
}
