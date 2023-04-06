package ee.qrental.firm.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FirmAddRequest extends AbstractAddRequest {
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
