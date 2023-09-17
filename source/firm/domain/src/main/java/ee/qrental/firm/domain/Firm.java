package ee.qrental.firm.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Firm {
    private Long id;
    private String name;
    private String ceoFirstName;
    private String ceoLastName;
    private String ceoDeputy1FirstName;
    private String ceoDeputy1LastName;
    private String ceoDeputy2FirstName;
    private String ceoDeputy2LastName;
    private String ceoDeputy3FirstName;
    private String ceoDeputy3LastName;
    private String iban;
    private String registrationNumber;
    private String vatNumber;
    private String comment;
    private String email;
    private String postAddress;
    private String phone;
    private String bank;
    private Boolean qGroup;
}
