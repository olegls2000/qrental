package ee.qrental.firm.api.in.response;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class FirmResponse {
  private Long id;
  private String name;
  private String iban;
  private String regNumber;
  private String vatNumber;
  private String email;
  private String postAddress;
  private String phone;
  private String bank;
  private Boolean qGroup;
  private String comment;
}
