package ee.qrent.billing.report.api.in.usecase;

import ee.qrent.billing.report.api.in.request.WeeklyReportAddRequest;

public interface WeeklyReportAddUseCase {

  void add(final WeeklyReportAddRequest request);
}
