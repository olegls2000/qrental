package ee.qrent.common.utils;

import static java.lang.String.format;
//TODO to remove

import lombok.Getter;

public enum QWeek {
  ALL("all", 0),
  FIRST("Jan 2-8", 1),
  SECOND("Jan 9-15", 2),
  THIRD("Jan 16-22", 3),
  FORTH("Jan 23-29", 4),
  FIFTH("Jan-Feb 30-5", 5),
  SIXTH("Feb 6-12", 6),
  SEVENTH("Feb 13-19", 7),
  EIGHTH("Feb 20-26", 8),
  NINTH("Feb-Mar 27-5", 9),
  TENTH("Mar 6-12", 10),
  ELEVENTH("Mar 13-19", 11),
  TWELFTH("Mar 20-26", 12),
  THIRTEENTH("Mar-Apr 27-2", 13),
  FOURTEENTH("Apr 3-9", 14),
  FIFTEENTH("Apr 10-16", 15),
  SIXTEENTH("Apr 17-23", 16),
  SEVENTEENTH("Apr 24-30", 17),
  EIGHTEENTH("May 1-7", 18),
  NINETEENTH("May 8-14", 19),
  TWENTIETH("May 15-21", 20),
  TWENTY_FIRST("May 22-28", 21),
  TWENTY_SECOND("May-Jun 29-4", 22),
  TWENTY_THIRD("Jun 5-11", 23),
  TWENTY_FORTH("Jun 12-18", 24),
  TWENTY_FIFTH("Jun 19-25", 25),
  TWENTY_SIXTH("Jun-Jul 26-2", 26),
  TWENTY_SEVENTH("Jul 3-9", 27),
  TWENTY_EIGHTH("Jul 10-16", 28),
  TWENTY_NINTH("Jul 17-23", 29),
  THIRTIETH("Jul 24-30", 30),
  THIRTY_FIRST("Jul-Aug 31-6", 31),
  THIRTY_SECOND("Aug 7-13", 32),
  THIRTY_THIRD("Aug 14-20", 33),
  THIRTY_FORTH("Aug 21-27", 34),
  THIRTY_FIFTH("Aug-Sep 28-3", 35),
  THIRTY_SIXTH("Sep 4-10", 36),
  THIRTY_SEVENTH("Sep 11-17", 37),
  THIRTY_EIGHTH("Sep 18-24", 38),
  THIRTY_NINTH("Sep-Oct 25-1", 39),
  FORTY("Oct 2-8", 40),
  FORTY_FIRST("Oct 9-15", 41),
  FORTY_SECOND("Oct 16-22", 42),
  FORTY_THIRD("Oct 23-29", 43),
  FORTY_FORTH("Oct-Nov 30-5", 44),
  FORTY_FIFTH("Nov 6-12", 45),
  FORTY_SIXTH("Nov 13-19", 46),
  FORTY_SEVENTH("Nov 20-26", 47),
  FORTY_EIGHTH("Nov-Dec 27-3", 48);
  private String month;

  @Getter private Integer number;

  QWeek(final String month, final Integer number) {
    this.month = month;
    this.number = number;
  }

  public String getDisplayValue() {
    if (this.number == 0) {
      return "All Weeks";
    }
    return format("Week %d ( %s )", number, month);
  }
}
