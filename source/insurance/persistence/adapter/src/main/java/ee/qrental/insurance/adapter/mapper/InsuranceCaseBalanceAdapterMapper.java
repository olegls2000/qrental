package ee.qrental.insurance.adapter.mapper;

import ee.qrental.insurance.domain.InsuranceCaseBalance;
import ee.qrental.insurance.entity.jakarta.InsuranceCaseBalanceJakartaEntity;

public class InsuranceCaseBalanceAdapterMapper {

  public InsuranceCaseBalance mapToDomain(final InsuranceCaseBalanceJakartaEntity entity) {
    if (entity == null) {
      return null;
    }
    return InsuranceCaseBalance.builder().id(entity.getId()).build();
  }

  public InsuranceCaseBalanceJakartaEntity mapToEntity(final InsuranceCaseBalance domain) {
    if (domain == null) {
      return null;
    }
    return InsuranceCaseBalanceJakartaEntity.builder().id(domain.getId()).build();
  }
}
