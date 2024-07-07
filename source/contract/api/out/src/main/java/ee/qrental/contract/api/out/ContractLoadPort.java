package ee.qrental.contract.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.contract.domain.Contract;

public interface ContractLoadPort extends LoadPort<Contract> {
   Contract loadByNumber(String number);
   Contract loadActiveByDriverId(final Long driverId);
}
