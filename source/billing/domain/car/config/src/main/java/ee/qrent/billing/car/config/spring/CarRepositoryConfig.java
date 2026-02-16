package ee.qrent.billing.car.config.spring;

import ee.qrent.billing.car.persistence.repository.CarRepository;
import ee.qrent.billing.car.persistence.repository.impl.CarRepositoryImpl;
import ee.qrent.billing.car.persistence.repository.spring.CarSpringDataRepository;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationRepository;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationCalculationResultRepository;
import ee.qrent.billing.car.persistence.repository.BrandingVerificationRepository;
import ee.qrent.billing.car.persistence.repository.impl.BrandingVerificationCalculationRepositoryImpl;
import ee.qrent.billing.car.persistence.repository.impl.BrandingVerificationCalculationResultRepositoryImpl;
import ee.qrent.billing.car.persistence.repository.impl.BrandingVerificationRepositoryImpl;
import ee.qrent.billing.car.persistence.repository.spring.BrandingVerificationCalculationResultSpringDataRepository;
import ee.qrent.billing.car.persistence.repository.spring.BrandingVerificationCalculationSpringDataRepository;
import ee.qrent.billing.car.persistence.repository.spring.BrandingVerificationSpringDataRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CarRepositoryConfig {

  @Bean
  CarRepository getCarRepository(final CarSpringDataRepository springDataRepository) {
    return new CarRepositoryImpl(springDataRepository);
  }

  @Bean
  BrandingVerificationRepository getBrandingVerificationRepository(
      final BrandingVerificationSpringDataRepository springDataRepository) {
    return new BrandingVerificationRepositoryImpl(springDataRepository);
  }

  @Bean
  BrandingVerificationCalculationRepository getBrandingVerificationCalculationRepository(
      final BrandingVerificationCalculationSpringDataRepository springDataRepository) {
    return new BrandingVerificationCalculationRepositoryImpl(springDataRepository);
  }

  @Bean
  BrandingVerificationCalculationResultRepository getBrandingVerificationCalculationResultRepository(
      final BrandingVerificationCalculationResultSpringDataRepository springDataRepository) {
    return new BrandingVerificationCalculationResultRepositoryImpl(springDataRepository);
  }
}
