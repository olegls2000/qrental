package ee.qrental.driver.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.driver.domain.FirmLink;
import java.time.LocalDate;

public interface FirmLinkLoadPort extends LoadPort<FirmLink> {
    FirmLink loadOneByDriverIdAndRequiredDate(final Long driverId, final LocalDate requiredDate);
}
