package ee.qrent.common.in.validation;

import ee.qrent.common.in.request.AbstractCloseRequest;

public interface CloseRequestValidator<R extends AbstractCloseRequest> {
    ViolationsCollector validate(final R request);
}