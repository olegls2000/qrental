package ee.qrental.transaction.domain.kind;

public enum TransactionKindsCode {
  F("Fee"),
  NFA("Non Fee Able"),
  FA("Fee Able"),
  P("Positive"),
  R("Repairment");

  private String name;

  TransactionKindsCode(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
