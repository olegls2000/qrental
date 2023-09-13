package ee.qrental.contract.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.contract.domain.Contract;

public interface ContractLoadPort extends LoadPort<Contract> {

    Contract loadByWeekAndDriverAndFirm(final Integer weekNumber, final Long driverId, final Long firmId);
}
