package ee.qrent.billing.report.repository.impl;

import ee.qrent.billing.report.adapter.repository.WeeklyReportCalculationRepository;
import ee.qrent.billing.report.persistence.entity.jakarta.WeeklyReportCalculationJakartaEntity;
import ee.qrent.billing.report.repository.spring.WeeklyReportCalculationSpringDataRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class WeeklyReportCalculationRepositoryImpl implements WeeklyReportCalculationRepository {

  private final WeeklyReportCalculationSpringDataRepository springDataRepository;

  @Override
  public WeeklyReportCalculationJakartaEntity save(
      final WeeklyReportCalculationJakartaEntity entity) {

    return springDataRepository.save(entity);
  }

  @Override
  public List<WeeklyReportCalculationJakartaEntity> findAll() {

    return springDataRepository.findAll();
  }

  @Override
  public WeeklyReportCalculationJakartaEntity getReferenceById(final Long id) {

    return springDataRepository.getReferenceById(id);
  }

  @Override
  public Long getLastCalculatedQWeekId() {
    return springDataRepository.getLastCalculationQWeekId();
  }
}
