package ee.qrent.billing.report.adapter.adapter;

import ee.qrent.billing.report.adapter.mapper.WeeklyReportCalculationAdapterMapper;
import ee.qrent.billing.report.adapter.repository.WeeklyReportCalculationRepository;

import ee.qrent.billing.report.api.out.WeeklyReportCalculationAddPort;
import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportCalculationPersistenceAdapter implements WeeklyReportCalculationAddPort {

  private final WeeklyReportCalculationRepository repository;
  private final WeeklyReportCalculationAdapterMapper mapper;

  @Override
  public WeeklyReportCalculation add(final WeeklyReportCalculation domain) {
    final var savedEntity = repository.save(mapper.mapToEntity(domain));

    return mapper.mapToDomain(savedEntity);
  }
}
