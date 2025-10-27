package ee.qrent.billing.report.core.service.pdf.label;

import ee.qrent.billing.driver.api.in.request.CommunicationLanguageIn;
import lombok.experimental.UtilityClass;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class WeeklyReportPdfLabelProviderTuesday {

  private static Map<String, Map<String, String>> languageVsLabelsMap = new HashMap<>(5);
  private static Map<String, String> mapEst;
  private static Map<String, String> mapRus;
  private static Map<String, String> mapEng;

  public static String REPORT_NAME_KEY = "REPORT_NAME_KEY";
  public static String THURSDAY_LABEL_KEY = "THURSDAY_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY";

  static {
    mapRus =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(
                REPORT_NAME_KEY, "РАССЫЛКА НА СРЕДУ - 16:00"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY, "В знак нашей благодарности"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY, "на текущей неделе."));

    mapEng =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "WEDNESDAY NEWSLETTER - 16:00"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY, "As a token of our appreciation,"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY, "for the current week."));

    mapEst =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "KOLMAPÄEVANE INFOKIRI - 16:00"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_LABEL_KEY, "Andmed eelmise nädala neljapäeva lõpu seisuga"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY, "Tänutäheks"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY, "meie boonuskampaaniad."));

    languageVsLabelsMap.put(CommunicationLanguageIn.RUS.name(), mapRus);
    languageVsLabelsMap.put(CommunicationLanguageIn.EST.name(), mapEst);
    languageVsLabelsMap.put(CommunicationLanguageIn.ENG.name(), mapEng);
  }

  public static String getLabelFromTuesday(final String language, final String key) {

    return languageVsLabelsMap.get(language).getOrDefault(key, "N/A");
  }
}
