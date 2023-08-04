package ee.qrental.ui.controller;

import static java.time.format.DateTimeFormatter.ofPattern;

import java.time.LocalDate;
import java.util.Locale;
import org.springframework.format.Formatter;

public class QDateFormatter implements Formatter<LocalDate> {

  @Override
  public LocalDate parse(final String text, final Locale locale) {
    return LocalDate.parse(text, ofPattern("dd-MM-yyyy"));
  }

  @Override
  public String print(final LocalDate object, final Locale locale) {
    return object.format(ofPattern("dd-MM-yyyy"));
  }
}
