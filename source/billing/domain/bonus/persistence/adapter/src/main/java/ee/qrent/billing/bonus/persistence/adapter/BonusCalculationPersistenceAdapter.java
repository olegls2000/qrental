package ee.qrent.billing.bonus.persistence.adapter;

import ee.qrent.billing.bonus.persistence.repository.BonusCalculationRepository;
import ee.qrent.billing.bonus.persistence.repository.BonusCalculationResultRepository;
import ee.qrent.billing.bonus.persistence.repository.BonusProgramRepository;
import ee.qrent.billing.bonus.api.out.BonusCalculationAddPort;
import ee.qrent.billing.bonus.domain.BonusCalculation;
import ee.qrent.billing.bonus.domain.BonusCalculationResult;
import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusCalculationJakartaEntity;
import ee.qrent.billing.bonus.persistence.entity.jakarta.BonusCalculationResultJakartaEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BonusCalculationPersistenceAdapter implements BonusCalculationAddPort {

  private final BonusCalculationRepository calculationRepository;
  private final BonusCalculationResultRepository resultRepository;
  private final BonusProgramRepository bonusProgramRepository;

  @Override
  public BonusCalculation add(final BonusCalculation domain) {
    final var rentCalculationEntity =
        BonusCalculationJakartaEntity.builder()
            .actionDate(domain.getActionDate())
            .qWeekId(domain.getQWeekId())
            .comment(domain.getComment())
            .build();
    final var calculationEntitySaved = calculationRepository.save(rentCalculationEntity);
    saveResults(domain, calculationEntitySaved);

    return null;
  }

  private void saveResults(
      final BonusCalculation domain, final BonusCalculationJakartaEntity calculationEntitySaved) {

    final var results = domain.getResults();
    for (final BonusCalculationResult result : results) {
      final var bonusProgram = bonusProgramRepository.getReferenceById(result.getBonusProgramId());
      final var resultEntity =
          BonusCalculationResultJakartaEntity.builder()
              .id(null)
              .transactionId(result.getTransactionId())
              .bonusCalculation(calculationEntitySaved)
              .bonusProgram(bonusProgram)
              .build();

      resultRepository.save(resultEntity);
    }
  }
}
