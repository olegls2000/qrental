package ee.qrent.common.in.usecase;

public interface AddUseCase<R> {
  Long add(R request);
}
