package ee.qrent.billing.transaction.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.transaction.domain.Transaction;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TransactionLoadPort extends LoadPort<Transaction> {

  List<Transaction> loadAllByIds(final List<Long> ids);

  List<Transaction> loadAllBetweenDates(final LocalDate dateStart, final LocalDate dateEnd);

  List<Transaction> loadAllByDriverId(final Long driverId);

  List<Transaction> loadAllByDriverIdAndBetweenDates(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd);

  List<Transaction> loadAllByDriverIdAndBetweenDatesAndKindIds(
      final Long driverId,
      final LocalDate dateStart,
      final LocalDate dateEnd,
      final List<Long> kindIds);

  List<Transaction> loadAllByDriverIdAndBetweenDatesAndTypeCodes(
      final Long driverId,
      final LocalDate dateStart,
      final LocalDate dateEnd,
      final Set<String> typeCodes);

  List<Transaction> loadAllByDriverIdAndBetweenDatesAndNonFee(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd);

  List<Transaction> loadAllByDriverIdAndBetweenDatesAndFee(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd);

  List<Transaction> loadAllByRentCalculationId(final Long rentCalculationId);

  List<Transaction> loadAllByInsuranceCalculationId(final Long insuranceCalculationId);

  List<Transaction> loadAllByInsuranceCaseId(final Long insuranceCaseId);

  List<Transaction> loadAllByBonusCalculationId(final Long bonusCalculationId);
}
