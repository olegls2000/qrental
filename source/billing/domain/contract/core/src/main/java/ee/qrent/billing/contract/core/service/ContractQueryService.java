package ee.qrent.billing.contract.core.service;

import static java.util.Arrays.stream;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.request.ContractUpdateRequest;
import ee.qrent.billing.contract.api.in.response.ContractResponse;
import ee.qrent.billing.contract.api.out.ContractLoadPort;
import ee.qrent.billing.contract.core.mapper.ContractResponseMapper;

import java.util.Comparator;
import java.util.List;

import ee.qrent.billing.contract.core.mapper.ContractUpdateRequestMapper;
import ee.qrent.billing.contract.domain.ContractDuration;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractQueryService implements GetContractQuery {

  private final Comparator<ContractResponse> DEFAULT_COMPARATOR =
      comparing(ContractResponse::getCreated);

  private final GetQWeekQuery qWeekQuery;
  private final ContractEndDateCalculator endDateCalculator;
  private final ContractLoadPort loadPort;
  private final ContractResponseMapper mapper;
  private final ContractUpdateRequestMapper updateRequestMapper;
  private final QDateTime qDateTime;

  @Override
  public List<ContractResponse> getAll() {
    return loadPort.loadAll().stream()
        .peek(endDateCalculator::setEndDate)
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR.reversed())
        .collect(toList());
  }

  @Override
  public ContractResponse getById(final Long id) {
    final var contract = loadPort.loadById(id);
    endDateCalculator.setEndDate(contract);

    return mapper.toResponse(contract);
  }

  @Override
  public String getObjectInfo(final Long id) {
    final var contract = loadPort.loadById(id);
    endDateCalculator.setEndDate(contract);

    return mapper.toObjectInfo(contract);
  }

  @Override
  public ContractUpdateRequest getUpdateRequestById(final Long id) {
    final var contract = loadPort.loadById(id);
    endDateCalculator.setEndDate(contract);

    return updateRequestMapper.toRequest(contract);
  }

  @Override
  public ContractResponse getLatestContractByDriverId(final Long driverId) {
    final var contract = loadPort.loadLatestByDriverId(driverId);
    endDateCalculator.setEndDate(contract);

    return mapper.toResponse(contract);
  }

  @Override
  public ContractResponse getActiveContractByDriverIdAndQWeekId(
      final Long driverId, final Long qWekId) {
    final var qWeek = qWeekQuery.getById(qWekId);
    final var activeContractOnRequestedWeek =
        loadPort.loadActiveByDateAndDriverId(qWeek.getStart(), driverId);
    endDateCalculator.setEndDate(activeContractOnRequestedWeek);

    return mapper.toResponse(activeContractOnRequestedWeek);
  }

  @Override
  public List<String> getAllDurations() {
    return stream(ContractDuration.values()).map(ContractDuration::getLabel).toList();
  }

  @Override
  public List<ContractResponse> getAllActiveForCurrentDate() {
    return loadPort.loadActiveByDate(qDateTime.getToday()).stream()
        .peek(endDateCalculator::setEndDate)
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR.reversed())
        .collect(toList());
  }

  @Override
  public ContractResponse getCurrentActiveByDriverId(final Long driverId) {
    final var today = qDateTime.getToday();
    final var currentActiveContract = loadPort.loadActiveByDateAndDriverId(today, driverId);
    endDateCalculator.setEndDate(currentActiveContract);

    return mapper.toResponse(currentActiveContract);
  }

  @Override
  public List<ContractResponse> getClosed() {
    return loadPort.loadClosedByDate(qDateTime.getToday()).stream()
        .peek(endDateCalculator::setEndDate)
        .map(mapper::toResponse)
        .sorted(DEFAULT_COMPARATOR.reversed())
        .collect(toList());
  }

  @Override
  public Long getCountActive() {
    return loadPort.loadCountActiveByDate(qDateTime.getToday());
  }

  @Override
  public Long getCountClosed() {
    return loadPort.loadCountClosedByDate(qDateTime.getToday());
  }
}
