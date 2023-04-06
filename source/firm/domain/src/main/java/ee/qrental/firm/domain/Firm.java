package ee.qrental.firm.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class Firm {
    private Long id;
    private String firmName;
    private String iban;
    private String regNumber;
    private String vatNumber;
    private String comment;
    private String email;
    private String postAddress;
    private String phone;
    private String bank;
    private Boolean qGroup;
}
