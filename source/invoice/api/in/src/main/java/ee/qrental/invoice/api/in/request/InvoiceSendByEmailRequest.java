package ee.qrental.invoice.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InvoiceSendByEmailRequest extends AbstractAddRequest {

  private Long id;
}
