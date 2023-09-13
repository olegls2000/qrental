package ee.qrental.contract.core.service;

import static jakarta.transaction.Transactional.TxType.SUPPORTS;
import static java.lang.String.format;
import static java.time.format.DateTimeFormatter.ofPattern;

import ee.qrental.contract.api.in.request.ContractAddRequest;
import ee.qrental.contract.api.in.request.ContractDeleteRequest;
import ee.qrental.contract.api.in.request.ContractUpdateRequest;
import ee.qrental.contract.api.in.usecase.ContractAddUseCase;
import ee.qrental.contract.api.in.usecase.ContractDeleteUseCase;
import ee.qrental.contract.api.in.usecase.ContractUpdateUseCase;
import ee.qrental.contract.api.out.ContractAddPort;
import ee.qrental.contract.api.out.ContractDeletePort;
import ee.qrental.contract.api.out.ContractLoadPort;
import ee.qrental.contract.api.out.ContractUpdatePort;
import ee.qrental.contract.core.mapper.ContractAddRequestMapper;
import ee.qrental.contract.core.mapper.ContractUpdateRequestMapper;
import ee.qrental.contract.core.validator.ContractBusinessRuleValidator;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@Transactional(SUPPORTS)
@AllArgsConstructor
public class ContractUseCaseService
    implements ContractAddUseCase, ContractUpdateUseCase, ContractDeleteUseCase {

  private final ContractAddPort addPort;
  private final ContractUpdatePort updatePort;
  private final ContractDeletePort deletePort;
  private final ContractLoadPort loadPort;
  private final ContractAddRequestMapper addRequestMapper;
  private final ContractUpdateRequestMapper updateRequestMapper;
  private final ContractBusinessRuleValidator businessRuleValidator;

  @Override
  public Long add(final ContractAddRequest request) {

    final var contract = addRequestMapper.toDomain(request);
    final var contractNumber = getContractNumber(currentDate, request);
    contract.setNumber(contractNumber);
    final var violationsCollector = businessRuleValidator.validateAdd(contract);
    if (violationsCollector.hasViolations()) {
      request.setViolations(violationsCollector.getViolations());
      return null;
    }
    final var savedContract = addPort.add(contract);

    return savedContract.getId();
  }


  @Override
  public void update(final ContractUpdateRequest request) {
    checkExistence(request.getId());
    updatePort.update(updateRequestMapper.toDomain(request));
  }

  @Transactional
  @Override
  public void delete(final ContractDeleteRequest request) {
    deletePort.delete(request.getId());
  }

  private void checkExistence(final Long id) {
    if (loadPort.loadById(id) == null) {
      throw new RuntimeException("Update of Contract failed. No Record with id = " + id);
    }
  }
}
