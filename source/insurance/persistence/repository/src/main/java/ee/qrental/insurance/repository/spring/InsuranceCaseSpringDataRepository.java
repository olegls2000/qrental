package ee.qrental.insurance.repository.spring;

import ee.qrental.insurance.entity.jakarta.InsuranceCaseJakartaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InsuranceCaseSpringDataRepository
    extends JpaRepository<InsuranceCaseJakartaEntity, Long> {

  List<InsuranceCaseJakartaEntity> findAllByActiveIsTrue();
}
