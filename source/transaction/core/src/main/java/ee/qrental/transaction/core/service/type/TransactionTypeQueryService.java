package ee.qrental.transaction.core.service.type;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.stream.Collectors.toList;

import ee.qrental.transaction.api.in.query.GetTransactionTypeQuery;
import ee.qrental.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrental.transaction.api.in.response.type.TransactionTypeResponse;
import ee.qrental.transaction.api.out.type.TransactionTypeLoadPort;
import ee.qrental.transaction.core.mapper.type.TransactionTypeResponseMapper;
import ee.qrental.transaction.core.mapper.type.TransactionTypeUpdateRequestMapper;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionTypeQueryService implements GetTransactionTypeQuery {

  private final TransactionTypeLoadPort loadPort;
  private final TransactionTypeResponseMapper mapper;
  private final TransactionTypeUpdateRequestMapper updateRequestMapper;

  @Override
  public List<TransactionTypeResponse> getAll() {
    return loadPort.loadAll().stream().map(mapper::toResponse).collect(toList());
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
  public List<TransactionTypeResponse> getNegative() {
    return loadPort.loadByNegative(TRUE).stream().map(mapper::toResponse).collect(toList());
  }

  @Override
  public List<TransactionTypeResponse> getPositive() {
    return loadPort.loadByNegative(FALSE).stream().map(mapper::toResponse).collect(toList());
  }
}
