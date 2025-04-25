package ee.qrent.common.in.usecase;

public interface QTask {
  Runnable getRunnable();

  String getName();
}
