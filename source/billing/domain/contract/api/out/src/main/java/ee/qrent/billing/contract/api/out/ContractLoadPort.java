package ee.qrent.billing.contract.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.contract.domain.Contract;

import java.time.LocalDate;
import java.util.List;

public interface ContractLoadPort extends LoadPort<Contract> {
  Contract loadByNumber(String number);

  Contract loadLatestByDriverId(final Long driverId);

  List<Contract> loadActiveByDate(final LocalDate date);

  List<Contract> loadAllByDriverId(final Long driverId);

  Contract loadActiveByDateAndDriverId(final LocalDate date, final Long driverId);

  List<Contract> loadClosedByDate(final LocalDate date);

  Long loadCountActiveByDate(final LocalDate date);

  Long loadCountClosedByDate(final LocalDate date);
}
