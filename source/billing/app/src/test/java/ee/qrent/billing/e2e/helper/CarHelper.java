package ee.qrent.billing.e2e.helper;

import ee.qrent.billing.car.api.in.request.CarAddRequest;

import java.time.LocalDate;
import java.time.Month;

public class CarHelper {

  public static CarAddRequest getValidCarAddRequest() {
    final var carAddRequest = new CarAddRequest();
    carAddRequest.setActive(true);
    carAddRequest.setStatus("IN_USE");
    carAddRequest.setQRent(true);
    carAddRequest.setRegNumber("123ABC");
    carAddRequest.setVin("1HGBH41JXMN109186");
    carAddRequest.setReleaseDate(LocalDate.of(2020, Month.JANUARY, 1));
    carAddRequest.setManufacturer("Toyota");
    carAddRequest.setModel("Corolla");
    carAddRequest.setAppropriation(true);
    carAddRequest.setElegance(false);
    carAddRequest.setGearType("automatic");
    carAddRequest.setFuelType("petrol");
    carAddRequest.setLpg(false);
    carAddRequest.setDateInstallLpg(LocalDate.of(2020, Month.JANUARY, 30));
    carAddRequest.setInsuranceFirm("Insurance Co.");
    carAddRequest.setInsuranceDateStart(LocalDate.of(2020, Month.FEBRUARY, 25));
    carAddRequest.setInsuranceDateEnd(LocalDate.of(2021, Month.FEBRUARY, 25));
    carAddRequest.setSCard(true);
    carAddRequest.setKey2(true);
    carAddRequest.setGps(true);
    carAddRequest.setTechnicalInspectionEnd(LocalDate.of(2021, Month.MARCH, 30));
    carAddRequest.setGasInspectionEnd(LocalDate.of(2021, Month.APRIL, 30));
    carAddRequest.setComment("Test comment");
    carAddRequest.setDateEndLpg(LocalDate.of(2021, Month.MAY, 30));
    carAddRequest.setBrandingQrent(true);
    carAddRequest.setBrandingBolt(false);
    carAddRequest.setBrandingForus(false);
    carAddRequest.setBrandingUber(false);
    carAddRequest.setBrandingTallink(false);

    return carAddRequest;
  }
}
