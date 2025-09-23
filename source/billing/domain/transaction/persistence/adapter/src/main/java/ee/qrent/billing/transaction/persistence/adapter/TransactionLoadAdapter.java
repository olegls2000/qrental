package ee.qrent.billing.transaction.persistence.adapter;

import ee.qrent.billing.transaction.persistence.mapper.TransactionAdapterMapper;
import ee.qrent.billing.transaction.persistence.repository.TransactionRepository;
import ee.qrent.billing.transaction.api.out.TransactionLoadPort;
import ee.qrent.billing.transaction.domain.Transaction;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TransactionLoadAdapter implements TransactionLoadPort {

  private final TransactionRepository repository;

  private final TransactionAdapterMapper mapper;

  @Override
  public List<Transaction> loadAll() {

    return repository.findAll().stream().map(mapper::mapToDomain).toList();
  }

  @Override
  public Transaction loadById(final Long id) {

    return mapper.mapToDomain(repository.getReferenceById(id));
  }

  @Override
  public List<Transaction> loadAllByDriverId(final Long driverId) {

    return repository.findByDriverId(driverId).stream().map(mapper::mapToDomain).toList();
  }

  @Override
  public List<Transaction> loadAllByIds(final List<Long> ids) {

    return repository.findByIds(ids).stream().map(mapper::mapToDomain).toList();
  }

  @Override
  public List<Transaction> loadAllBetweenDates(final LocalDate dateStart, final LocalDate dateEnd) {

    return repository.findAllByBetweenDates(dateStart, dateEnd).stream()
        .map(mapper::mapToDomain)
        .toList();
  }

  @Override
  public List<Transaction> loadAllByDriverIdAndBetweenDates(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd) {

    return repository.findAllByDriverIdAndBetweenDates(driverId, dateStart, dateEnd).stream()
        .map(mapper::mapToDomain)
        .toList();
  }

  @Override
  public List<Transaction> loadAllByDriverIdAndBetweenDatesAndKindIds(
      final Long driverId,
      final LocalDate dateStart,
      final LocalDate dateEnd,
      final List<Long> kindIds) {

    return repository
        .findAllByDriverIdAndBetweenDatesAndKindIds(driverId, dateStart, dateEnd, kindIds)
        .stream()
        .map(mapper::mapToDomain)
        .toList();
  }

  @Override
  public List<Transaction> loadAllByDriverIdAndBetweenDatesAndTypeCodes(
      final Long driverId,
      final LocalDate dateStart,
      final LocalDate dateEnd,
      final Set<String> typeCodes) {

    return repository
        .findAllByDriverIdAndBetweenDatesAndTypeCodes(driverId, dateStart, dateEnd, typeCodes)
        .stream()
        .map(mapper::mapToDomain)
        .toList();
  }

  @Override
  public List<Transaction> loadAllByDriverIdAndBetweenDatesAndNonFee(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd) {

    return repository
        .findAllByDriverIdAndBetweenDatesAndNonFee(driverId, dateStart, dateEnd)
        .stream()
        .map(mapper::mapToDomain)
        .toList();
  }

  @Override
  public List<Transaction> loadAllByDriverIdAndBetweenDatesAndFee(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd) {

    return repository.findAllByDriverIdAndBetweenDatesAndFee(driverId, dateStart, dateEnd).stream()
        .map(mapper::mapToDomain)
        .toList();
  }

  @Override
  public List<Transaction> loadAllByRentCalculationId(final Long rentCalculationId) {

    return repository.findAllByRentCalculationId(rentCalculationId).stream()
        .map(mapper::mapToDomain)
        .toList();
  }

  @Override
  public List<Transaction> loadAllByInsuranceCalculationId(final Long insuranceCalculationId) {

    return repository.findAllByInsuranceCalculationId(insuranceCalculationId).stream()
        .map(mapper::mapToDomain)
        .toList();
  }

  @Override
  public List<Transaction> loadAllByInsuranceCaseId(final Long insuranceCaseId) {

    return repository.findAllByInsuranceCaseId(insuranceCaseId).stream()
        .map(mapper::mapToDomain)
        .toList();
  }

  @Override
  public List<Transaction> loadAllByBonusCalculationId(final Long bonusCalculationId) {

    return repository.findAllByBonusCalculationId(bonusCalculationId).stream()
        .map(mapper::mapToDomain)
        .toList();
  }
}
