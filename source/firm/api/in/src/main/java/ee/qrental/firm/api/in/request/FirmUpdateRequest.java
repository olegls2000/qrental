package ee.qrental.firm.api.in.request;


import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class FirmUpdateRequest extends AbstractUpdateRequest {
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
