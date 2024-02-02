package ee.qrental.bonus.adapter.adapter;

import ee.qrental.bonus.adapter.repository.BonusCalculationRepository;
import ee.qrental.bonus.adapter.repository.BonusCalculationResultRepository;
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

  @Override
  public BonusCalculation add(final BonusCalculation domain) {
    final var rentCalculationEntity =
        BonusCalculationJakartaEntity.builder()
            .actionDate(domain.getActionDate())
            .qWeekId(domain.getQWeekId())
            .comment(domain.getComment())
            .build();
    final var rentCalculationEntitySaved = calculationRepository.save(rentCalculationEntity);
    saveResults(domain, rentCalculationEntitySaved);

    return null;
  }

  private void saveResults(
      final BonusCalculation domain, final BonusCalculationJakartaEntity calculationEntitySaved) {
    final var results = domain.getResults();
    for (final BonusCalculationResult result : results) {
      final var resultEntity =
          BonusCalculationResultJakartaEntity.builder()
              .id(null)
              .transactionId(result.getTransactionId())
              .bonusCalculation(calculationEntitySaved)
                  //TODO..
              .bonusProgram(null)
              .build();

      resultRepository.save(resultEntity);
    }
  }
}