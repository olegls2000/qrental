package ee.qrental.transaction.api.in.query.kind;

import ee.qrent.common.in.query.BaseGetQuery;
import ee.qrental.transaction.api.in.request.kind.TransactionKindUpdateRequest;
import ee.qrental.transaction.api.in.response.kind.TransactionKindResponse;

import java.util.List;

public interface GetTransactionKindQuery
    extends BaseGetQuery<TransactionKindUpdateRequest, TransactionKindResponse> {

  TransactionKindResponse getByName(final String name);

  TransactionKindResponse getByCode(final String code);

  List<TransactionKindResponse> getAllNonRepairment();

  List<TransactionKindResponse> getAllNonRepairmentExceptNonFeeAble();

  List<TransactionKindResponse> getAllRepairment();

  List<TransactionKindResponse> getAllSelfResponsibility();
}
