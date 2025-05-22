package ee.qrent.billing.report.adapter.adapter;

import static java.util.stream.Collectors.toList;

import java.util.List;

import ee.qrent.billing.report.adapter.mapper.WeeklyReportAdapterMapper;
import ee.qrent.billing.report.adapter.repository.WeeklyReportRepository;
import ee.qrent.billing.report.api.out.WeeklyReportLoadPort;
import ee.qrent.billing.report.domain.WeeklyReport;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportLoadAdapter implements WeeklyReportLoadPort {

  private final WeeklyReportRepository repository;
  private final WeeklyReportAdapterMapper mapper;

  @Override
  public List<WeeklyReport> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public WeeklyReport loadById(Long id) {
    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public List<WeeklyReport> loadAllByCalculationId(Long calculationId) {
    return repository.findByCalculationId(calculationId).stream()
            .map(mapper::mapToDomain)
            .collect(toList());
  }
}
