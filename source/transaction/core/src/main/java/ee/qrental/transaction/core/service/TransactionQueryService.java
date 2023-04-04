package ee.qrental.transaction.core.service;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import ee.qrental.transaction.api.in.query.GetTransactionQuery;
import ee.qrental.transaction.api.in.request.TransactionFilterRequest;
import ee.qrental.transaction.api.in.request.TransactionUpdateRequest;
import ee.qrental.transaction.api.in.response.TransactionResponse;
import ee.qrental.transaction.api.out.TransactionLoadPort;
import ee.qrental.transaction.core.mapper.TransactionResponseMapper;
import ee.qrental.transaction.core.mapper.TransactionUpdateRequestMapper;
import ee.qrental.transaction.core.service.strategy.TransactionLoadStrategy;
import ee.qrental.transaction.domain.Transaction;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionQueryService implements GetTransactionQuery {

  private final TransactionLoadPort transactionLoadPort;
  private final List<TransactionLoadStrategy> loadStrategies;
  private final TransactionResponseMapper responseMapper;
  private final TransactionUpdateRequestMapper updateRequestMapper;

  @Override
  public List<TransactionResponse> getAll() {
    return mapToTransactionResponseList(transactionLoadPort.loadAll());
  }

  @Override
  public TransactionResponse getById(final Long id) {
    return responseMapper.toResponse(transactionLoadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(final Long id) {
    return responseMapper.toObjectInfo(transactionLoadPort.loadById(id));
  }

  @Override
  public List<TransactionResponse> getAllByDriverId(final Long driverId) {
    return mapToTransactionResponseList(transactionLoadPort.loadAllByDriverId(driverId));
  }

  @Override
  public List<TransactionResponse> getAllByCalculationId(Long calculationId) {
    return mapToTransactionResponseList(transactionLoadPort.loadAllByCalculationId(calculationId));
  }

  @Override
  public List<TransactionResponse> getAllByFilterRequest(final TransactionFilterRequest request) {
    return mapToTransactionResponseList(
        loadStrategies.stream()
            .filter(strategy -> strategy.canApply(request))
            .findFirst()
            .orElseThrow(
                () ->
                    new RuntimeException("No Load Strategy was found for the request: " + request))
            .load(request));
  }

  @Override
  public TransactionUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(transactionLoadPort.loadById(id));
  }

  private List<TransactionResponse> mapToTransactionResponseList(
      final List<Transaction> transactions) {
    return transactions.stream()
        .map(responseMapper::toResponse)
        .sorted(comparing(TransactionResponse::getDate).reversed())
        .collect(toList());
  }
}
