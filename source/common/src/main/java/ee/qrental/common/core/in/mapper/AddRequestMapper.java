package ee.qrental.common.core.in.mapper;

public interface AddRequestMapper<R, D> {
  D toDomain(final R request);
}
