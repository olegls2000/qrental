package ee.qrental.transaction.core.mapper.rent;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.transaction.api.in.request.rent.RentCalculationAddRequest;
import ee.qrental.transaction.domain.rent.RentCalculation;

import java.time.LocalDate;
import java.util.ArrayList;

public class RentCalculationAddRequestMapper
    implements AddRequestMapper<RentCalculationAddRequest, RentCalculation> {

  public RentCalculation toDomain(final RentCalculationAddRequest request) {
    final var actionDate = LocalDate.now();

    return RentCalculation.builder()
        .actionDate(actionDate)
        .qWeekId(request.getQWeekId())
        .results(new ArrayList<>())
        .comment(request.getComment())
        .build();
  }
}
