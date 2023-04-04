package ee.qrental.ui.controller;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDate;
import java.util.Locale;
import org.springframework.format.Formatter;

public class QDateFormatter implements Formatter<LocalDate> {

  @Override
  public LocalDate parse(final String text, final Locale locale) {
    return LocalDate.parse(text, ofPattern("yyyy-MM-dd"));
  }

  @Override
  public String print(final LocalDate object, final Locale locale) {
    return object.format(ofPattern("yyyy-MM-dd"));
  }
}
