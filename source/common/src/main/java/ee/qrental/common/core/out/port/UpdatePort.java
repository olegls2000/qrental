package ee.qrental.common.core.out.port;

public interface UpdatePort<D> {
  D update(D domain);
}
