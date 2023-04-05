package ee.qrental.invoice.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import ee.qrental.common.core.utils.QWeek;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InvoiceAddRequest extends AbstractAddRequest {
  private Long driverId;
  private Long qFirmId;
  private Integer year;
  private QWeek week;
  private String comment;
}
