package ee.qrent.billing.report.repository.impl;

import ee.qrent.billing.report.adapter.repository.WeeklyReportRepository;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportJakartaEntity;
import ee.qrent.billing.report.repository.spring.WeeklyReportSpringDataRepository;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class WeeklyReportRepositoryImpl implements WeeklyReportRepository {

  private final WeeklyReportSpringDataRepository springDataRepository;

  @Override
  public List<WeeklyReportJakartaEntity> findAll() {
    return springDataRepository.findAll();
  }

  @Override
  public WeeklyReportJakartaEntity getReferenceById(final Long id) {
    return springDataRepository.getReferenceById(id);
  }

  @Override
  public List<WeeklyReportJakartaEntity> findByCalculationId(final Long calculationId) {
    return springDataRepository.findByCalculationId(calculationId);
  }

  @Override
  public WeeklyReportJakartaEntity save(final WeeklyReportJakartaEntity entity) {
    return springDataRepository.save(entity);
  }
}
