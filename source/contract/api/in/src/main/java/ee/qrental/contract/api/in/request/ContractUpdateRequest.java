package ee.qrental.contract.api.in.request;

import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class ContractUpdateRequest extends AbstractUpdateRequest {

  private Long id;
  private String driverCompany;
  private String driverCompanyRegNumber;
  private String driverCompanyAddress;
  private String qFirmName;
  private String qFirmRegNumber;
  private String qFirmVatNumber;
  private String qFirmIban;
  private String qFirmBank;
  private String comment;
}
