package ee.qrent.billing.insurance.core.mapper;

import static ee.qrent.common.utils.QNumberUtils.qRound;
import static java.lang.String.format;

import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.insurance.api.in.response.InsuranceCaseBalanceResponse;
import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseBalanceResponseMapper {

  private final GetQWeekQuery qWeekQuery;

  public InsuranceCaseBalanceResponse toResponse(final InsuranceCaseBalance domain) {
    final var qWeekResponse = qWeekQuery.getById(domain.getQWeekId());

    return InsuranceCaseBalanceResponse.builder()
        .qWeekInfo(format("%d - %d", qWeekResponse.getYear(), qWeekResponse.getNumber()))
        .damageRemaining(qRound(domain.getDamageRemaining()))
        .selfResponsibilityRemaining(qRound(domain.getSelfResponsibilityRemaining()))
        .withQKasko(domain.getWithQKasko())
        .build();
  }
}
