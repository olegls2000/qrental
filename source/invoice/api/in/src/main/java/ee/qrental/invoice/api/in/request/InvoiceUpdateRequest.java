package ee.qrental.invoice.api.in.request;

import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class InvoiceUpdateRequest extends AbstractUpdateRequest {

  private String comment;
}
