package ee.qrent.billing.e2e.helper;

import ee.qrent.billing.driver.api.in.request.CommunicationLanguageIn;
import ee.qrent.billing.driver.api.in.request.DriverAddRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.lang.Boolean.TRUE;

public class DriverHelper {

  public static DriverAddRequest getValidAddRequest(
      final Long qFirmId, final Long callSignId, final Long recommendedByDriverId) {

    final var addRequest = new DriverAddRequest();
    addRequest.setActive(true);
    addRequest.setFirstName("it_first_name_1");
    addRequest.setLastName("it_last_name_1");
    addRequest.setCommunicationLanguage(CommunicationLanguageIn.ENG);
    addRequest.setTaxNumber(11111111111l);
    addRequest.setPhone("it_+37211111111");
    addRequest.setEmail("it_driver1@gmail.com");
    addRequest.setLegalEntityType("COMPANY");
    addRequest.setCompanyName("it_company_name_1");
    addRequest.setCompanyCeoFirstName("it_company_ceo_first_name_1");
    addRequest.setCompanyCeoLastName("it_company_ceo_last_name_1");
    addRequest.setCompanyCeoTaxNumber(11111111112l);
    addRequest.setRegNumber("it_reg_number_1");
    addRequest.setCompanyAddress("it_Tallinn Lootsa 11z");
    addRequest.setCompanyVat("it_111111111111");
    addRequest.setDriverLicenseNumber("it_11111111111");
    addRequest.setDriverLicenseExp(LocalDate.now().plus(5, ChronoUnit.YEARS));
    addRequest.setTaxiLicense("it_taxi_licen_1");
    addRequest.setAddress("it_address_1");
    addRequest.setNeedInvoicesByEmail(true);
    addRequest.setNeedFee(true);
    addRequest.setNeedReport(true);
    addRequest.setHasRequiredObligation(TRUE);
    addRequest.setRequiredObligation(BigDecimal.valueOf(200));
    addRequest.setByTelegram(true);
    addRequest.setByEmail(true);
    addRequest.setByWhatsApp(true);
    addRequest.setByViber(true);
    addRequest.setBySms(true);
    addRequest.setByPhone(true);
    addRequest.setDeposit(BigDecimal.valueOf(500));
    addRequest.setBoltDriverIdentifier("it_bolt_driver_id_1");
    addRequest.setBoltIndividualIdentifier("it_bolt_individual_id_1");
    addRequest.setQFirmId(qFirmId);
    addRequest.setCallSignId(callSignId);
    addRequest.setRecommendedByDriverId(recommendedByDriverId);

    return addRequest;
  }
}
