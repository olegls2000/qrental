package ee.qrent.common.in.usecase;

public interface QTaskRunner {
  void run(final QTask qTask);

  String STATUS_SUCCESS = "SUCCESS";
  String STATUS_FAILED = "FAILED";
}
