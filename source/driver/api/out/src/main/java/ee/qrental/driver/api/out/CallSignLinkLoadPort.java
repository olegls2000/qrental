package ee.qrental.driver.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.driver.domain.CallSignLink;
import java.time.LocalDate;
import java.util.List;

public interface CallSignLinkLoadPort extends LoadPort<CallSignLink> {

  List<CallSignLink> loadByCallSignId(final Long callSignId);

  CallSignLink loadActiveByCallSignId(final Long callSignId);

  CallSignLink loadActiveByDriverId(final Long driverId);

  List<CallSignLink> loadByDriverId(final Long driverId);

  CallSignLink loadByDriverIdAndDate(final Long driverId, final LocalDate date);

  List<CallSignLink> loadActiveByDate(final LocalDate date);

  List<CallSignLink> loadClosedByDate(final LocalDate date);

  Long loadCountActiveByDate(final LocalDate date);

  Long loadCountClosedByDate(final LocalDate date);
}
