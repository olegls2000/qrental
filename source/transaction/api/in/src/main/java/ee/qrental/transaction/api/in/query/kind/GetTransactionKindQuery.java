package ee.qrental.transaction.api.in.query.kind;

import ee.qrental.common.core.in.query.BaseGetQuery;
import ee.qrental.transaction.api.in.request.kind.TransactionKindUpdateRequest;
import ee.qrental.transaction.api.in.response.kind.TransactionKindResponse;

public interface GetTransactionKindQuery
    extends BaseGetQuery<TransactionKindUpdateRequest, TransactionKindResponse> {

  TransactionKindResponse getByName(final String name);

  TransactionKindResponse getByCode(final String code);
}
