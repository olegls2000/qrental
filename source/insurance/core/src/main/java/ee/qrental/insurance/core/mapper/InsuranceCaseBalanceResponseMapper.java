package ee.qrental.insurance.core.mapper;

import static ee.qrental.common.utils.QNumberUtils.qRound;
import static java.lang.String.format;

import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.insurance.api.in.response.InsuranceCaseBalanceResponse;
import ee.qrental.insurance.domain.InsuranceCaseBalance;
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
        .build();
  }
}
