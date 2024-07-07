package ee.qrental.invoice.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InvoiceSendByEmailRequest extends AbstractAddRequest {

  private Long id;
}
