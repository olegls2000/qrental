package ee.qrental.bonus.api.in.response;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
public class BonusResponse {
  private Long id;
  private String name;
  private String ceoName;
  private String iban;
  private String registrationNumber;
  private String vatNumber;
  private String email;
  private String postAddress;
  private String phone;
  private String bank;
  private Boolean qGroup;
  private List<String> deputies = new ArrayList<>();
  private String comment;
}
