package ee.qrent.billing.insurance.persistence.mapper;

import ee.qrent.billing.insurance.domain.InsuranceCaseBalance;
import ee.qrent.billing.insurance.persistence.entity.jakarta.InsuranceCaseBalanceJakartaEntity;
import ee.qrent.billing.insurance.persistence.entity.jakarta.InsuranceCaseJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseBalanceAdapterMapper {

  private final InsuranceCaseAdapterMapper insuranceCaseMapper;

  public InsuranceCaseBalance mapToDomain(final InsuranceCaseBalanceJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return InsuranceCaseBalance.builder()
        .id(entity.getId())
        .qWeekId(entity.getQWeekId())
        .selfResponsibilityRemaining(entity.getSelfResponsibilityRemaining())
        .damageRemaining(entity.getDamageRemaining())
        .withQKasko(entity.getWithQKasko())
        .insuranceCase(insuranceCaseMapper.mapToDomain(entity.getInsuranceCase()))
        .build();
  }

  public InsuranceCaseBalanceJakartaEntity mapToEntity(final InsuranceCaseBalance domain) {
    if (domain == null) {
      return null;
    }
    return InsuranceCaseBalanceJakartaEntity.builder()
        .id(domain.getId())
        .qWeekId(domain.getQWeekId())
        .damageRemaining(domain.getDamageRemaining())
        .selfResponsibilityRemaining(domain.getSelfResponsibilityRemaining())
        .withQKasko(domain.getWithQKasko())
        .insuranceCase(
            InsuranceCaseJakartaEntity.builder().id(domain.getInsuranceCase().getId()).build())
        .build();
  }
}
