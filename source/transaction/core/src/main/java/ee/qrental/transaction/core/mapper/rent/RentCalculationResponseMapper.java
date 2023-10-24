package ee.qrental.transaction.core.mapper.rent;

import ee.qrental.common.core.in.mapper.ResponseMapper;
import ee.qrental.transaction.api.in.response.rent.RentCalculationResponse;
import ee.qrental.transaction.domain.rent.RentCalculation;

public class RentCalculationResponseMapper
    implements ResponseMapper<RentCalculationResponse, RentCalculation> {
  @Override
  public RentCalculationResponse toResponse(final RentCalculation domain) {
    if (domain == null) {
      return null;
    }

    return RentCalculationResponse.builder()
        .id(domain.getId())
        .actionDate(domain.getActionDate())
        .startDate(domain.getStartDate())
        .endDate(domain.getEndDate())
        .transactionCount(domain.getResults().size())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(final RentCalculation domain) {
    return String.format("Rent Calculation was done at: %s", domain.getActionDate());
  }
}
