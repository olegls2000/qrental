package ee.qrental.car.adapter.mapper;

import ee.qrental.car.domain.Car;
import ee.qrental.car.domain.CarStatus;
import ee.qrental.car.entity.jakarta.CarJakartaEntity;

public class CarAdapterMapper {

  public Car mapToDomain(final CarJakartaEntity entity) {
    return Car.builder()
        .id(entity.getId())
        .active(entity.getActive())
        .status(CarStatus.valueOf(entity.getStatus()))
        .qRent(entity.getQRent())
        .regNumber(entity.getRegNumber())
        .vin(entity.getVin())
        .releaseDate(entity.getReleaseDate())
        .manufacturer(entity.getManufacturer())
        .model(entity.getModel())
        .appropriation(entity.getAppropriation())
        .elegance(entity.getElegance())
        .gearType(entity.getGearType())
        .fuelType(entity.getFuelType())
        .lpg(entity.getLpg())
        .dateInstallLpg(entity.getDateInstallLpg())
        .insuranceFirm(entity.getInsuranceFirm())
        .insuranceDateStart(entity.getInsuranceDateStart())
        .insuranceDateEnd(entity.getInsuranceDateEnd())
        .sCard(entity.getSCard())
        .key2(entity.getKey2())
        .gps(entity.getGps())
        .technicalInspectionEnd(entity.getTechnicalInspectionEnd())
        .gasInspectionEnd(entity.getGasInspectionEnd())
        .comment(entity.getComment())
        .dateEndLpg(entity.getDateEndLpg())
        .brandingQrent(entity.getBrandingQrent())
        .brandingBolt(entity.getBrandingBolt())
        .brandingForus(entity.getBrandingForus())
        .brandingUber(entity.getBrandingUber())
        .brandingTallink(entity.getBrandingTallink())
        .build();
  }

  public CarJakartaEntity mapToEntity(final Car domain) {
    return CarJakartaEntity.builder()
        .id(domain.getId())
        .active(domain.getActive())
        .status(domain.getStatus().name())
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
        .comment(domain.getComment())
        .dateEndLpg(domain.getDateEndLpg())
        .brandingQrent(domain.getBrandingQrent())
        .brandingBolt(domain.getBrandingBolt())
        .brandingForus(domain.getBrandingForus())
        .brandingUber(domain.getBrandingUber())
        .brandingTallink(domain.getBrandingTallink())
        .build();
  }
}
