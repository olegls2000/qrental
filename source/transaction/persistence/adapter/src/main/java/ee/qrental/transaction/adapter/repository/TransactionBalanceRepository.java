package ee.qrental.transaction.adapter.repository;

public interface TransactionBalanceRepository {
    boolean isTransactionCalculatedInBalance(final Long transactionId);
}
