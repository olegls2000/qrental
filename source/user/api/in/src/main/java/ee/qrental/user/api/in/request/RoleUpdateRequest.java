package ee.qrental.user.api.in.request;


import ee.qrent.common.in.request.AbstractUpdateRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class RoleUpdateRequest extends AbstractUpdateRequest {
    private String name;
    private String comment;
}
