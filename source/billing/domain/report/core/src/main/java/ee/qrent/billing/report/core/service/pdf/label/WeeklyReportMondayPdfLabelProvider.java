package ee.qrent.billing.report.core.service.pdf.label;

import ee.qrent.billing.driver.api.in.request.CommunicationLanguageIn;
import lombok.experimental.UtilityClass;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class WeeklyReportMondayPdfLabelProvider {

  private static Map<String, Map<String, String>> languageVsLabelsMap = new HashMap<>(5);
  private static Map<String, String> mapEst;
  private static Map<String, String> mapRus;
  private static Map<String, String> mapEng;

  public static String REPORT_NAME_KEY = "REPORT_NAME_KEY";
  public static String DRIVER_LABEL_KEY = "DRIVER_LABEL_KEY";

  static {
    mapRus =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "ОТЧЕТ ЗА ПОНЕДЕЛЬНИК"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Водитель"));

    mapEng =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "MONDAY REPORT"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Driver"));

    mapEst =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "ESMASPÄEVANE ARUANNE"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Juht"));

    languageVsLabelsMap.put(CommunicationLanguageIn.RUS.name(), mapRus);
    languageVsLabelsMap.put(CommunicationLanguageIn.EST.name(), mapEst);
    languageVsLabelsMap.put(CommunicationLanguageIn.ENG.name(), mapEng);
  }

  public static String getLabel(final String language, final String key) {

    return languageVsLabelsMap.get(language).get(key);
  }
}
