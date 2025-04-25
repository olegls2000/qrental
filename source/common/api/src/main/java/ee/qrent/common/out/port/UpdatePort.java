package ee.qrent.common.out.port;

public interface UpdatePort<D> {
  D update(final D domain);
}
