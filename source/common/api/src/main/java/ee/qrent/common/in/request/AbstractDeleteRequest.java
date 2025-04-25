package ee.qrent.common.in.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public abstract class AbstractDeleteRequest extends AbstractRequest {
  private Long id;
}
