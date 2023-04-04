package ee.qrental.invoice.api.in.request;

import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class InvoiceUpdateRequest extends AbstractUpdateRequest {

  private String comment;
}
