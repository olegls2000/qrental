package ee.qrental.transaction.adapter.mapper.rent;

import ee.qrental.transaction.domain.rent.RentCalculation;
import ee.qrental.transaction.domain.rent.RentCalculationResult;
import ee.qrental.transaction.entity.jakarta.rent.RentCalculationJakartaEntity;
import ee.qrental.transaction.entity.jakarta.rent.RentCalculationResultJakartaEntity;
import lombok.AllArgsConstructor;

import java.util.Collections;
import java.util.List;

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
                    .toList();

    return Collections.unmodifiableList(result);
  }
}
