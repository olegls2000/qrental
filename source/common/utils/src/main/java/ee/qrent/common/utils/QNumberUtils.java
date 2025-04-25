package ee.qrent.common.utils;

import static java.math.BigDecimal.ROUND_HALF_UP;

import java.math.BigDecimal;
import lombok.experimental.UtilityClass;

@UtilityClass
public class QNumberUtils {
  public static BigDecimal qRound(final BigDecimal value) {
    if (value == null) {
      return null;
    }

    return value.setScale(2, ROUND_HALF_UP);
  }
}
