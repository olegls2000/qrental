package ee.qrental.common.core.in.request;

import ee.qrental.common.core.in.validation.WithViolations;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public abstract class AbstractRequest implements WithViolations {

  @Builder.Default private List<String> violations = new ArrayList<>();

  public boolean hasViolations() {
    return !violations.isEmpty();
  }
}
