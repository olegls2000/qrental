package ee.qrent.billing.bonus.core.mapper;

import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrent.billing.bonus.api.in.request.BonusCalculationAddRequest;
import ee.qrent.billing.bonus.domain.BonusCalculation;
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
