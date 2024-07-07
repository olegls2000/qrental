package ee.qrental.transaction.api.in.query.type;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrental.transaction.api.in.response.type.TransactionTypeResponse;
import java.util.List;

public interface GetTransactionTypeQuery
    extends BaseGetQuery<TransactionTypeUpdateRequest, TransactionTypeResponse> {

  TransactionTypeResponse getByName(final String name);
  List<TransactionTypeResponse> getByNameIn(final List<String> names);

  List<TransactionTypeResponse> getNegative();

  List<TransactionTypeResponse> getPositive();
}
