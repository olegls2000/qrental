package ee.qrent.common.in.mapper;

public interface AddRequestMapper<R, D> {
  D toDomain(final R request);
}
