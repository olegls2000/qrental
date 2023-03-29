package ee.qrental.common.core.in.mapper;

public interface UpdateRequestMapper<R, D> {

  D toDomain(final R request);

  R toRequest(final D domain);
}
