package ee.qrent.billing.report.adapter.adapter;


import ee.qrent.billing.report.adapter.mapper.WeeklyReportAdapterMapper;
import ee.qrent.billing.report.adapter.repository.WeeklyReportRepository;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationAddPort;
import ee.qrent.billing.report.domain.WeeklyReport;
import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportPersistenceAdapter implements WeeklyReportCalculationAddPort {

  private final WeeklyReportRepository repository;
  private final WeeklyReportAdapterMapper mapper;

  @Override
  public WeeklyReportCalculation add(final WeeklyReportCalculation domain) {
    final var savedEntity = repository.save(mapper.mapToEntity(domain));

    return mapper.mapToDomain(savedEntity);
  }
}
