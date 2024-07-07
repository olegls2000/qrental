package ee.qrental.car.core.mapper;

import ee.qrental.car.api.in.response.CarResponse;
import ee.qrental.car.domain.Car;
import ee.qrent.common.in.mapper.ResponseMapper;

import static java.lang.String.format;

public class CarResponseMapper implements ResponseMapper<CarResponse, Car> {
  @Override
  public CarResponse toResponse(final Car domain) {
    return CarResponse.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .status(domain.getStatus().getLabel())
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
        .warrantyRagStatus(domain.getWarrantyRagStatus().name())
        .comment(domain.getComment())
        .brandingQrent(domain.getBrandingQrent())
        .brandingBolt(domain.getBrandingBolt())
        .brandingForus(domain.getBrandingForus())
        .brandingUber(domain.getBrandingUber())
        .brandingTallink(domain.getBrandingTallink())
        .build();
  }

  @Override
  public String toObjectInfo(Car domain) {
    return format("%s %s", domain.getRegNumber(), domain.getManufacturer());
  }
}
