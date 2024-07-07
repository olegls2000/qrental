package ee.qrent.common.in.validation;

import java.util.List;

public interface WithViolations {
  List<String> getViolations();

  void setViolations(final List<String> violations);

  boolean hasViolations();
}
