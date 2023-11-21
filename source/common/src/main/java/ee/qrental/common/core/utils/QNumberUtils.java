package ee.qrental.common.core.utils;

import static java.math.RoundingMode.DOWN;

import java.math.BigDecimal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class QNumberUtils {
  public static BigDecimal round(final BigDecimal value) {
    if (value == null) {
      return null;
    }

    return value.setScale(2, DOWN);
  }
}