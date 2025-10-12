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

  public static String OBLIGATION_HEADER_TEXT_PART_1_LABEL_KEY =
      "OBLIGATION_HEADER_TEXT_PART_1_LABEL_KEY";
  public static String OBLIGATION_HEADER_TEXT_PART_2_LABEL_KEY =
      "OBLIGATION_HEADER_TEXT_PART_2_LABEL_KEY";
  public static String RENT_HEADER_TEXT_LABEL_KEY = "RENT_HEADER_TEXT_LABEL_KEY";
  public static String RENT_CLARIFICATION_TEXT_LABEL_KEY = "RENT_CLARIFICATION_TEXT_LABEL_KEY";
  public static String BONUS_PROGRAM_LABEL_KEY = "BONUS_PROGRAM_LABEL_KEY";
  public static String BONUS_PROGRAM_REL_PARTNER_LABEL_KEY = "BONUS_PROGRAM_REL_PARTNER_LABEL_KEY";
  public static String BONUS_PROGRAM_BOLT_RIDES_LABEL_KEY = "BONUS_PROGRAM_BOLT_RIDES_LABEL_KEY";
  public static String BONUS_PROGRAM_FRIEND_REF_LABEL_KEY = "BONUS_PROGRAM_FRIEND_REF_LABEL_KEY";
  public static String RENT_ADJUSTMENT_LABEL_KEY = "RENT_ADJUSTMENT_LABEL_KEY";
  public static String RENT_ADJUSTMENT_BOLT_INCOME_1_LABEL_KEY =
      "RENT_ADJUSTMENT_BOLT_INCOME_1_LABEL_KEY";
  public static String RENT_ADJUSTMENT_BOLT_INCOME_2_LABEL_KEY =
      "RENT_ADJUSTMENT_BOLT_INCOME_2_LABEL_KEY";
  public static String OTHER_OBLIGATIONS_LABEL_KEY = "OTHER_OBLIGATIONS_LABEL_KEY";
  public static String ADD_INN_INSURANCE_LABEL_KEY = "ADD_INN_INSURANCE_LABEL_KEY";

  static {
    mapRus =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "евро"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "ОТЧЕТ НА ПОНЕДЕЛЬНИК"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Арендатор"),
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
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_4_LABEL_KEY, "Пожалуйста, "),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_5_LABEL_KEY, "оплати, "),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_6_LABEL_KEY, "эту сумму "),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_7_LABEL_KEY, "до 16:00 "),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_8_LABEL_KEY, "четверга (изменится в будущем), чтобы "),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_9_LABEL_KEY, "активировать бонусные кампании "),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_10_LABEL_KEY, "на следующую неделю "),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_HEADER_TEXT_PART_1_LABEL_KEY, "Ниже детальная информация по "),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_HEADER_TEXT_PART_2_LABEL_KEY, "твоим обязательствам"),
            new AbstractMap.SimpleEntry<>(RENT_HEADER_TEXT_LABEL_KEY, "Арендная плата всего: "),
            new AbstractMap.SimpleEntry<>(
                RENT_CLARIFICATION_TEXT_LABEL_KEY, "Аренда за текущую неделю"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_LABEL_KEY, "Кампания"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_REL_PARTNER_LABEL_KEY, "Надежный партнер"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_BOLT_RIDES_LABEL_KEY, "«Поездки Bolt"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_FRIEND_REF_LABEL_KEY, "Приведи друга"),
            new AbstractMap.SimpleEntry<>(RENT_ADJUSTMENT_LABEL_KEY, "Коррекция аренды"),
            new AbstractMap.SimpleEntry<>(RENT_ADJUSTMENT_BOLT_INCOME_1_LABEL_KEY, "Заработок"),
            new AbstractMap.SimpleEntry<>(RENT_ADJUSTMENT_BOLT_INCOME_2_LABEL_KEY, "Bolt"),
            new AbstractMap.SimpleEntry<>(OTHER_OBLIGATIONS_LABEL_KEY, "Прочие обязательства"),
            new AbstractMap.SimpleEntry<>(
                ADD_INN_INSURANCE_LABEL_KEY,
                "ДВС за текущую неделю (дополнительное внутреннее страхование)"));

    ///  /////////////////////////////////////////////

    mapEng =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "euro"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "MONDAY REPORT"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Tenant"),
            new AbstractMap.SimpleEntry<>(PERSONAL_NUMBER_LABEL_KEY, "Personal code"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Reported Week"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Call Sign in Q System"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_LABEL_KEY, "Data as of the end of Thursday last week"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_1_LABEL_KEY, "You"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_TEXT_PART_2_COMPLETED_LABEL_KEY, "have fulfilled"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_TEXT_PART_2_NOT_COMPLETED_LABEL_KEY, "did not fulfill"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_3_LABEL_KEY, "your"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_4_LABEL_KEY, "obligations"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_TEXT_PART_5_LABEL_KEY, "for the previous week"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_1_DEBT_LABEL_KEY, "Your outstanding balance"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_1_PREPAYMENT_LABEL_KEY, "Your prepaid balance"),
            new AbstractMap.SimpleEntry<>(THURSDAY_BALANCE_TEXT_PART_2_LABEL_KEY, "as of the"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_3_LABEL_KEY, "end of Thursday last week was:"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY, "As a token of our appreciation,"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY, "we have activated"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY, "all our"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY, "bonus campaigns"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY, "for the current week."),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY, "Unfortunately, our"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY, "bonus campaigns will not be"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_INACTIVE_TEXT_PART_3_LABEL_KEY, "to you"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_4_LABEL_KEY, "available"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_5_LABEL_KEY, "for the current week."),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_1_LABEL_KEY, "Your "),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_2_LABEL_KEY, " current week obligations"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_3_LABEL_KEY, " at the moment are:"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_4_LABEL_KEY, "Please"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_5_LABEL_KEY, "make the "),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_6_LABEL_KEY, "payment"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_7_LABEL_KEY, "by 16:00 Thursday"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_8_LABEL_KEY, "(will change in future),"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_9_LABEL_KEY, "to activate the bonus campaigns"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_10_LABEL_KEY, "for the next week"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_HEADER_TEXT_PART_1_LABEL_KEY,
                "Below you can find detailed information about your"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_HEADER_TEXT_PART_2_LABEL_KEY, " current week obligations:"),
            new AbstractMap.SimpleEntry<>(RENT_HEADER_TEXT_LABEL_KEY, "Total rental fee:"),
            new AbstractMap.SimpleEntry<>(
                RENT_CLARIFICATION_TEXT_LABEL_KEY, "Current week rental fee"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_LABEL_KEY, "Campaign "),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_REL_PARTNER_LABEL_KEY, "Reliable Partner"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_BOLT_RIDES_LABEL_KEY, "Bolt Rides"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_FRIEND_REF_LABEL_KEY, "Bring a friend"),
            new AbstractMap.SimpleEntry<>(RENT_ADJUSTMENT_LABEL_KEY, "??Коррекция аренды"),
            new AbstractMap.SimpleEntry<>(RENT_ADJUSTMENT_BOLT_INCOME_1_LABEL_KEY, "??Заработок"),
            new AbstractMap.SimpleEntry<>(RENT_ADJUSTMENT_BOLT_INCOME_2_LABEL_KEY, "Bolt"),
            new AbstractMap.SimpleEntry<>(OTHER_OBLIGATIONS_LABEL_KEY, "??Прочие обязательства"),
            new AbstractMap.SimpleEntry<>(
                ADD_INN_INSURANCE_LABEL_KEY,
                "??ДВС за текущую неделю (дополнительное внутреннее страхование)"));

    /////////////////////////////////////////////////////////////////////////////////////////

    mapEst =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "euro"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "ESMASPÄEVANE ARUANNE"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Rentnik"),
            new AbstractMap.SimpleEntry<>(PERSONAL_NUMBER_LABEL_KEY, "Isikukood"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Aruande nädal"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Kutsung Q süsteemis"),
            new AbstractMap.SimpleEntry<>(RENTED_CAR_LABEL_KEY, "Hetkel kasutuses olev sõiduk"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_LABEL_KEY, "Andmed eelmise nädala neljapäeva lõpu seisuga:"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_1_LABEL_KEY, "Sa"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_2_COMPLETED_LABEL_KEY, "täitsid"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_TEXT_PART_2_NOT_COMPLETED_LABEL_KEY, "ei täitnud"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_3_LABEL_KEY, "oma"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_4_LABEL_KEY, "kohustused"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_TEXT_PART_5_LABEL_KEY, "eelmise nädala eest"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_TEXT_PART_6_LABEL_KEY, "õigeaegselt ja täies mahus."),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_1_DEBT_LABEL_KEY, "Sinu võlg eelmise"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_1_PREPAYMENT_LABEL_KEY, "Sinu ettemaks eelmise"),
            new AbstractMap.SimpleEntry<>(THURSDAY_BALANCE_TEXT_PART_2_LABEL_KEY, "nädala"),
            new AbstractMap.SimpleEntry<>(
                THURSDAY_BALANCE_TEXT_PART_3_LABEL_KEY, "neljapäeva lõpu seisuga oli:"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_TEXT_PART_1_LABEL_KEY, "Tänutäheks"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_2_LABEL_KEY, "oleme käesoleval"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_ACTIVE_TEXT_PART_3_LABEL_KEY, "nädalal"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_4_LABEL_KEY, "aktiveerinud kõik"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_ACTIVE_TEXT_PART_5_LABEL_KEY, "meie boonuskampaaniad."),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_INACTIVE_TEXT_PART_1_LABEL_KEY, "Kahjuks"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_2_LABEL_KEY, "ei ole meie boonuskampaaniad"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_INACTIVE_TEXT_PART_3_LABEL_KEY, "sellel"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_4_LABEL_KEY, "nädalal sinu jaoks"),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_INACTIVE_TEXT_PART_5_LABEL_KEY, "saadaval."),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_1_LABEL_KEY, "Sinu"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_2_LABEL_KEY, "käesoleva nädala kohustused"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_3_LABEL_KEY, "on hetkel:"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_4_LABEL_KEY, "Palun"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_MONDAY_TEXT_PART_5_LABEL_KEY, "tasu "),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_6_LABEL_KEY, "see summa hiljemalt"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_7_LABEL_KEY, "neljapäeva kella 16:00-ni "),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_8_LABEL_KEY, "(muutub tulevikul),"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_9_LABEL_KEY, "et aktiveerida boonuskampaaniad "),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_MONDAY_TEXT_PART_10_LABEL_KEY, "järgmiseks nädalaks"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_HEADER_TEXT_PART_1_LABEL_KEY, "Allpool on detailne teave "),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_HEADER_TEXT_PART_2_LABEL_KEY, "sinu kohustuste kohta:"),
            new AbstractMap.SimpleEntry<>(RENT_HEADER_TEXT_LABEL_KEY, "Renditasu kokku:"),
            new AbstractMap.SimpleEntry<>(
                RENT_CLARIFICATION_TEXT_LABEL_KEY, "Käesoleva nädala renditasu"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_LABEL_KEY, "kampaania "),
            new AbstractMap.SimpleEntry<>(
                BONUS_PROGRAM_REL_PARTNER_LABEL_KEY, "Usaldusväärne partner"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_BOLT_RIDES_LABEL_KEY, "Bolt sõidud"),
            new AbstractMap.SimpleEntry<>(BONUS_PROGRAM_FRIEND_REF_LABEL_KEY, "Soovita sõbrale"),
            new AbstractMap.SimpleEntry<>(RENT_ADJUSTMENT_LABEL_KEY, "?? Коррекция аренды"),
            new AbstractMap.SimpleEntry<>(RENT_ADJUSTMENT_BOLT_INCOME_1_LABEL_KEY, "??Заработок"),
            new AbstractMap.SimpleEntry<>(RENT_ADJUSTMENT_BOLT_INCOME_2_LABEL_KEY, "Bolt"),
            new AbstractMap.SimpleEntry<>(OTHER_OBLIGATIONS_LABEL_KEY, "??Прочие обязательства"),
            new AbstractMap.SimpleEntry<>(
                ADD_INN_INSURANCE_LABEL_KEY,
                "??ДВС за текущую неделю (дополнительное внутреннее страхование)"));

    languageVsLabelsMap.put(CommunicationLanguageIn.RUS.name(), mapRus);
    languageVsLabelsMap.put(CommunicationLanguageIn.EST.name(), mapEst);
    languageVsLabelsMap.put(CommunicationLanguageIn.ENG.name(), mapEng);
  }

  public static String getLabel(final String language, final String key) {

    return languageVsLabelsMap.get(language).get(key);
  }
}
