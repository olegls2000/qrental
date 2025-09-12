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
  public static String ID_NUMBER_LABEL_KEY = "ID_NUMBER_LABEL_KEY";
  public static String REPORTED_WEEK_LABEL_KEY = "REPORTED_WEEK_LABEL_KEY";
  public static String CALL_SIGN_LABEL_KEY = "CALL_SIGN_LABEL_KEY";
  public static String RENTED_CAR_LABEL_KEY = "RENTED_CAR_LABEL_KEY";
  public static String CREATED_ON_LABEL_KEY = "CREATED_ON_LABEL_KEY";

  static {
    mapRus =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "ОТЧЕТ ЗА ПОНЕДЕЛЬНИК"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Водитель"),
            new AbstractMap.SimpleEntry<>(
                ID_NUMBER_LABEL_KEY, "Персональный идентификационный номер"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Отчетная неделя"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Позывной"),
            new AbstractMap.SimpleEntry<>(RENTED_CAR_LABEL_KEY, "Арендованный автомобиль"),
            new AbstractMap.SimpleEntry<>(CREATED_ON_LABEL_KEY, "Создано на"));

    mapEng =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "MONDAY REPORT"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Driver"),
            new AbstractMap.SimpleEntry<>(ID_NUMBER_LABEL_KEY, "Personal identification number"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Reported Week"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Call Sign"),
            new AbstractMap.SimpleEntry<>(RENTED_CAR_LABEL_KEY, "Rented Car"),
            new AbstractMap.SimpleEntry<>(CREATED_ON_LABEL_KEY, "Created on"));

    mapEst =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "ESMASPÄEVANE ARUANNE"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Juht"),
            new AbstractMap.SimpleEntry<>(ID_NUMBER_LABEL_KEY, "Isikukood"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Aruande nädal"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Kutsung"),
            new AbstractMap.SimpleEntry<>(RENTED_CAR_LABEL_KEY, "Renditud auto"),
            new AbstractMap.SimpleEntry<>(CREATED_ON_LABEL_KEY, "Loodud"));

    languageVsLabelsMap.put(CommunicationLanguageIn.RUS.name(), mapRus);
    languageVsLabelsMap.put(CommunicationLanguageIn.EST.name(), mapEst);
    languageVsLabelsMap.put(CommunicationLanguageIn.ENG.name(), mapEng);
  }

  public static String getLabel(final String language, final String key) {

    return languageVsLabelsMap.get(language).get(key);
  }
}
