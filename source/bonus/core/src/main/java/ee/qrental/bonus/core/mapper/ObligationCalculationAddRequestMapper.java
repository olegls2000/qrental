package ee.qrental.bonus.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.bonus.domain.ObligationCalculation;
import ee.qrental.bonus.api.in.request.ObligationCalculationAddRequest;

import java.time.LocalDate;
import java.util.ArrayList;

public class ObligationCalculationAddRequestMapper
    implements AddRequestMapper<ObligationCalculationAddRequest, ObligationCalculation> {

  @Override
  public ObligationCalculation toDomain(ObligationCalculationAddRequest request) {
    final var actionDate = LocalDate.now();

    return ObligationCalculation.builder()
            .actionDate(actionDate)
            .qWeekId(request.getQWeekId())
            .results(new ArrayList<>())
            .comment(request.getComment())
            .build();
  }
}
