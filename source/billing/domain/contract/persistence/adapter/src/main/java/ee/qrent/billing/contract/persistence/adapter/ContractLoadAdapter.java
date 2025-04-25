package ee.qrent.billing.contract.persistence.adapter;

import static java.util.stream.Collectors.toList;

import ee.qrent.billing.contract.persistence.mapper.ContractAdapterMapper;
import ee.qrent.billing.contract.persistence.repository.ContractRepository;
import ee.qrent.billing.contract.api.out.ContractLoadPort;
import ee.qrent.billing.contract.domain.Contract;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ContractLoadAdapter implements ContractLoadPort {

  private final ContractRepository repository;
  private final ContractAdapterMapper mapper;

  @Override
  public List<Contract> loadAll() {
    return repository.findAll().stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Contract loadById(final Long id) {
    final var entity = repository.getReferenceById(id);

    return mapper.mapToDomain(entity);
  }

  @Override
  public Contract loadByNumber(final String number) {
    final var entity = repository.getByNumber(number);

    return mapper.mapToDomain(entity);
  }

  @Override
  public Contract loadLatestByDriverId(final Long driverId) {
    return mapper.mapToDomain(repository.findLatestByDriverId(driverId));
  }

  @Override
  public List<Contract> loadActiveByDate(final LocalDate date) {
    return repository.findActiveByDate(date).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public List<Contract> loadAllByDriverId(final Long driverId) {
    return repository.findAllByDriverId(driverId).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Contract loadActiveByDateAndDriverId(final LocalDate date, final Long driverId) {
    final var activeContract = repository.findActiveByDateAndDriverId(date, driverId);
    return mapper.mapToDomain(activeContract);
  }

  @Override
  public List<Contract> loadClosedByDate(final LocalDate date) {
    return repository.findClosedByDate(date).stream().map(mapper::mapToDomain).collect(toList());
  }

  @Override
  public Long loadCountActiveByDate(final LocalDate date) {
    return repository.findCountActiveByDate(date);
  }

  @Override
  public Long loadCountClosedByDate(final LocalDate date) {
    return repository.findCountClosedByDate(date);
  }
}
