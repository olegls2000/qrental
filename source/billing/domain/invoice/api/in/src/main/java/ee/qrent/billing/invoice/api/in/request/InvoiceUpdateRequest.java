package ee.qrent.billing.invoice.api.in.request;

import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class InvoiceUpdateRequest extends AbstractUpdateRequest {

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
