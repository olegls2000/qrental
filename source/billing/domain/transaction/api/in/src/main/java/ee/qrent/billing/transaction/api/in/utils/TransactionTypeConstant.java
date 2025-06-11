package ee.qrent.billing.transaction.api.in.utils;

import lombok.experimental.UtilityClass;

//@UtilityClass
public class TransactionTypeConstant {
  public static final String TRANSACTION_TYPE_NAME_WEEKLY_RENT = "weekly rent";
  public static final String TRANSACTION_TYPE_NO_LABEL_FINE = "no label fine";
  public static final String TRANSACTION_TYPE_SELF_RESPONSIBILITY =
      "self responsibility payment request";
  public static final String TRANSACTION_TYPE_ABSENCE_ADJUSTMENT = "absence adjustment";
  public static final String TRANSACTION_TYPE_INNER_ROAD_INSURANCE = "inner road insurance";
  public static final String TRANSACTION_TYPE_DAMAGE_WRITE_OFF = "damage write off";
}
