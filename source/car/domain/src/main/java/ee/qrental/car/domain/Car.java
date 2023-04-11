package ee.qrental.car.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@SuperBuilder
@Getter
public class Car {
    private Long id;
    private Boolean active;
    private Boolean qRent;
    private String regNumber;
    private String vin;
    private LocalDate releaseDate;
    private String manufacturer;
    private String model;
    private Boolean appropriation;
    private Boolean elegance;
    private String gearType;
    private String fuelType;
    private Boolean lpg;
    private LocalDate dateInstallLpg;
    private String insuranceFirm;
    private LocalDate insuranceDateStart;
    private LocalDate insuranceDateEnd;
    private Boolean sCard;
    private Boolean key2;
    private Boolean gps;
    private LocalDate technicalInspectionEnd;
    private LocalDate gasInspectionEnd;
    private String comment;
    private LocalDate dateEndLpg;
}
