package ee.qrent.billing.report.core.service.pdf.converter;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderCommon.CURRENCY_NAME_KEY;
import static ee.qrent.billing.report.core.service.pdf.label.WeeklyReportPdfLabelProviderCommon.getLabelFromCommon;
import static java.lang.String.format;
import static java.math.BigDecimal.ZERO;

@UtilityClass
public class WeeklyReportFormatUtils {
  static String formatDate(final LocalDate date) {
    if (date == null) {

      return "";
    }
    final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MMM.yyyy");

    return date.format(formatter);
  }

  static String formatAmount(final BigDecimal amount) {
    if (amount == null) {
      return "0.00";
    }
    return format(Locale.US, "%,.2f", amount);
  }

  static String formatAmountWithCurrency(final BigDecimal amount, final String language) {
    final var euroCurrency = getLabelFromCommon(language, CURRENCY_NAME_KEY);
    var nonNullAmount = amount;
    if (amount == null) {
      nonNullAmount = ZERO;
    }
    final var formattedAmount = formatAmount(nonNullAmount.abs());

    return format("%s %s", formattedAmount, euroCurrency);
  }

  static String formatInterval(final LocalDate start, final LocalDate end) {

    return "(%s - %s)".formatted(formatDate(start), formatDate(end));
  }
}
