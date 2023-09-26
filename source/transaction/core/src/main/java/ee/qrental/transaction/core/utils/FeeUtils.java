package ee.qrental.transaction.core.utils;

import ee.qrental.constant.api.in.response.ConstantResponse;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class FeeUtils {
  public static final String FEE_WEEKLY_INTEREST = "fee weekly interest";

  public static BigDecimal getWeekFeeInterest(final ConstantResponse constant) {
    if (constant == null) {
      throw new RuntimeException(
          "Please create a Constant: " + FEE_WEEKLY_INTEREST + " with appropriate value");
    }
    return constant.getValue();
  }
}
