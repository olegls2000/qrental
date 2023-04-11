package ee.qrental.common.core.in.validation;

public interface QValidator<D> {
  ViolationsCollector validateAdd(final D domain);
  ViolationsCollector validateUpdate(final D domain);
  ViolationsCollector validateDelete(final D domain);
}
