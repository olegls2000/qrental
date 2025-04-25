package ee.qrent.billing.transaction.api.in.query.kind;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrent.billing.transaction.api.in.request.kind.TransactionKindUpdateRequest;
import ee.qrent.billing.transaction.api.in.response.kind.TransactionKindResponse;

import java.util.List;

public interface GetTransactionKindQuery
    extends BaseGetQuery<TransactionKindUpdateRequest, TransactionKindResponse> {

  TransactionKindResponse getByName(final String name);

  TransactionKindResponse getByCode(final String code);

  List<TransactionKindResponse> getAllNonRepairment();

  List<TransactionKindResponse> getAllNonRepairmentExceptNonFeeAble();

  List<TransactionKindResponse> getAllRepairment();

  List<TransactionKindResponse> getAllSelfResponsibility();

  List<TransactionKindResponse> getAllByCodes(final List<String> codes);
}
