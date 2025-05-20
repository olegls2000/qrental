package ee.qrent.billing.report.adapter.adapter;


import ee.qrent.billing.report.adapter.mapper.WeeklyReportAdapterMapper;
import ee.qrent.billing.report.adapter.repository.WeeklyReportRepository;
import ee.qrent.billing.report.api.out.WeeklyReportAddPort;
import ee.qrent.billing.report.domain.WeeklyReport;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportPersistenceAdapter implements WeeklyReportAddPort {

  private final WeeklyReportRepository repository;
  private final WeeklyReportAdapterMapper mapper;

  @Override
  public WeeklyReport add(final WeeklyReport domain) {
    final var savedEntity = repository.save(mapper.mapToEntity(domain));

    return mapper.mapToDomain(savedEntity);
  }
}
