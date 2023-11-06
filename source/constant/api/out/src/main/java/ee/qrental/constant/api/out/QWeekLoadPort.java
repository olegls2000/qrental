package ee.qrental.constant.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.constant.domain.QWeek;

import java.util.List;

public interface QWeekLoadPort extends LoadPort<QWeek> {

  List<QWeek> loadByYear(final Integer year);

  List<Integer> loadYears();

  QWeek loadByYearAndNumber(final Integer year, final Integer number);
}
