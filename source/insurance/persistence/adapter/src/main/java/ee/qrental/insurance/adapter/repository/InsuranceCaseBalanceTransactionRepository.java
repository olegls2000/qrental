package ee.qrental.insurance.adapter.repository;

import ee.qrental.insurance.entity.jakarta.InsuranceCaseBalanceTransactionJakartaEntity;

public interface InsuranceCaseBalanceTransactionRepository {
    InsuranceCaseBalanceTransactionJakartaEntity save(final InsuranceCaseBalanceTransactionJakartaEntity entity);
}
