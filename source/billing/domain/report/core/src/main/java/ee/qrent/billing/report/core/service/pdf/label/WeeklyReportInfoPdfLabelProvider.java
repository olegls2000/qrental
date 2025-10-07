package ee.qrent.billing.report.core.service.pdf.label;

import ee.qrent.billing.driver.api.in.request.CommunicationLanguageIn;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;

@UtilityClass
public class WeeklyReportInfoPdfLabelProvider {

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
  public static String INFO_LABEL_KEY = "INFO_LABEL_KEY";

  static {
    mapRus =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "евро"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "Уведомительное письмо"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Водитель"),
            new AbstractMap.SimpleEntry<>(ID_NUMBER_LABEL_KEY, "Личный код"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Отчетная неделя"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Позывной в системе Q"),
            new AbstractMap.SimpleEntry<>(
                RENTED_CAR_LABEL_KEY, "Используемый в данный момент автомобиль"),
            new AbstractMap.SimpleEntry<>(CREATED_ON_LABEL_KEY, "Создано на"),
            new AbstractMap.SimpleEntry<>(
                INFO_LABEL_KEY,
                "Дорогой партнёр!\n"
                    + "\n"
                    + "Начиная со следующего понедельника, мы запускаем новую информационную рассылку.\n"
                    + "\n"
                    + "Она будет поступать на твой адрес электронной почты и содержать актуальную информацию о твоих обязательствах — в частности, о том, сколько нужно оплатить сейчас, чтобы получить бонусы по нашим кампаниям на следующей неделе.\n"
                    + "\n"
                    + "Обращаем внимание, что на первом этапе рассылка будет работать в тестовом режиме. Поэтому просим тебя не пугаться, если заметишь ошибки или неточности, а просто сообщить нам об этом.\n"
                    + "\n"
                    + "С твоей помощью мы сможем довести систему до совершенства и в дальнейшем предоставлять тебе только точную и полную информацию!\n"
                    + "\n"
                    + "Спасибо за сотрудничество и поддержку этого проекта!"));
    mapEng =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "euro"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "Notification letter"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Driver"),
            new AbstractMap.SimpleEntry<>(ID_NUMBER_LABEL_KEY, "Personal identification number"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Reported Week"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Call Sign"),
            new AbstractMap.SimpleEntry<>(RENTED_CAR_LABEL_KEY, "Rented Car"),
            new AbstractMap.SimpleEntry<>(CREATED_ON_LABEL_KEY, "Created on"),
            new AbstractMap.SimpleEntry<>(
                INFO_LABEL_KEY,
                "Dear Partner,\n"
                    + "\n"
                    + "Starting next Monday, we will launch our new information newsletter.\n"
                    + "\n"
                    + "It will be sent to your email address and will contain details about your current obligations — specifically, how much you need to pay now in order to receive bonuses from our campaigns next week.\n"
                    + "\n"
                    + "Please note that this will initially be a test version. Therefore, don’t be alarmed if you notice any mistakes or inaccuracies — simply let us know about them right away.\n"
                    + "\n"
                    + "With your help, we’ll be able to perfect the system and provide you with accurate and complete information in the future!\n"
                    + "\n"
                    + "Thank you for your cooperation and your support in this project!"));

    mapEst =
        Map.ofEntries(
            new AbstractMap.SimpleEntry<>(CURRENCY_NAME_KEY, "euro"),
            new AbstractMap.SimpleEntry<>(REPORT_NAME_KEY, "Teavituskiri"),
            new AbstractMap.SimpleEntry<>(DRIVER_LABEL_KEY, "Juht"),
            new AbstractMap.SimpleEntry<>(ID_NUMBER_LABEL_KEY, "Isikukood"),
            new AbstractMap.SimpleEntry<>(REPORTED_WEEK_LABEL_KEY, "Aruande nädal"),
            new AbstractMap.SimpleEntry<>(CALL_SIGN_LABEL_KEY, "Kutsung"),
            new AbstractMap.SimpleEntry<>(RENTED_CAR_LABEL_KEY, "Renditud auto"),
            new AbstractMap.SimpleEntry<>(CREATED_ON_LABEL_KEY, "Loodud"),
            new AbstractMap.SimpleEntry<>(
                INFO_LABEL_KEY,
                "Hea partner!\n"
                    + "\n"
                    + "Alates järgmisest esmaspäevast alustame oma uut infokirja.\n"
                    + "\n"
                    + "See saadetakse sinu e-posti aadressile ning sisaldab teavet sinu kohustuste kohta — ehk kui palju on vaja praegu tasuda, et saada boonuseid meie kampaaniate eest järgmisel nädalal.\n"
                    + "\n"
                    + "Teavitame, et esialgu on tegemist testversiooniga. Seetõttu palume mitte ehmuda, kui märkad infokirjas vigu või ebatäpsusi, vaid anna neist kohe meile teada.\n"
                    + "\n"
                    + "Nii saame tänu sinu abile süsteemi täiustada ja tulevikus edastada sulle alati täpset ja täielikku teavet!\n"
                    + "\n"
                    + "Aitäh koostöö eest ja sinu panuse eest sellesse projekti!"));

    languageVsLabelsMap.put(CommunicationLanguageIn.RUS.name(), mapRus);
    languageVsLabelsMap.put(CommunicationLanguageIn.EST.name(), mapEst);
    languageVsLabelsMap.put(CommunicationLanguageIn.ENG.name(), mapEng);
  }

  public static String getLabel(final String language, final String key) {

    return languageVsLabelsMap.get(language).get(key);
  }
}
