package ee.qrental.bonus.adapter.adapter;

import ee.qrental.bonus.adapter.mapper.ObligationCalculationAdapterMapper;
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
  private final ObligationCalculationAdapterMapper mapper;

  @Override
  public ObligationCalculation add(final ObligationCalculation domain) {
    final var calculationEntity =
        ObligationCalculationJakartaEntity.builder()
            .actionDate(domain.getActionDate())
            .qWeekId(domain.getQWeekId())
            .comment(domain.getComment())
            .build();
    final var calculationEntitySaved = calculationRepository.save(calculationEntity);
    saveResults(domain, calculationEntitySaved);

    return mapper.mapToDomain(calculationEntitySaved);
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
