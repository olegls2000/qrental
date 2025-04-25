package ee.qrent.billing.insurance.persistence.repository;

import ee.qrent.billing.insurance.persistence.entity.jakarta.InsuranceCaseBalanceTransactionJakartaEntity;

public interface InsuranceCaseBalanceTransactionRepository {
    InsuranceCaseBalanceTransactionJakartaEntity save(final InsuranceCaseBalanceTransactionJakartaEntity entity);
}
