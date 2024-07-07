package ee.qrent.common.in.mapper;

public interface ResponseMapper<R, D> {
  R toResponse(final D domain);

  String toObjectInfo(final D domain);
}
