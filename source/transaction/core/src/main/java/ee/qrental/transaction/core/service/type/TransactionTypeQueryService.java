package ee.qrental.transaction.core.service.type;

import ee.qrental.transaction.api.in.query.type.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrental.transaction.api.in.response.type.TransactionTypeResponse;
import ee.qrental.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrental.transaction.core.mapper.type.TransactionTypeResponseMapper;
import ee.qrental.transaction.core.mapper.type.TransactionTypeUpdateRequestMapper;
import ee.qrental.transaction.domain.kind.TransactionKindsCode;
import lombok.AllArgsConstructor;

import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

@AllArgsConstructor
public class TransactionTypeQueryService implements GetTransactionTypeQuery {

  private final TransactionTypeLoadPort loadPort;
  private final TransactionTypeResponseMapper mapper;
  private final TransactionTypeUpdateRequestMapper updateRequestMapper;

  @Override
  public List<TransactionTypeResponse> getAll() {
    return loadPort.loadAll().stream()
        .map(mapper::toResponse)
        .sorted(
            comparing(TransactionTypeResponse::getKind)
                .thenComparing(TransactionTypeResponse::getName))
        .collect(toList());
  }

  @Override
  public TransactionTypeResponse getById(final Long id) {
    return mapper.toResponse(loadPort.loadById(id));
  }

  @Override
  public String getObjectInfo(Long id) {
    return mapper.toObjectInfo(loadPort.loadById(id));
  }

  @Override
  public TransactionTypeUpdateRequest getUpdateRequestById(Long id) {
    return updateRequestMapper.toRequest(loadPort.loadById(id));
  }

  @Override
  public TransactionTypeResponse getByName(final String name) {
    return mapper.toResponse(loadPort.loadByName(name));
  }

  @Override
  public List<TransactionTypeResponse> getByNameIn(final List<String> names) {
    return loadPort.loadByNameIn(names).stream().map(mapper::toResponse).toList();
  }

  @Override
  public List<TransactionTypeResponse> getNegative() {

    return loadPort
        .loadByKindCodesIn(
            asList(
                TransactionKindsCode.F.name(),
                TransactionKindsCode.SR.name(),
                TransactionKindsCode.R.name(),
                TransactionKindsCode.NFA.name(),
                TransactionKindsCode.SR.name(),
                TransactionKindsCode.FA.name()))
        .stream()
        .map(mapper::toResponse)
        .collect(toList());
  }

  @Override
  public List<TransactionTypeResponse> getPositive() {
    return loadPort.loadByKindCodesIn(List.of(TransactionKindsCode.P.name())).stream()
        .map(mapper::toResponse)
        .toList();
  }
}
