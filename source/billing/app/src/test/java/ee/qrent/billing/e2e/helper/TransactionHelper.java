package ee.qrent.billing.e2e.helper;

import ee.qrent.billing.transaction.api.in.request.TransactionAddRequest;
import ee.qrent.billing.transaction.api.in.request.type.TransactionTypeAddRequest;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionHelper {

  public static TransactionAddRequest getValidAddRequest(
      final Long driverId, final Long transactionTypeId, final LocalDate transactionDate) {
    final var request = new TransactionAddRequest();

    request.setDriverId(driverId);
    request.setTransactionTypeId(transactionTypeId);
    request.setAmount(BigDecimal.valueOf(100));
    request.setDate(transactionDate);

    return request;
  }

  public static TransactionTypeAddRequest getValidAddRequest(
      final Long transactionKindId, final String code) {
    final var request = new TransactionTypeAddRequest();
    request.setTransactionKindId(transactionKindId);
    request.setCode(code);
    request.setUiName(code + "_UI");
    request.setUiVisible(true);
    request.setInvoiceIncluded(true);
    request.setNameEng(code + "_ENG");
    request.setNameEst(code + "_EST");
    request.setNameRus(code + "_RUS");

    return request;
  }
}
