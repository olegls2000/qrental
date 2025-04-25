package ee.qrent.common.in.validation;

import ee.qrent.common.in.request.AbstractUpdateRequest;

public interface UpdateRequestValidator<R extends AbstractUpdateRequest> {
  ViolationsCollector validate(final R request);
}
