package ee.qrental.transaction.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.transaction.domain.Transaction;
import java.time.LocalDate;
import java.util.List;

public interface TransactionLoadPort extends LoadPort<Transaction> {

  List<Transaction> loadAllByDriverId(final Long driverId);

  List<Transaction> loadAllByIds(final List<Long> ids);

  List<Transaction> loadAllBetweenDays(final LocalDate dateStart, final LocalDate dateEnd);

  List<Transaction> loadAllByDriverIdAndBetweenDays(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd);

  List<Transaction> loadAllByDriverIdAndKindIdAndBetweenDays(
      final Long driverId,
      final List<Long> kindIds,
      final LocalDate dateStart,
      final LocalDate dateEnd);

  List<Transaction> loadAllNonFeeByDriverIdAndBetweenDays(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd);

  List<Transaction> loadAllFeeByDriverIdAndBetweenDays(
      final Long driverId, final LocalDate dateStart, final LocalDate dateEnd);

  List<Transaction> loadAllByRentCalculationId(final Long rentCalculationId);

  List<Transaction> loadAllByInsuranceCalculationId(final Long insuranceCalculationId);

  List<Transaction> loadAllByInsuranceCaseId(final Long insuranceCaseId);

  List<Transaction> loadAllByBonusCalculationId(final Long bonusCalculationId);
}
