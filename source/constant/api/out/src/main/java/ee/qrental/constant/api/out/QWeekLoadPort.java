package ee.qrental.constant.api.out;


import ee.qrent.common.out.port.LoadPort;
import ee.qrental.constant.domain.QWeek;

import java.util.List;

public interface QWeekLoadPort extends LoadPort<QWeek> {

  List<QWeek> loadByYear(final Integer year);

  List<QWeek> loadAllBetweenByIds(final Long startWeekId, final Long endWeekId);
  List<QWeek> loadAllBeforeById(final Long id);

  List<QWeek> loadAllAfterById(final Long id);

  QWeek loadByYearAndNumber(final Integer year, final Integer number);
}
