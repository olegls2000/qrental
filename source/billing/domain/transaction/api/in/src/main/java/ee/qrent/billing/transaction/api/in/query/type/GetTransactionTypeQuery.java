package ee.qrent.billing.transaction.api.in.query.type;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeUpdateRequest;
import ee.qrent.billing.transaction.api.in.response.type.TransactionTypeResponse;
import java.util.List;

public interface GetTransactionTypeQuery
    extends BaseGetQuery<TransactionTypeUpdateRequest, TransactionTypeResponse> {

  TransactionTypeResponse getByCode(final String code);

  List<TransactionTypeResponse> getByCodeIn(final List<String> codes);

  List<TransactionTypeResponse> getNegative();

  List<TransactionTypeResponse> getPositive();
}
