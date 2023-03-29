package ee.qrental.common.core.in.validation;

public interface QValidator<D> {
  ViolationsCollector validate(final D domain);
}
