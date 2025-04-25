package ee.qrent.billing.insurance.core.service;

import static java.math.BigDecimal.ZERO;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.util.List;

import ee.qrent.billing.insurance.api.in.query.GetInsuranceCaseQuery;
import ee.qrent.billing.insurance.api.in.request.InsuranceCaseUpdateRequest;
import ee.qrent.billing.insurance.api.in.response.InsuranceCaseBalanceResponse;
import ee.qrent.billing.insurance.api.in.response.InsuranceCaseResponse;
import ee.qrent.billing.insurance.api.out.InsuranceCaseBalanceLoadPort;
import ee.qrent.billing.insurance.api.out.InsuranceCaseLoadPort;
import ee.qrent.billing.insurance.core.mapper.InsuranceCaseBalanceResponseMapper;
import ee.qrent.billing.insurance.core.mapper.InsuranceCaseResponseMapper;
import ee.qrent.billing.insurance.core.mapper.InsuranceCaseUpdateRequestMapper;
import ee.qrent.billing.transaction.api.in.query.GetTransactionQuery;
import ee.qrent.billing.transaction.api.in.response.TransactionResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsuranceCaseQueryService implements GetInsuranceCaseQuery {

  private final GetTransactionQuery transactionQuery;
  private final InsuranceCaseLoadPort loadPort;
  private final InsuranceCaseBalanceLoadPort insuranceCaseBalanceLoadPort;
  private final InsuranceCaseResponseMapper responseMapper;
  private final InsuranceCaseBalanceResponseMapper insuranceCaseBalanceResponseMapper;
  private final InsuranceCaseUpdateRequestMapper updateRequestMapper;

  @Override
  public List<InsuranceCaseResponse> getAll() {
    return loadPort.loadAll().stream().map(responseMapper::toResponse).collect(toList());
  }

  @Override
  public InsuranceCaseResponse getById(final Long id) {
    return responseMapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(final Long id) {
    return responseMapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public InsuranceCaseUpdateRequest getUpdateRequestById(final Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public List<InsuranceCaseResponse> getActive() {
    return loadPort.loadAllActive().stream().map(responseMapper::toResponse).collect(toList());
  }

  @Override
  public List<InsuranceCaseResponse> getActiveByDriverId(final Long driverId) {
    return loadPort.loadAllActiveByDriverId(driverId).stream()
        .map(responseMapper::toResponse)
        .collect(toList());
  }

  @Override
  public List<InsuranceCaseResponse> getClosed() {
    return loadPort.loadAlClosed().stream().map(responseMapper::toResponse).collect(toList());
  }

  @Override
  public Long getCountActive() {
    return loadPort.loadCountActive();
  }

  @Override
  public Long getCountClosed() {
    return loadPort.loadCountClosed();
  }

  @Override
  public List<InsuranceCaseBalanceResponse> getInsuranceCaseBalancesByInsuranceCase(
      final Long insuranceCaseId) {
    return insuranceCaseBalanceLoadPort.loadAllByInsuranceCseId(insuranceCaseId).stream()
        .map(domain -> insuranceCaseBalanceResponseMapper.toResponse(domain))
        .toList();
  }

  @Override
  public BigDecimal getPaidAmountByInsuranceCaseId(final Long insuranceCaseId) {
    final var paymentTransactionIds =
        loadPort.loadPaymentTransactionIdsByInsuranceCaseId(insuranceCaseId);

    return transactionQuery.getAllByIds(paymentTransactionIds).stream()
        .map(TransactionResponse::getRealAmount)
        .reduce(ZERO, BigDecimal::add)
        .abs();
  }
}
