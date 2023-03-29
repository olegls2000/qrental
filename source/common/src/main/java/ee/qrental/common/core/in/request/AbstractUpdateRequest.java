package ee.qrental.common.core.in.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractUpdateRequest extends AbstractRequest {
  private Long id;
}
