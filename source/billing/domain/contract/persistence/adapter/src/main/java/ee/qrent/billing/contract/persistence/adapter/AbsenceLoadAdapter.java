package ee.qrent.billing.contract.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.contract.persistence.mapper.AbsenceAdapterMapper;
import ee.qrent.billing.contract.persistence.repository.AbsenceRepository;
import ee.qrent.billing.contract.api.out.AbsenceLoadPort;

import ee.qrent.billing.contract.domain.Absence;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AbsenceLoadAdapter implements AbsenceLoadPort {

  private final AbsenceRepository repository;
  private final AbsenceAdapterMapper mapper;

  @Override
  public List<Absence> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Absence loadById(Long id) {
    return mapper.mapToDomain(repository.findById(id));
  }

  @Override
  public List<Absence> loadByDriverIdAndDateStartAndDateEnd(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd) {
    return repository.findByDriverIdAndDateStartAndDateEnd(driverId, dateStart, dateEnd).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }

  @Override
  public List<Absence> loadByDriverIdAndDateStart(final Long driverId, final LocalDate dateStart) {
    return repository.findByDriverIdAndDateStart(driverId, dateStart).stream()
        .map(mapper::mapToDomain)
        .collect(toList());
  }
}
