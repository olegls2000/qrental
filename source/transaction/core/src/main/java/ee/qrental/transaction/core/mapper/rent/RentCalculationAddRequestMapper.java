package ee.qrental.transaction.core.mapper.rent;

import ee.qrental.common.core.in.mapper.AddRequestMapper;
import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrental.transaction.domain.rent.RentCalculation;
import java.util.ArrayList;

public class RentCalculationAddRequestMapper
    implements AddRequestMapper<RentCalculationAddRequest, RentCalculation> {

  public RentCalculation toDomain(final RentCalculationAddRequest request) {
    final var actionDate = request.getActionDate();

    return RentCalculation.builder()
        .actionDate(actionDate)
        .startDate(actionDate)
        .endDate(actionDate.plusDays(6))
        .results(new ArrayList<>())
        .comment(request.getComment())
        .build();
  }
}
