package ee.qrental.transaction.domain.kind;

public enum TransactionKindsCode {
  F("Fee"),
  NFA("Non Fee Able"),
  FA("Fee Able"),
  P("Positive"),
  SR("Self Responsibility"),
  R("Repairment");

  private String name;

  TransactionKindsCode(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
