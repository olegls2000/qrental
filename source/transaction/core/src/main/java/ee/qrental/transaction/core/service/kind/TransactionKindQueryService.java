package ee.qrental.transaction.core.service.kind;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;

import ee.qrental.transaction.api.in.query.kind.GetTransactionKindQuery;
import ee.qrental.transaction.api.in.request.kind.TransactionKindUpdateRequest;
import ee.qrental.transaction.api.in.response.kind.TransactionKindResponse;
import ee.qrental.transaction.api.out.kind.TransactionKindLoadPort;
import ee.qrental.transaction.core.mapper.kind.TransactionKindResponseMapper;
import ee.qrental.transaction.core.mapper.kind.TransactionKindUpdateRequestMapper;
import ee.qrental.transaction.domain.kind.TransactionKindsCode;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionKindQueryService implements GetTransactionKindQuery {

  private final TransactionKindLoadPort loadPort;
  private final TransactionKindResponseMapper mapper;
  private final TransactionKindUpdateRequestMapper updateRequestMapper;

  @Override
  public List<TransactionKindResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public TransactionKindResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public TransactionKindUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public TransactionKindResponse getByName(final String name) {
    return mapper.toResponse(loadPort.loadByName(name));
  }

  @Override
  public TransactionKindResponse getByCode(final String code) {
    return mapper.toResponse(loadPort.loadByCode(code));
  }

  @Override
  public List<TransactionKindResponse> getAllNonRepairment() {
    return loadPort
        .loadAllByCodeIn(
            asList(
                TransactionKindsCode.F.name(),
                TransactionKindsCode.NFA.name(),
                TransactionKindsCode.FA.name(),
                TransactionKindsCode.P.name()))
        .stream()
        .map(mapper::toResponse)
        .collect(toList());
  }

  @Override
  public List<TransactionKindResponse> getAllSelfResponsibility() {
    return loadPort.loadAllByCodeIn(asList(TransactionKindsCode.SR.name())).stream()
        .map(mapper::toResponse)
        .collect(toList());
  }

  @Override
  public List<TransactionKindResponse> getAllNonRepairmentExceptNonFeeAble() {
    return loadPort
        .loadAllByCodeIn(
            asList(
                TransactionKindsCode.F.name(),
                TransactionKindsCode.FA.name(),
                TransactionKindsCode.P.name()))
        .stream()
        .map(mapper::toResponse)
        .collect(toList());
  }

  @Override
  public List<TransactionKindResponse> getAllRepairment() {
    return loadPort.loadAllByCodeIn(singletonList(TransactionKindsCode.R.name())).stream()
        .map(mapper::toResponse)
        .collect(toList());
  }
}
