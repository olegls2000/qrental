package ee.qrent.billing.report.api.in.usecase;

import ee.qrent.billing.report.api.in.request.WeeklyReportSendByEmailRequest;

public interface WeeklyReportSendByEmailUseCase {

  void sendByEmail(final WeeklyReportSendByEmailRequest request);
}
