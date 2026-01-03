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

  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY";

  public static String BONUS_PROGRAM_ACTIVE_COMMENT_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_COMMENT_LABEL_KEY";

  public static String BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY =
      "BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY";
  public static String BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY =
      "BONUS_PROGRAM_INANCTIVE_TEXT_PART_2_LABEL_KEY";

  public static String BONUS_PROGRAM_INACTIVE_COMMENT_LABEL_KEY =
      "BONUS_PROGRAM_INACTIVE_COMMENT_LABEL_KEY";

  static {
    mapRus =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "РАССЫЛКА НА ЧЕТВЕРГ - 18:00"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY,
                "Поздравляем - ты выполнил свои обязательства за текущую неделю"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY, "своевременно и в полном объеме"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY,
                "Твоя предоплата на конец четверга текущей недели составила "),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY,
                "Так держать!\n"
                    + "В знак нашей благодарности мы активировали все наши бонусные кампании на следующей неделе"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY,
                "Сообщаем, что, к сожалению, ты не выполнил свои обязательства за текущую неделю"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY, "своевременно и в полном объеме..."),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_COMMENT_LABEL_KEY, "В знак нашей благодарности мы активировали все наши бонусные кампании на следующей неделе"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_INACTIVE_COMMENT_LABEL_KEY, "По этой причине мы не можем активировать наши бонусные кампании на следующей неделе"));

    mapEng =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "THURSDAY NEWSLETTER - 18:00"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY, "Congratulations — you have fulfilled your obligations for the current week "),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY, "in a timely and complete manner!"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY, "Your prepaid balance as of the end of Thursday this week is "),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY, "As a token of our appreciation, we have activated all our bonus campaigns for the upcoming week."),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY,
                "We have to inform you that, unfortunately, you did not fulfil your obligations for the current week"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY, "on time and in full..."),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_COMMENT_LABEL_KEY, "As a token of our appreciation, we have activated all our bonus campaigns for the upcoming week."),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_INACTIVE_COMMENT_LABEL_KEY, "For this reason, we are unable to activate our bonus campaigns for the upcoming week."));

    mapEst =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "NELJAPÄEVA INFOKIRI - 18:00"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY,
                "Palju õnne — oled täitnud oma kohustused käesoleval nädalal"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY, "õigeaegselt ja täies mahus!"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY,
                "Sinu ettemakse käesoleva nädala neljapäeva lõpuks on: "),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY,
                "Jätka samas vaimus!\n"
                    + "Tänutäheks oleme aktiveerinud kõik meie boonuskampaaniad järgmiseks nädalaks."),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY,
                "Anname teada, et kahjuks ei täitnud Sa oma käesoleva nädala kohustusi"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY, "õigeaegselt ja täies mahus..."),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_COMMENT_LABEL_KEY, "Tänutäheks oleme aktiveerinud kõik meie boonuskampaaniad järgmiseks nädalaks."),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_INACTIVE_COMMENT_LABEL_KEY, "Seetõttu ei saa me järgmisel nädalal aktiveerida meie boonuskampaaniaid."));

    languageVsLabelsMap.put(CommunicationLanguageIn.RUS.name(), mapRus);
    languageVsLabelsMap.put(CommunicationLanguageIn.EST.name(), mapEst);
    languageVsLabelsMap.put(CommunicationLanguageIn.ENG.name(), mapEng);
  }

  public static String getLabelFromFriday(final String language, final String key) {

    return languageVsLabelsMap.get(language).getOrDefault(key, "N/A");
  }
}
