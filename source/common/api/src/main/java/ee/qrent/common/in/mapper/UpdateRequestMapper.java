package ee.qrent.common.in.mapper;

public interface UpdateRequestMapper<R, D> {

  D toDomain(final R request);

  R toRequest(final D domain);
}
