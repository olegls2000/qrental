package ee.qrental.car.core.mapper;

import static java.lang.String.format;

import ee.qrental.car.api.in.response.CarResponse;
import ee.qrental.car.domain.Car;
import ee.qrental.common.core.in.mapper.ResponseMapper;

public class CarResponseMapper implements ResponseMapper<CarResponse, Car> {
  @Override
  public CarResponse toResponse(final Car domain) {
    return CarResponse.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .qRent(domain.getQRent())
        .regNumber(domain.getRegNumber())
        .vin(domain.getVin())
        .releaseDate(domain.getReleaseDate())
        .manufacturer(domain.getManufacturer())
        .model(domain.getModel())
        .appropriation(domain.getAppropriation())
        .elegance(domain.getElegance())
        .gearType(domain.getGearType())
        .fuelType(domain.getFuelType())
        .lpg(domain.getLpg())
        .dateInstallLpg(domain.getDateInstallLpg())
        .insuranceFirm(domain.getInsuranceFirm())
        .insuranceDateStart(domain.getInsuranceDateStart())
        .insuranceDateEnd(domain.getInsuranceDateEnd())
        .sCard(domain.getSCard())
        .key2(domain.getKey2())
        .gps(domain.getGps())
        .technicalInspectionEnd(domain.getTechnicalInspectionEnd())
        .gasInspectionEnd(domain.getGasInspectionEnd())
        .dateEndLpg(domain.getDateEndLpg())
        .insuranceRagStatus(domain.getInsuranceRagStatus().name())
        .technicalInspectionRagStatus(domain.getTechnicalInspectionRagStatus().name())
        .gasInspectionRagStatus(domain.getGasInspectionRagStatus().name())
        .comment(domain.getComment())
        .build();
  }

  @Override
  public String toObjectInfo(Car domain) {
    return format("%s %s", domain.getRegNumber(), domain.getManufacturer());
  }
}
