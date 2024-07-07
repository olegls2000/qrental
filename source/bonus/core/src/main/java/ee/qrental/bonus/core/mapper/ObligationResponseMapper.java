package ee.qrental.bonus.core.mapper;

import static java.lang.String.format;

import ee.qrental.bonus.api.in.response.ObligationResponse;
import ee.qrental.bonus.domain.Obligation;
import ee.qrent.common.in.mapper.ResponseMapper;
import ee.qrental.driver.api.in.query.GetDriverQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ObligationResponseMapper implements ResponseMapper<ObligationResponse, Obligation> {

  private GetDriverQuery driverQuery;

  @Override
  public ObligationResponse toResponse(final Obligation domain) {
    if(domain == null){
      return null;
    }

    final var driver = driverQuery.getById(domain.getDriverId());

    return ObligationResponse.builder()
        .driverInfo(format("%s %s", driver.getFirstName(), driver.getLastName()))
        .amount(domain.getObligationAmount())
        .positiveAmount(domain.getPositiveAmount())
        .matchCount(domain.getMatchCount())
        .build();
  }

  @Override
  public String toObjectInfo(Obligation domain) {
    return null;
  }
}
