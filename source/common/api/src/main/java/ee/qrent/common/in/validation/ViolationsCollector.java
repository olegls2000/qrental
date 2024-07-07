package ee.qrent.common.in.validation;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

public class ViolationsCollector {
  @Getter private final List<String> violations = new ArrayList<>();

  public boolean hasViolations() {
    return !violations.isEmpty();
  }

  public void collect(String... violations) {
    this.violations.addAll(asList(violations));
  }
}
