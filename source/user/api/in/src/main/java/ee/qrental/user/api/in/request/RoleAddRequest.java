package ee.qrental.user.api.in.request;

import ee.qrental.common.core.in.request.AbstractAddRequest;
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
