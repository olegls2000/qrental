package ee.qrental.user.api.in.request;


import ee.qrental.common.core.in.request.AbstractUpdateRequest;
import java.util.Set;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class UserAccountUpdateRequest extends AbstractUpdateRequest {

    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Set<Long> roles;
}
