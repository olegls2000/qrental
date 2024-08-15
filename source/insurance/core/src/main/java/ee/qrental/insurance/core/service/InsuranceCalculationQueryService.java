package ee.qrental.insurance.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.insurance.api.in.query.GetInsuranceCalculationQuery;
import ee.qrental.insurance.api.in.response.InsuranceCalculationResponse;
import ee.qrental.insurance.api.out.InsuranceCalculationLoadPort;
import ee.qrental.insurance.core.mapper.InsuranceCalculationResponseMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationQueryService implements GetInsuranceCalculationQuery {

  private final InsuranceCalculationLoadPort loadPort;
  private final InsuranceCalculationResponseMapper responseMapper;

  @Override
  public List<InsuranceCalculationResponse> getAll() {
    return loadPort.loadAll().stream().map(responseMapper::toResponse).collect(toList());
  }
}
