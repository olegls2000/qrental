package ee.qrental.insurance.repository.spring;

import ee.qrental.insurance.entity.jakarta.InsuranceCaseBalanceTransactionJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceCaseBalanceTransactionSpringDataRepository
    extends JpaRepository<InsuranceCaseBalanceTransactionJakartaEntity, Long> {}
