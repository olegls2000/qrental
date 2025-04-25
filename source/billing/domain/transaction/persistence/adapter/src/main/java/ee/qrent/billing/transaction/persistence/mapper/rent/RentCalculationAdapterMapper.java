package ee.qrent.billing.transaction.persistence.mapper.rent;

import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.toList;

import ee.qrent.billing.transaction.domain.rent.RentCalculation;
import ee.qrent.billing.transaction.domain.rent.RentCalculationResult;
import ee.qrent.billing.transaction.persistence.entity.jakarta.rent.RentCalculationJakartaEntity;
import ee.qrent.billing.transaction.persistence.entity.jakarta.rent.RentCalculationResultJakartaEntity;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RentCalculationAdapterMapper {

  public RentCalculation mapToDomain(final RentCalculationJakartaEntity entity) {
    if (entity == null) {
      return null;
    }

    return RentCalculation.builder()
        .id(entity.getId())
        .actionDate(entity.getActionDate())
        .qWeekId(entity.getQWeekId())
        .results(mapResultsToDomains(entity.getResults()))
        .comment(entity.getComment())
        .build();
  }

  private List<RentCalculationResult> mapResultsToDomains(
      final List<RentCalculationResultJakartaEntity> resultsList) {

    if (resultsList == null) {
      return null;
    }
    final var result =
        resultsList.stream()
            .map(
                entity ->
                    RentCalculationResult.builder()
                        .id(entity.getId())
                        .carLinkId(entity.getCarLinkId())
                        .transactionId(entity.getTransactionId())
                        .build())
            .collect(toList());

    return unmodifiableList(result);
  }
}
