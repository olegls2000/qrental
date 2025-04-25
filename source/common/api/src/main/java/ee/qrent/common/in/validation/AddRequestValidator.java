package ee.qrent.common.in.validation;

import ee.qrent.common.in.request.AbstractAddRequest;

public interface AddRequestValidator<R extends AbstractAddRequest> {
    ViolationsCollector validate(final R request);
}