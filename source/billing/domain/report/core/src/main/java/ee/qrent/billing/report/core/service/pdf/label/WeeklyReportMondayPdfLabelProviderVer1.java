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
  public static String ID_NUMBER_LABEL_KEY = "ID_NUMBER_LABEL_KEY";
  public static String REPORTED_WEEK_LABEL_KEY = "REPORTED_WEEK_LABEL_KEY";
  public static String CALL_SIGN_LABEL_KEY = "CALL_SIGN_LABEL_KEY";
  public static String RENTED_CAR_LABEL_KEY = "RENTED_CAR_LABEL_KEY";
  public static String CREATED_ON_LABEL_KEY = "CREATED_ON_LABEL_KEY";
  public static String FINANCIAL_COMMENT_KEY = "FINANCIAL_COMMENT_KEY";
  public static String DEPOSIT_KEY = "DEPOSIT_KEY";
  public static String PAID_DEPOSIT_KEY = "PAID_DEPOSIT_KEY";
  public static String BALANCE_KEY = "BALANCE_KEY";
  public static String BALANCE_END_WEEK_EXPLANATION_KEY = "BALANCE_END_WEEK_EXPLANATION_KEY";
  public static String BALANCE_MONDAY_EXPLANATION_KEY = "BALANCE_MONDAY_EXPLANATION_KEY";
  public static String DEBT_KEY = "DEBT_KEY";
  public static String DEBT_EXPLANATION_KEY = "DEBT_EXPLANATION_KEY";
  public static String OBLIGATION_KEY = "OBLIGATION_KEY";
  public static String OBLIGATION_COMPLETED_EXPLANATION_KEY =
      "OBLIGATION_COMPLETED_EXPLANATION_KEY";
  public static String OBLIGATION_COMPLETED_WITH_DELAY_EXPLANATION_KEY =
      "OBLIGATION_COMPLETED_WITH_DELAY_EXPLANATION_KEY";
  public static String OBLIGATION_NOT_COMPLETED_EXPLANATION_KEY =
      "OBLIGATION_NOT_COMPLETED_EXPLANATION_KEY";
  public static String TRANSACTION_TABLE_NAME_KEY = "TRANSACTION_TABLE_NAME_KEY";
  public static String TRANSACTION_TABLE_TYPE_COLUMN_KEY = "TRANSACTION_TABLE_TYPE_COLUMN_KEY";
  public static String TRANSACTION_TABLE_AMOUNT_COLUMN_KEY = "TRANSACTION_TABLE_AMOUNT_COLUMN_KEY";
  public static String REPORT_COMMENT_KEY = "REPORT_COMMENT_KEY";

  static {
    mapRus =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "евро"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "ОТЧЕТ НА ПОНЕДЕЛЬНИК"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Водитель"),
            new AbstractMap.SimpleEntry<>(
                ID_NUMBER_LABEL_KEY, "Личный код"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Отчетная неделя"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Позывной в системе Q"),
            new AbstractMap.SimpleEntry<>(RENTED_CAR_LABEL_KEY, "Используемый в данный момент автомобиль"),
            new AbstractMap.SimpleEntry<>(CREATED_ON_LABEL_KEY, "Создано на"),
            new AbstractMap.SimpleEntry<>(
                FINANCIAL_COMMENT_KEY, "По нашим данным Ваше финансовое состояние"),
            new AbstractMap.SimpleEntry<>(DEPOSIT_KEY, "Депозит"),
            new AbstractMap.SimpleEntry<>(PAID_DEPOSIT_KEY, "Оплаченный депозит"),
            new AbstractMap.SimpleEntry<>(BALANCE_KEY, "Баланс"),
            new AbstractMap.SimpleEntry<>(
                BALANCE_END_WEEK_EXPLANATION_KEY, "в конце отчетной недели"),
            new AbstractMap.SimpleEntry<>(
                BALANCE_MONDAY_EXPLANATION_KEY, "в понедельник после отчетной недели"),
            new AbstractMap.SimpleEntry<>(DEBT_KEY, "Долг"),
            new AbstractMap.SimpleEntry<>(DEBT_EXPLANATION_KEY, "в конце отчетной недели"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_KEY, "Обязательство"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_COMPLETED_EXPLANATION_KEY,
                "Твои обязательства были выполнены своевременно и в полном объеме – согласно условиям твоего договора"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_COMPLETED_WITH_DELAY_EXPLANATION_KEY,
                "Твои обязательства были выполнены в полном объеме, но с опозданием"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_NOT_COMPLETED_EXPLANATION_KEY,
                "Твои обязательства не были выполнены своевременно и в полном объеме"),
            new AbstractMap.SimpleEntry<>(
                TRANSACTION_TABLE_NAME_KEY, "Отчетные недельные транзакции"),
            new AbstractMap.SimpleEntry<>(TRANSACTION_TABLE_TYPE_COLUMN_KEY, "Тип"),
            new AbstractMap.SimpleEntry<>(TRANSACTION_TABLE_AMOUNT_COLUMN_KEY, "Сумма"),
            new AbstractMap.SimpleEntry<>(
                REPORT_COMMENT_KEY,
                "Данные о корректировках с заработков или обязательств из приложений (Bolt, Forus) будут внесены в твой баланс до 12:00 вторника этой недели. Свой обновленный отчет и сальдо ты получишь вскоре после этого."));

    mapEng =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "euro"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "MONDAY REPORT"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Driver"),
            new AbstractMap.SimpleEntry<>(ID_NUMBER_LABEL_KEY, "Personal identification number"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Reported Week"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Call Sign"),
            new AbstractMap.SimpleEntry<>(RENTED_CAR_LABEL_KEY, "Rented Car"),
            new AbstractMap.SimpleEntry<>(CREATED_ON_LABEL_KEY, "Created on"),
            new AbstractMap.SimpleEntry<>(
                FINANCIAL_COMMENT_KEY, "According to our data, your financial state is"),
            new AbstractMap.SimpleEntry<>(DEPOSIT_KEY, "Deposit"),
            new AbstractMap.SimpleEntry<>(PAID_DEPOSIT_KEY, "Paid Deposit"),
            new AbstractMap.SimpleEntry<>(BALANCE_KEY, "Balance"),
            new AbstractMap.SimpleEntry<>(
                BALANCE_END_WEEK_EXPLANATION_KEY, "on the end of Reported Week"),
            new AbstractMap.SimpleEntry<>(
                BALANCE_MONDAY_EXPLANATION_KEY, "on Monday after Reported Week"),
            new AbstractMap.SimpleEntry<>(DEBT_KEY, "Debt"),
            new AbstractMap.SimpleEntry<>(DEBT_EXPLANATION_KEY, "on the end of Reported Week"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_KEY, "Obligation"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_COMPLETED_EXPLANATION_KEY,
                "Your obligations were fulfilled on time and in full – according to the terms of your contract"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_COMPLETED_WITH_DELAY_EXPLANATION_KEY,
                "Your obligations were fulfilled in full, but late."),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_NOT_COMPLETED_EXPLANATION_KEY,
                "Your obligations were not fulfilled in a timely manner and in full"),
            new AbstractMap.SimpleEntry<>(TRANSACTION_TABLE_NAME_KEY, "Reported Week Transactions"),
            new AbstractMap.SimpleEntry<>(TRANSACTION_TABLE_TYPE_COLUMN_KEY, "Type"),
            new AbstractMap.SimpleEntry<>(TRANSACTION_TABLE_AMOUNT_COLUMN_KEY, "Amount"),
            new AbstractMap.SimpleEntry<>(
                REPORT_COMMENT_KEY,
                "Adjustments to earnings or liabilities from applications (Bolt, Forus) will be entered into your balance by 12:00 Tuesday of this week. You will receive your updated report and balance shortly thereafter."));

    mapEst =
        Map.ofEntries(
                new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "euro"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "ESMASPÄEVANE ARUANNE"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Juht"),
            new AbstractMap.SimpleEntry<>(ID_NUMBER_LABEL_KEY, "Isikukood"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Aruande nädal"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Kutsung"),
            new AbstractMap.SimpleEntry<>(RENTED_CAR_LABEL_KEY, "Renditud auto"),
            new AbstractMap.SimpleEntry<>(CREATED_ON_LABEL_KEY, "Loodud"),
            new AbstractMap.SimpleEntry<>(
                FINANCIAL_COMMENT_KEY, "Meie andmetel on teie finantsseisund"),
            new AbstractMap.SimpleEntry<>(DEPOSIT_KEY, "Deposiit"),
            new AbstractMap.SimpleEntry<>(PAID_DEPOSIT_KEY, "Tasutud tagatisraha"),
            new AbstractMap.SimpleEntry<>(BALANCE_KEY, "Tasakaal"),
            new AbstractMap.SimpleEntry<>(BALANCE_END_WEEK_EXPLANATION_KEY, "aruandenädala lõpus"),
            new AbstractMap.SimpleEntry<>(
                BALANCE_MONDAY_EXPLANATION_KEY, "esmaspäeval pärast aruandenädalat"),
            new AbstractMap.SimpleEntry<>(DEBT_KEY, "Võlg"),
            new AbstractMap.SimpleEntry<>(DEBT_EXPLANATION_KEY, "aruandenädala lõpus"),
            new AbstractMap.SimpleEntry<>(OBLIGATION_KEY, "Kohustus"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_COMPLETED_EXPLANATION_KEY,
                "Teie kohustused täideti õigeaegselt ja täielikult – vastavalt teie lepingu tingimustele"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_COMPLETED_WITH_DELAY_EXPLANATION_KEY,
                "Teie kohustused täideti täielikult, kuid hilinenult"),
            new AbstractMap.SimpleEntry<>(
                OBLIGATION_NOT_COMPLETED_EXPLANATION_KEY,
                "Teie kohustusi ei täidetud õigeaegselt ja täielikult"),
            new AbstractMap.SimpleEntry<>(TRANSACTION_TABLE_NAME_KEY, "Aruandenädala tehingud"),
            new AbstractMap.SimpleEntry<>(TRANSACTION_TABLE_TYPE_COLUMN_KEY, "Tüüp"),
            new AbstractMap.SimpleEntry<>(TRANSACTION_TABLE_AMOUNT_COLUMN_KEY, "Summa"),
            new AbstractMap.SimpleEntry<>(
                REPORT_COMMENT_KEY,
                "Rakenduste (Bolt, Forus) tulude või kohustuste korrigeerimised kantakse teie saldole selle nädala teisipäevaks kell 12.00. Saate oma uuendatud aruande ja saldo varsti pärast seda."));

    languageVsLabelsMap.put(CommunicationLanguageIn.RUS.name(), mapRus);
    languageVsLabelsMap.put(CommunicationLanguageIn.EST.name(), mapEst);
    languageVsLabelsMap.put(CommunicationLanguageIn.ENG.name(), mapEng);
  }

  public static String getLabel(final String language, final String key) {

    return languageVsLabelsMap.get(language).get(key);
  }
}
