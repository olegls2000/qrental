package ee.qrental.constant.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.constant.domain.QWeek;

public interface QWeekLoadPort extends LoadPort<QWeek> {

    QWeek loadByYearAndNumber(final Integer year, final Integer number);

}
