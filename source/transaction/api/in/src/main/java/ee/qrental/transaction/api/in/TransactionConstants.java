package ee.qrental.transaction.api.in;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;

@UtilityClass
public class TransactionConstants {
  public static final String TRANSACTION_TYPE_NAME_FEE_DEBT = "fee debt";
  public static final String TRANSACTION_TYPE_NAME_FEE_REPLENISH = "fee replenish";
  public static final  String TRANSACTION_TYPE_COMPENSATION = "compensation";
  private static final Set<String> TRANSACTION_TYPES_FEE =
          new HashSet<>(asList(TRANSACTION_TYPE_NAME_FEE_DEBT, TRANSACTION_TYPE_NAME_FEE_REPLENISH));
   public static boolean isFeeType(final String type){
    return TRANSACTION_TYPES_FEE.contains(type);
  }

  public static boolean isNotFeeType(final String type){
    return !TRANSACTION_TYPES_FEE.contains(type);
  }
}
