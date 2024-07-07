package ee.qrental.invoice.api.in.request;


import ee.qrent.common.in.request.AbstractAddRequest;
import ee.qrental.common.utils.QWeek;
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
  //TODO change to QWeekId
  private QWeek week;
  private String comment;
}
