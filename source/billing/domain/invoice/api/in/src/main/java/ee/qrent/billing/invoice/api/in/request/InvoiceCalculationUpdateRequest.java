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
public class InvoiceCalculationUpdateRequest extends AbstractUpdateRequest {
  private Long id;
  private String comment;
}
