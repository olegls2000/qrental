package ee.qrent.billing.ui.formatter;


import static ee.qrent.common.utils.QTimeUtils.Q_DATE_FORMATTER;
import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDate;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;
import org.springframework.format.Formatter;

public class QDateFormatter implements Formatter<LocalDate> {
  public final static String MODEL_ATTRIBUTE_DATE_FORMATTER = "qDateFormatter";

  public String format(final TemporalAccessor temporal) {
    if (temporal == null){
      return "empty";
    }
    return Q_DATE_FORMATTER.format(temporal);
  }

  @Override
  public LocalDate parse(final String text, final Locale locale) {
    return LocalDate.parse(text, ofPattern("yyyy-MM-dd"));
  }

  @Override
  public String print(final LocalDate object, final Locale locale) {
    return object.format(ofPattern("yyyy-MM-dd"));
  }
}
