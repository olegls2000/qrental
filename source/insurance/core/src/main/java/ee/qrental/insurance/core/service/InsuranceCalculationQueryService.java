package ee.qrental.insurance.core.service;

import static java.util.stream.Collectors.toList;

import ee.qrental.constant.api.in.query.GetQWeekQuery;


import ee.qrental.insurance.api.in.query.GetInsuranceCalculationQuery;
import ee.qrental.insurance.api.in.response.InsuranceCalculationResponse;
import ee.qrental.insurance.api.out.InsuranceCalculationLoadPort;
import ee.qrental.insurance.core.mapper.InsuranceCalculationResponseMapper;
import java.util.List;

import ee.qrental.transaction.api.in.query.rent.GetRentCalculationQuery;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCalculationQueryService implements GetInsuranceCalculationQuery {

  private final InsuranceCalculationLoadPort loadPort;
  private final InsuranceCalculationResponseMapper responseMapper;
  private final GetQWeekQuery qWeekQuery;
  private final GetRentCalculationQuery rentCalculationQuery;

  @Override
  public List<InsuranceCalculationResponse> getAll() {
    return loadPort.loadAll().stream().map(responseMapper::toResponse).collect(toList());
  }

  @Override
  public InsuranceCalculationResponse getById(Long id) {
    final var domain = loadPort.loadById(id);
    return responseMapper.toResponse(domain);
  }

  @Override
  public Long getStartQWeekId() {
    final var lastCalculatedQWeekId = loadPort.loadLastCalculatedQWeekId();
    if (lastCalculatedQWeekId == null) {
      final var firstQWeek = qWeekQuery.getFirstWeek();
      return firstQWeek.getId();
    } else {
      return lastCalculatedQWeekId;
    }
  }

  @Override
  public Long getEndQWeekId() {
    return rentCalculationQuery.getLastCalculatedQWeekId();
  }
}
