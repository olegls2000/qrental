package ee.qrent.billing.report.core.service.pdf.label;

import ee.qrent.billing.driver.api.in.request.CommunicationLanguageIn;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WeeklyReportPdfLabelProviderFriday {

  private static Map<String, Map<String, String>> languageVsLabelsMap = new HashMap<>(5);
  private static Map<String, String> mapEst;
  private static Map<String, String> mapRus;
  private static Map<String, String> mapEng;
  public static String REPORT_NAME_KEY = "REPORT_NAME_KEY";
  public static String BONUS_REPORT_HEADER_LABEL_KEY = "BONUS_REPORT_HEADER_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY";

  static {
    mapRus =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(
                REPORT_NAME_KEY, "РАССЫЛКА НА ЧЕТВЕРГ - 18:00"),
            new AbstractMap.SimpleEntry<>(BONUS_REPORT_HEADER_LABEL_KEY, "Отчет по оплате и бонусам:"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY,
                "Так держать! \n В знак нашей благодарности"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY, "на следующей неделе."));

    mapEng =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "THURSDAY NEWSLETTER - 18:00"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY,
                "Keep it up! \n As a token of our appreciation,"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY, "for the upcoming  week."));

    mapEst =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "NELJAPÄEVA INFOKIRI - 18:00"),
            new AbstractMap.SimpleEntry<>(
                    BONUS_REPORT_HEADER_LABEL_KEY, "Andmed eelmise nädala neljapäeva lõpu seisuga"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY, "Jätka samas vaimus! \n Tänutäheks"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY, "järgmiseks nädalaks."));

    languageVsLabelsMap.put(CommunicationLanguageIn.RUS.name(), mapRus);
    languageVsLabelsMap.put(CommunicationLanguageIn.EST.name(), mapEst);
    languageVsLabelsMap.put(CommunicationLanguageIn.ENG.name(), mapEng);
  }

  public static String getLabelFromFriday(final String language, final String key) {

    return languageVsLabelsMap.get(language).getOrDefault(key, "N/A");
  }
}
