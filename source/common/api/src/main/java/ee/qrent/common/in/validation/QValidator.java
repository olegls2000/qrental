package ee.qrent.common.in.validation;

public interface QValidator<D> {
  ViolationsCollector validateAdd(final D domain);
  ViolationsCollector validateUpdate(final D domain);
  ViolationsCollector validateDelete(final Long id);
}
