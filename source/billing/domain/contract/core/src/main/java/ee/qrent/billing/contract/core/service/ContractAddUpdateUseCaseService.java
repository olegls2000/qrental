package ee.qrent.billing.contract.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.common.in.validation.AddRequestValidator;
import ee.qrent.common.in.validation.UpdateRequestValidator;
import ee.qrent.billing.contract.api.in.request.ContractAddRequest;
import ee.qrent.billing.contract.api.in.request.ContractUpdateRequest;
import ee.qrent.billing.contract.api.in.usecase.ContractAddUseCase;
import ee.qrent.billing.contract.api.in.usecase.ContractUpdateUseCase;
import ee.qrent.billing.contract.api.out.ContractAddPort;
import ee.qrent.billing.contract.api.out.ContractLoadPort;
import ee.qrent.billing.contract.api.out.ContractUpdatePort;
import ee.qrent.billing.contract.core.mapper.ContractAddRequestMapper;
import ee.qrent.billing.contract.domain.Contract;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class ContractAddUpdateUseCaseService implements ContractAddUseCase, ContractUpdateUseCase {
  private final ContractAddPort addPort;
  private final ContractUpdatePort updatePort;
  private final ContractLoadPort loadPort;
  private final ContractAddRequestMapper addRequestMapper;
  private final AddRequestValidator<ContractAddRequest> addRequestValidator;
  private final UpdateRequestValidator<ContractUpdateRequest> updateRequestValidator;
  private final QDateTime qDateTime;

  @Override
  public Long add(final ContractAddRequest request) {
    final var violationsCollector = addRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return null;
    }
    final var contract = addRequestMapper.toDomain(request);
    final var savedContract = addPort.add(contract);

    return savedContract.getId();
  }

  @Override
  public void update(final ContractUpdateRequest request) {
    final var violationsCollector = updateRequestValidator.validate(request);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());

      return;
    }
    final var toStopContract = loadPort.loadById(request.getId());
    final var today = qDateTime.getToday();
    stop(toStopContract, today);

    final var addRequest = getContractAddRequest(toStopContract, request.getQFirmId(), today);
    add(addRequest);
  }

  private void stop(final Contract contractToStop, final LocalDate stopDate) {
    contractToStop.setDateEnd(stopDate);
    updatePort.update(contractToStop);
  }

  private ContractAddRequest getContractAddRequest(
      final Contract contractToStop, final Long newQFirmId, final LocalDate startDate) {
    final var result = new ContractAddRequest();
    result.setDateStart(startDate);
    final var duration = contractToStop.getContractDuration().getLabel();
    result.setContractDuration(duration);
    result.setQFirmId(newQFirmId);
    result.setDriverId(contractToStop.getDriverId());

    return result;
  }
}
