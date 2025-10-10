package ee.qrent.billing.report.core.service.pdf.label;

import ee.qrent.billing.driver.api.in.request.CommunicationLanguageIn;
import lombok.experimental.UtilityClass;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class WeeklyReportMondayPdfLabelProviderVer1 {

  private static Map<String, Map<String, String>> languageVsLabelsMap = new HashMap<>(5);
  private static Map<String, String> mapEst;
  private static Map<String, String> mapRus;
  private static Map<String, String> mapEng;

  public static String CURRENCY_NAME_KEY = "CURRENCY_NAME_KEY";
  public static String REPORT_NAME_KEY = "REPORT_NAME_KEY";
  public static String DRIVER_LABEL_KEY = "DRIVER_LABEL_KEY";

  public static String PERSONAL_NUMBER_LABEL_KEY = "PERSONAL_NUMBER_LABEL_KEY";
  public static String REPORTED_WEEK_LABEL_KEY = "REPORTED_WEEK_LABEL_KEY";
  public static String CALL_SIGN_LABEL_KEY = "CALL_SIGN_LABEL_KEY";
  public static String RENTED_CAR_LABEL_KEY = "RENTED_CAR_LABEL_KEY";
  public static String THURSDAY_LABEL_KEY = "THURSDAY_LABEL_KEY";
  public static String OBLIGATION_TEXT_PART_1_LABEL_KEY = "OBLIGATION_TEXT_PART_1_LABEL_KEY";
  public static String OBLIGATION_TEXT_PART_2_COMPLETED_LABEL_KEY =
      "OBLIGATION_TEXT_PART_2_COMPLETED_LABEL_KEY";
  public static String OBLIGATION_TEXT_PART_2_NOT_COMPLETED_LABEL_KEY =
      "OBLIGATION_TEXT_PART_2_NOT_COMPLETED_LABEL_KEY";
  public static String OBLIGATION_TEXT_PART_3_LABEL_KEY = "OBLIGATION_TEXT_PART_3_LABEL_KEY";
  public static String OBLIGATION_TEXT_PART_4_LABEL_KEY = "OBLIGATION_TEXT_PART_4_LABEL_KEY";
  public static String OBLIGATION_TEXT_PART_5_LABEL_KEY = "OBLIGATION_TEXT_PART_5_LABEL_KEY";
  public static String OBLIGATION_TEXT_PART_6_LABEL_KEY = "OBLIGATION_TEXT_PART_6_LABEL_KEY";
  public static String THURSDAY_BALANCE_TEXT_PART_1_DEBT_LABEL_KEY =
      "THURSDAY_BALANCE_TEXT_PART_1_DEBT_LABEL_KEY";
  public static String THURSDAY_BALANCE_TEXT_PART_1_PREPAYMENT_LABEL_KEY =
      "THURSDAY_BALANCE_TEXT_PART_1_PREPAYMENT_LABEL_KEY";
  public static String THURSDAY_BALANCE_TEXT_PART_2_LABEL_KEY =
      "THURSDAY_BALANCE_TEXT_PART_2_LABEL_KEY";
  public static String THURSDAY_BALANCE_TEXT_PART_3_LABEL_KEY =
      "THURSDAY_BALANCE_TEXT_PART_3_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY";
  public static String BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY =
      "BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY";

  public static String BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY =
      "BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY";
  public static String BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY =
      "BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY";
  public static String BONUS_PROGRAM_INACTIVE_TEXT_PART_3_LABEL_KEY =
      "BONUS_PROGRAM_INACTIVE_TEXT_PART_3_LABEL_KEY";
  public static String BONUS_PROGRAM_INACTIVE_TEXT_PART_4_LABEL_KEY =
      "BONUS_PROGRAM_INACTIVE_TEXT_PART_4_LABEL_KEY";
  public static String BONUS_PROGRAM_INACTIVE_TEXT_PART_5_LABEL_KEY =
      "BONUS_PROGRAM_INACTIVE_TEXT_PART_5_LABEL_KEY";

  public static String OBLIGATION_MONDAY_TEXT_PART_1_LABEL_KEY =
      "OBLIGATION_MONDAY_TEXT_PART_1_LABEL_KEY";
  public static String OBLIGATION_MONDAY_TEXT_PART_2_LABEL_KEY =
      "OBLIGATION_MONDAY_TEXT_PART_2_LABEL_KEY";
  public static String OBLIGATION_MONDAY_TEXT_PART_3_LABEL_KEY =
      "OBLIGATION_MONDAY_TEXT_PART_3_LABEL_KEY";
  public static String OBLIGATION_MONDAY_TEXT_PART_4_LABEL_KEY =
      "OBLIGATION_MONDAY_TEXT_PART_4_LABEL_KEY";
  public static String OBLIGATION_MONDAY_TEXT_PART_5_LABEL_KEY =
      "OBLIGATION_MONDAY_TEXT_PART_5_LABEL_KEY";
  public static String OBLIGATION_MONDAY_TEXT_PART_6_LABEL_KEY =
      "OBLIGATION_MONDAY_TEXT_PART_6_LABEL_KEY";
  public static String OBLIGATION_MONDAY_TEXT_PART_7_LABEL_KEY =
      "OBLIGATION_MONDAY_TEXT_PART_7_LABEL_KEY";
  public static String OBLIGATION_MONDAY_TEXT_PART_8_LABEL_KEY =
      "OBLIGATION_MONDAY_TEXT_PART_8_LABEL_KEY";
  public static String OBLIGATION_MONDAY_TEXT_PART_9_LABEL_KEY =
      "OBLIGATION_MONDAY_TEXT_PART_9_LABEL_KEY";
  public static String OBLIGATION_MONDAY_TEXT_PART_10_LABEL_KEY =
      "OBLIGATION_MONDAY_TEXT_PART_10_LABEL_KEY";

  static {
    mapRus =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "евро"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "ОТЧЕТ НА ПОНЕДЕЛЬНИК"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Водитель"),
            new AbstractMap.SimpleEntry<>(PERSONAL_NUMBER_LABEL_KEY, "Личный код"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Позывной в системе Q"),
            new AbstractMap.SimpleEntry<>(
                RENTED_CAR_LABEL_KEY, "Используемый в данный момент автомобиль"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_LABEL_KEY, "Данные на конец четверга прошлой недели"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_1_LABEL_KEY, "Ты"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_2_COMPLETED_LABEL_KEY, "выполнил"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_TEXT_PART_2_NOT_COMPLETED_LABEL_KEY, "не выполнил"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_3_LABEL_KEY, "свои"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_4_LABEL_KEY, "обязательства"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_5_LABEL_KEY, "за прошлую неделю"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_TEXT_PART_6_LABEL_KEY, "своевременно и в полном объеме."),
            new AbstractMap.SimpleEntry<>(THURSDAY_BALANCE_TEXT_PART_1_DEBT_LABEL_KEY, "Твой долг"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_1_PREPAYMENT_LABEL_KEY, "Твоя предоплата"),
            new AbstractMap.SimpleEntry<>(THURSDAY_BALANCE_TEXT_PART_2_LABEL_KEY, "на конец"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_3_LABEL_KEY, "четверга прошлой недели:"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY, "В знак нашей благодарности"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY, "мы активировали"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY, "все наши"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY, "бонусные кампании"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY, "на текущей неделе."),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY, "К сожалению, наши"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY, "бонусные кампании не будут"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_INACTIVE_TEXT_PART_3_LABEL_KEY, "для тебя"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_INACTIVE_TEXT_PART_4_LABEL_KEY, "доступны"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_5_LABEL_KEY, "на текущей неделе."),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_1_LABEL_KEY, "Cейчас"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_2_LABEL_KEY, "твои обязательства"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_3_LABEL_KEY, "за текущую неделю составляют:"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_4_LABEL_KEY, "Пожалуйста,"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_5_LABEL_KEY, "оплати,"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_6_LABEL_KEY, "эту сумму"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_7_LABEL_KEY, "до 16:00"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_8_LABEL_KEY, "завтрашнего дня, чтобы"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_9_LABEL_KEY, "активировать бонусные кампании"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_10_LABEL_KEY, "на следующую неделю"));

    ///  /////////////////////////////////////////////

    mapEng =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "euro"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "MONDAY REPORT"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Driver"),
            new AbstractMap.SimpleEntry<>(
                PERSONAL_NUMBER_LABEL_KEY, "Personal identification number"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Reported Week"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Call Sign in Q System"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_LABEL_KEY, "?? Данные на конец четверга прошлой недели"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_1_LABEL_KEY, "?? Ты"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_TEXT_PART_2_COMPLETED_LABEL_KEY, "?? выполнил"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_TEXT_PART_2_NOT_COMPLETED_LABEL_KEY, "?? не выполнил"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_3_LABEL_KEY, "?? свои"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_4_LABEL_KEY, "?? обязательства"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_5_LABEL_KEY, "?? за прошлую неделю"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_1_DEBT_LABEL_KEY, "?? Твой долг"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_1_PREPAYMENT_LABEL_KEY, "?? Твоя предоплата"),
            new AbstractMap.SimpleEntry<>(THURSDAY_BALANCE_TEXT_PART_2_LABEL_KEY, "?? на конец"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_3_LABEL_KEY, "?? четверга прошлой недели:"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY, "?? В знак нашей благодарности"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY, "?? мы активировали"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY, "?? все наши"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY, "?? бонусные кампании"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY, "?? на текущей неделе."),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY, "?? К сожалению, наши"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY, "?? бонусные кампании не будут"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_3_LABEL_KEY, "?? для тебя"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_4_LABEL_KEY, "?? доступны"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_5_LABEL_KEY, "?? на текущей неделе."),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_1_LABEL_KEY, "?? Cейчас"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_2_LABEL_KEY, "?? твои обязательства"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_3_LABEL_KEY, "?? за текущую неделю составляют:"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_4_LABEL_KEY, "?? Пожалуйста,"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_5_LABEL_KEY, "?? оплати,"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_6_LABEL_KEY, "?? эту сумму"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_7_LABEL_KEY, "?? до 16:00"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_8_LABEL_KEY, "?? завтрашнего дня, чтобы"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_9_LABEL_KEY, "?? активировать бонусные кампании"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_10_LABEL_KEY, "?? на следующую неделю"));
    /////////////////////////////////////////////////////////////////////////////////////////

    mapEst =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "euro"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "ESMASPÄEVANE ARUANNE"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Juht"),
            new AbstractMap.SimpleEntry<>(PERSONAL_NUMBER_LABEL_KEY, "Isikukood"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Aruande nädal"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "?? Kutsung"),
            new AbstractMap.SimpleEntry<>(RENTED_CAR_LABEL_KEY, "?? Renditud auto"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_1_LABEL_KEY, "?? Ты"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_TEXT_PART_2_COMPLETED_LABEL_KEY, "?? выполнил"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_TEXT_PART_2_NOT_COMPLETED_LABEL_KEY, "?? не выполнил"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_3_LABEL_KEY, "?? свои"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_4_LABEL_KEY, "?? обязательства"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_5_LABEL_KEY, "?? за прошлую неделю"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_1_DEBT_LABEL_KEY, "?? Твой долг"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_1_PREPAYMENT_LABEL_KEY, "?? Твоя предоплата"),
            new AbstractMap.SimpleEntry<>(THURSDAY_BALANCE_TEXT_PART_2_LABEL_KEY, "?? на конец"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_3_LABEL_KEY, "?? четверга прошлой недели:"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY, "?? В знак нашей благодарности"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY, "?? мы активировали"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY, "?? все наши"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY, "?? бонусные кампании"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY, "?? на текущей неделе."),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY, "?? К сожалению, наши"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY, "?? бонусные кампании не будут"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_3_LABEL_KEY, "?? для тебя"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_4_LABEL_KEY, "?? доступны"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_5_LABEL_KEY, "?? на текущей неделе."),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_1_LABEL_KEY, "?? Cейчас"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_2_LABEL_KEY, "?? твои обязательства"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_3_LABEL_KEY, "?? за текущую неделю составляют:"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_4_LABEL_KEY, "?? Пожалуйста,"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_5_LABEL_KEY, "?? оплати,"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_6_LABEL_KEY, "?? эту сумму"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_7_LABEL_KEY, "?? до 16:00"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_8_LABEL_KEY, "?? завтрашнего дня, чтобы"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_9_LABEL_KEY, "?? активировать бонусные кампании"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_10_LABEL_KEY, "?? на следующую неделю"));

    languageVsLabelsMap.put(CommunicationLanguageIn.RUS.name(), mapRus);
    languageVsLabelsMap.put(CommunicationLanguageIn.EST.name(), mapEst);
    languageVsLabelsMap.put(CommunicationLanguageIn.ENG.name(), mapEng);
  }

  public static String getLabel(final String language, final String key) {

    return languageVsLabelsMap.get(language).get(key);
  }
}
