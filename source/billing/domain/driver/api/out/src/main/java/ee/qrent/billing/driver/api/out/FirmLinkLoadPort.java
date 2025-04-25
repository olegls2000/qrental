package ee.qrent.billing.driver.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.driver.domain.FirmLink;
import java.time.LocalDate;

public interface FirmLinkLoadPort extends LoadPort<FirmLink> {
    FirmLink loadOneByDriverIdAndRequiredDate(final Long driverId, final LocalDate requiredDate);
}
