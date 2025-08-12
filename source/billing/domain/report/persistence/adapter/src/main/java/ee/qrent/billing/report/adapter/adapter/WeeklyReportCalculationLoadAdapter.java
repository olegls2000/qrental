package ee.qrent.billing.report.adapter.adapter;

import static java.util.stream.Collectors.toList;

import java.util.List;

import ee.qrent.billing.report.adapter.mapper.WeeklyReportCalculationAdapterMapper;
import ee.qrent.billing.report.adapter.repository.WeeklyReportCalculationRepository;
import ee.qrent.billing.report.api.out.WeeklyReportCalculationLoadPort;
import ee.qrent.billing.report.domain.WeeklyReportCalculation;
import ee.qrent.billing.report.domain.WeeklyReportType;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportTypeJakarta;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportCalculationLoadAdapter implements WeeklyReportCalculationLoadPort {

  private final WeeklyReportCalculationRepository repository;
  private final WeeklyReportCalculationAdapterMapper mapper;

  @Override
  public List<WeeklyReportCalculation> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public WeeklyReportCalculation loadById(final Long id) {
    final var entity = repository.getReferenceById(id);
    return mapper.mapToDomain(entity);
  }

  @Override
  public Long loadLastCalculatedQWeekId() {
    return repository.getLastCalculatedQWeekId();
  }

  @Override
  public WeeklyReportCalculation loadByQWeekIdAndReportType(
      final Long qWeekId, final WeeklyReportType reportType) {
    final var entity =
        repository.getCalculationByQWeekIdAndReportType(
            qWeekId, WeeklyReportTypeJakarta.valueOf(reportType.name()));

    return mapper.mapToDomain(entity);
  }
}
