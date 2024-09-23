package ee.qrental.transaction.adapter.adapter.rent;

import ee.qrental.transaction.adapter.repository.rent.RentCalculationRepository;
import ee.qrental.transaction.adapter.repository.rent.RentCalculationResultRepository;
import ee.qrental.transaction.api.out.rent.RentCalculationAddPort;
import ee.qrental.transaction.domain.rent.RentCalculation;
import ee.qrental.transaction.domain.rent.RentCalculationResult;
import ee.qrental.transaction.entity.jakarta.rent.RentCalculationJakartaEntity;
import ee.qrental.transaction.entity.jakarta.rent.RentCalculationResultJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationPersistenceAdapter implements RentCalculationAddPort {

  private final RentCalculationRepository rentCalculationRepository;
  private final RentCalculationResultRepository rentCalculationResultRepository;

  @Override
  public RentCalculation add(final RentCalculation domain) {
    final var rentCalculationEntity =
        RentCalculationJakartaEntity.builder()
            .actionDate(domain.getActionDate())
            .qWeekId(domain.getQWeekId())
            .comment(domain.getComment())
            .build();
    final var calculationEntitySaved = rentCalculationRepository.save(rentCalculationEntity);
    saveResults(domain, calculationEntitySaved);

    return RentCalculation.builder().id(calculationEntitySaved.getId()).build();
  }

  private void saveResults(
      final RentCalculation domain, final RentCalculationJakartaEntity rentCalculationEntitySaved) {
    final var rentCalculationResults = domain.getResults();
    for (final RentCalculationResult result : rentCalculationResults) {
      final var resultEntity =
          RentCalculationResultJakartaEntity.builder()
              .id(null)
              .rentCalculation(rentCalculationEntitySaved)
              .carLinkId(result.getCarLinkId())
              .transactionId(result.getTransactionId())
              .build();
      rentCalculationResultRepository.save(resultEntity);
    }
  }
}
