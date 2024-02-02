package ee.qrental.bonus.core.mapper;

import ee.qrental.bonus.api.in.request.BonusCalculationAddRequest;
import ee.qrental.bonus.domain.BonusCalculation;
import ee.qrental.common.core.in.mapper.AddRequestMapper;
import java.time.LocalDate;
import java.util.ArrayList;

public class BonusCalculationAddRequestMapper
    implements AddRequestMapper<BonusCalculationAddRequest, BonusCalculation> {

  @Override
  public BonusCalculation toDomain(final BonusCalculationAddRequest request) {
    final var actionDate = LocalDate.now();

    return BonusCalculation.builder()
            .actionDate(actionDate)
            .qWeekId(request.getQWeekId())
            .results(new ArrayList<>())
            .comment(request.getComment())
            .build();
  }
}
