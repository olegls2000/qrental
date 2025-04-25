package ee.qrent.billing.firm.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FirmAddRequest extends AbstractAddRequest {
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
  private String email;
  private String postAddress;
  private String phone;
  private String bank;
  private Boolean qGroup;
  private String comment;
}
