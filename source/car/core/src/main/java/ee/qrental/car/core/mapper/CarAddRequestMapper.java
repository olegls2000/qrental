package ee.qrental.car.core.mapper;

import ee.qrental.car.domain.CarStatus;
import ee.qrent.common.in.mapper.AddRequestMapper;
import ee.qrental.car.api.in.request.CarAddRequest;
import ee.qrental.car.domain.Car;

public class CarAddRequestMapper implements AddRequestMapper<CarAddRequest, Car> {

  @Override
  public Car toDomain(CarAddRequest request) {
    return Car.builder()
        .id(null)
        .active(request.getActive())
        .status(CarStatus.valueOf(request.getStatus()))
        .qRent(request.getQRent())
        .regNumber(request.getRegNumber())
        .vin(request.getVin())
        .releaseDate(request.getReleaseDate())
        .manufacturer(request.getManufacturer())
        .model(request.getModel())
        .appropriation(request.getAppropriation())
        .elegance(request.getElegance())
        .gearType(request.getGearType())
        .fuelType(request.getFuelType())
        .lpg(request.getLpg())
        .dateInstallLpg(request.getDateInstallLpg())
        .insuranceFirm(request.getInsuranceFirm())
        .insuranceDateStart(request.getInsuranceDateStart())
        .insuranceDateEnd(request.getInsuranceDateEnd())
        .sCard(request.getSCard())
        .key2(request.getKey2())
        .gps(request.getGps())
        .technicalInspectionEnd(request.getTechnicalInspectionEnd())
        .gasInspectionEnd(request.getGasInspectionEnd())
        .comment(request.getComment())
        .dateEndLpg(request.getDateEndLpg())
        .brandingQrent(request.getBrandingQrent())
        .brandingBolt(request.getBrandingBolt())
        .brandingForus(request.getBrandingForus())
        .brandingUber(request.getBrandingUber())
        .brandingTallink(request.getBrandingTallink())
        .build();
  }
}
