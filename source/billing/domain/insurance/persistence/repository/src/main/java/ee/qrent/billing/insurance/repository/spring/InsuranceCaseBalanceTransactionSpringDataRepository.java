package ee.qrent.billing.insurance.repository.spring;

import ee.qrent.billing.insurance.persistence.entity.jakarta.InsuranceCaseBalanceTransactionJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceCaseBalanceTransactionSpringDataRepository
    extends JpaRepository<InsuranceCaseBalanceTransactionJakartaEntity, Long> {}
