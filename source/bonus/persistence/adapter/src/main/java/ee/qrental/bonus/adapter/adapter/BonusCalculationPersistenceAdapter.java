package ee.qrental.bonus.adapter.adapter;

import ee.qrental.bonus.adapter.repository.BonusCalculationRepository;
import ee.qrental.bonus.adapter.repository.BonusCalculationResultRepository;
import ee.qrental.bonus.adapter.repository.BonusProgramRepository;
import ee.qrental.bonus.api.out.BonusCalculationAddPort;
import ee.qrental.bonus.domain.BonusCalculation;
import ee.qrental.bonus.domain.BonusCalculationResult;
import ee.qrental.bonus.entity.jakarta.BonusCalculationJakartaEntity;
import ee.qrental.bonus.entity.jakarta.BonusCalculationResultJakartaEntity;
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
