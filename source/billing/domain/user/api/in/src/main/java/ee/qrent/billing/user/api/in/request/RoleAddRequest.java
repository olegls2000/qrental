package ee.qrent.billing.user.api.in.request;

import ee.qrent.common.in.request.AbstractAddRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleAddRequest extends AbstractAddRequest {
  private String name;
  private String comment;
}
