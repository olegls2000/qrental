package ee.qrental.driver.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.driver.domain.CallSignLink;
import ee.qrental.driver.domain.FirmLink;

import java.time.LocalDate;
import java.util.List;

public interface FirmLinkLoadPort extends LoadPort<FirmLink> {
}
