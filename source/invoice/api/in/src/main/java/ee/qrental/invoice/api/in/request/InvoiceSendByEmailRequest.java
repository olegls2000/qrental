package ee.qrental.invoice.api.in.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@Getter
@Setter
public class InvoiceSendByEmailRequest {

  private Long id;
}
