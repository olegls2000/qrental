package ee.qrental.bonus.adapter.adapter;

import ee.qrental.bonus.adapter.repository.ObligationCalculationRepository;
import ee.qrental.bonus.adapter.repository.ObligationCalculationResultRepository;
import ee.qrental.bonus.api.out.ObligationCalculationAddPort;
import ee.qrental.bonus.domain.ObligationCalculation;
import ee.qrental.bonus.domain.ObligationCalculationResult;
import ee.qrental.bonus.entity.jakarta.ObligationCalculationJakartaEntity;
import ee.qrental.bonus.entity.jakarta.ObligationCalculationResultJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationCalculationPersistenceAdapter implements ObligationCalculationAddPort {

  private final ObligationCalculationRepository calculationRepository;
  private final ObligationCalculationResultRepository resultRepository;

  @Override
  public ObligationCalculation add(final ObligationCalculation domain) {
    final var rentCalculationEntity =
        ObligationCalculationJakartaEntity.builder()
            .actionDate(domain.getActionDate())
            .qWeekId(domain.getQWeekId())
            .comment(domain.getComment())
            .build();
    final var rentCalculationEntitySaved = calculationRepository.save(rentCalculationEntity);
    saveResults(domain, rentCalculationEntitySaved);

    return null;
  }

  private void saveResults(
      final ObligationCalculation domain,
      final ObligationCalculationJakartaEntity calculationEntitySaved) {
    final var results = domain.getResults();
    for (final ObligationCalculationResult result : results) {
      final var resultEntity =
          ObligationCalculationResultJakartaEntity.builder()
              .id(null)
              .obligationId(result.getObligationId())
              .obligationCalculation(calculationEntitySaved)
              .build();

      resultRepository.save(resultEntity);
    }
  }
}
