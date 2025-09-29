package ee.qrent.billing.e2e.helper;

import ee.qrent.billing.car.api.in.request.CarAddRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class CarHelper {

  public static CarAddRequest getValidAddRequest() {

    final var releaseDate = LocalDate.now().minus(1, ChronoUnit.YEARS);
    final var insuranceDateStart = releaseDate.plus(45, ChronoUnit.DAYS);
    final var insuranceDateEnd = insuranceDateStart.plusYears(1);
    final var technicalInspectionEnd = releaseDate.plusYears(2);
    final var gasInspectionEnd = releaseDate.plusYears(2);
    final var dateEndLpg = releaseDate.plusYears(2);

    final var request = new CarAddRequest();
    request.setActive(true);
    request.setStatus("IN_USE");
    request.setQRent(true);
    request.setRegNumber("it_AAA");
    request.setVin("it_vin_1111111111");
    request.setReleaseDate(releaseDate);
    request.setManufacturer("it_manufacturer_1");
    request.setModel("it_model_1");
    request.setAppropriation(true);
    request.setElegance(false);
    request.setGearType("it_automatic");
    request.setFuelType("it_petrol");
    request.setLpg(false);
    request.setDateInstallLpg(releaseDate.plus(30, ChronoUnit.DAYS));
    request.setInsuranceFirm("it_Insurance_firm_1");
    request.setInsuranceDateStart(insuranceDateStart);
    request.setInsuranceDateEnd(insuranceDateEnd);
    request.setSCard(true);
    request.setKey2(true);
    request.setGps(true);
    request.setTechnicalInspectionEnd(technicalInspectionEnd);
    request.setGasInspectionEnd(gasInspectionEnd);
    request.setComment("it_comment_1");
    request.setDateEndLpg(dateEndLpg);
    request.setBrandingQrent(true);
    request.setBrandingBolt(false);
    request.setBrandingForus(false);
    request.setBrandingUber(false);
    request.setBrandingTallink(false);
    request.setCustomRentActive(Boolean.TRUE);
    request.setCustomRentAmount(BigDecimal.valueOf(200L));

    return request;
  }
}
