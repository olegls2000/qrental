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
            .startDate(domain.getStartDate())
            .endDate(domain.getEndDate())
            .comment(domain.getComment())
            .build();
    final var rentCalculationEntitySaved = rentCalculationRepository.save(rentCalculationEntity);
    saveRentCalculationResults(domain, rentCalculationEntitySaved);

    return null;
  }

  private void saveRentCalculationResults(
      final RentCalculation domain, final RentCalculationJakartaEntity rentCalculationEntitySaved) {
    final var rentCalculationResults = domain.getResults();
    for (final RentCalculationResult result : rentCalculationResults) {
      final var rentCalculationResultEntity =
          RentCalculationResultJakartaEntity.builder()
              .id(null)
              .rentCalculation(rentCalculationEntitySaved)
              .carLinkId(result.getCarLinkId())
              .transactionId(result.getTransactionId())
              .build();
      rentCalculationResultRepository.save(rentCalculationResultEntity);
    }
  }
}
