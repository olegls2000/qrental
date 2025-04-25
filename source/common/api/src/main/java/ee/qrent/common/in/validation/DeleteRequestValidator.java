package ee.qrent.common.in.validation;

import ee.qrent.common.in.request.AbstractDeleteRequest;

public interface DeleteRequestValidator<R extends AbstractDeleteRequest> {
    ViolationsCollector validate(final R request);
}