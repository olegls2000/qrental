package ee.qrent.common.in.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;


@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class AbstractUpdateRequest extends AbstractRequest {
  private Long id;
}
