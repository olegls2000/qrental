package ee.qrental.car.core.mapper;

import ee.qrental.common.core.in.mapper.UpdateRequestMapper;
import ee.qrental.car.api.in.request.CarUpdateRequest;
import ee.qrental.car.domain.Car;

public class CarUpdateRequestMapper implements UpdateRequestMapper<CarUpdateRequest, Car> {

    @Override
    public Car toDomain(final CarUpdateRequest request) {
        return Car.builder()
                .id(request.getId())
                .active(request.getActive())
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
                .build();
    }

    @Override
    public CarUpdateRequest toRequest(final Car domain) {
        return CarUpdateRequest.builder()
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
                .comment(domain.getComment())
                .dateEndLpg(domain.getDateEndLpg())
                .build();
    }
}
