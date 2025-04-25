package ee.qrent.billing.contract.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.constant.api.in.query.GetQWeekQuery;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.out.AbsenceLoadPort;
import ee.qrent.billing.contract.core.mapper.AbsenceResponseMapper;
import ee.qrent.billing.contract.core.mapper.AbsenceUpdateRequestMapper;
import ee.qrent.billing.contract.domain.Absence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AbsenceQueryServiceTest {
  private AbsenceQueryService instanceUnderTest;
  private GetContractQuery contractQuery;
  private GetQWeekQuery qWeekQuery;
  private AbsenceLoadPort loadPort;
  private AbsenceResponseMapper mapper;
  private AbsenceUpdateRequestMapper updateRequestMapper;
  private QDateTime qDateTime;

  @BeforeEach
  void init() {
    contractQuery = mock(GetContractQuery.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    loadPort = mock(AbsenceLoadPort.class);
    mapper = mock(AbsenceResponseMapper.class);
    updateRequestMapper = mock(AbsenceUpdateRequestMapper.class);
    qDateTime = mock(QDateTime.class);
    instanceUnderTest =
        new AbsenceQueryService(
            contractQuery, qWeekQuery, loadPort, mapper, updateRequestMapper, qDateTime);
  }

  // Test Week: 2 Dec 2024 ... 8 Dec 2024

  @Test
  public void testAbsenceDaysCountInWeekIfOneAbsenceWithStartDateBeforeWeekStartAndEndDateIsNull() {
    // given
    final var driverId = 1L;
    final var qWeekId = 5L;
    final var qWeekStartDate = LocalDate.of(2024, Month.DECEMBER, 2);
    final var qWeekEndDate = LocalDate.of(2024, Month.DECEMBER, 8);
    final var absence =
        Absence.builder().dateStart(LocalDate.of(2024, Month.DECEMBER, 1)).dateEnd(null).build();

    when(qWeekQuery.getById(qWeekId))
        .thenReturn(
            QWeekResponse.builder().id(qWeekId).start(qWeekStartDate).end(qWeekEndDate).build());

    when(loadPort.loadByDriverIdAndDateStartAndDateEnd(driverId, qWeekStartDate, qWeekEndDate))
        .thenReturn(singletonList(absence));

    // when
    final var daysCount =
        instanceUnderTest.getAbsencesDayCountsByDriverIdAndQWeekId(driverId, qWeekId);

    // then
    assertEquals(7, daysCount);
  }

  @Test
  public void testAbsenceDaysCountInWeekIfOneAbsenceWithStartDateSameAsWeekStartAndEndDateIsNull() {
    // given
    final var driverId = 1L;
    final var qWeekId = 5L;
    final var qWeekStartDate = LocalDate.of(2024, Month.DECEMBER, 2);
    final var qWeekEndDate = LocalDate.of(2024, Month.DECEMBER, 8);
    final var absence =
        Absence.builder().dateStart(LocalDate.of(2024, Month.DECEMBER, 2)).dateEnd(null).build();

    when(qWeekQuery.getById(qWeekId))
        .thenReturn(
            QWeekResponse.builder().id(qWeekId).start(qWeekStartDate).end(qWeekEndDate).build());

    when(loadPort.loadByDriverIdAndDateStartAndDateEnd(driverId, qWeekStartDate, qWeekEndDate))
        .thenReturn(singletonList(absence));

    // when
    final var daysCount =
        instanceUnderTest.getAbsencesDayCountsByDriverIdAndQWeekId(driverId, qWeekId);

    // then
    assertEquals(7, daysCount);
  }

  @Test
  public void testAbsenceDaysCountInWeekIfOneAbsenceWithStartDateAfterWeekStartAndEndDateIsNull() {
    // given
    final var driverId = 1L;
    final var qWeekId = 5L;
    final var qWeekStartDate = LocalDate.of(2024, Month.DECEMBER, 2);
    final var qWeekEndDate = LocalDate.of(2024, Month.DECEMBER, 8);
    final var absence =
        Absence.builder().dateStart(LocalDate.of(2024, Month.DECEMBER, 3)).dateEnd(null).build();

    when(qWeekQuery.getById(qWeekId))
        .thenReturn(
            QWeekResponse.builder().id(qWeekId).start(qWeekStartDate).end(qWeekEndDate).build());

    when(loadPort.loadByDriverIdAndDateStartAndDateEnd(driverId, qWeekStartDate, qWeekEndDate))
        .thenReturn(singletonList(absence));

    // when
    final var daysCount =
        instanceUnderTest.getAbsencesDayCountsByDriverIdAndQWeekId(driverId, qWeekId);

    // then
    assertEquals(6, daysCount);
  }

  @Test
  public void
      testAbsenceDaysCountInWeekIfOneAbsenceWithStartDateAfterWeekStartAndEndDateIsBeforeWeekEndDate() {
    // given
    final var driverId = 1L;
    final var qWeekId = 5L;
    final var qWeekStartDate = LocalDate.of(2024, Month.DECEMBER, 2);
    final var qWeekEndDate = LocalDate.of(2024, Month.DECEMBER, 8);
    final var absence =
        Absence.builder()
            .dateStart(LocalDate.of(2024, Month.DECEMBER, 3))
            .dateEnd(LocalDate.of(2024, Month.DECEMBER, 7))
            .build();

    when(qWeekQuery.getById(qWeekId))
        .thenReturn(
            QWeekResponse.builder().id(qWeekId).start(qWeekStartDate).end(qWeekEndDate).build());

    when(loadPort.loadByDriverIdAndDateStartAndDateEnd(driverId, qWeekStartDate, qWeekEndDate))
        .thenReturn(singletonList(absence));

    // when
    final var daysCount =
        instanceUnderTest.getAbsencesDayCountsByDriverIdAndQWeekId(driverId, qWeekId);

    // then
    assertEquals(5, daysCount);
  }

  @Test
  public void
      testAbsenceDaysCountInWeekIfOneAbsenceWithStartDateAfterWeekStartAndEndDateSameAsWeekEndDate() {
    // given
    final var driverId = 1L;
    final var qWeekId = 5L;
    final var qWeekStartDate = LocalDate.of(2024, Month.DECEMBER, 2);
    final var qWeekEndDate = LocalDate.of(2024, Month.DECEMBER, 8);
    final var absence =
        Absence.builder()
            .dateStart(LocalDate.of(2024, Month.DECEMBER, 3))
            .dateEnd(LocalDate.of(2024, Month.DECEMBER, 8))
            .build();

    when(qWeekQuery.getById(qWeekId))
        .thenReturn(
            QWeekResponse.builder().id(qWeekId).start(qWeekStartDate).end(qWeekEndDate).build());

    when(loadPort.loadByDriverIdAndDateStartAndDateEnd(driverId, qWeekStartDate, qWeekEndDate))
        .thenReturn(singletonList(absence));

    // when
    final var daysCount =
        instanceUnderTest.getAbsencesDayCountsByDriverIdAndQWeekId(driverId, qWeekId);

    // then
    assertEquals(6, daysCount);
  }

  @Test
  public void
      testAbsenceDaysCountInWeekIfOneAbsenceWithStartDateAfterWeekStartAndEndDateAfterWeekEndDate() {
    // given
    final var driverId = 1L;
    final var qWeekId = 5L;
    final var qWeekStartDate = LocalDate.of(2024, Month.DECEMBER, 2);
    final var qWeekEndDate = LocalDate.of(2024, Month.DECEMBER, 8);
    final var absence =
        Absence.builder()
            .dateStart(LocalDate.of(2024, Month.DECEMBER, 3))
            .dateEnd(LocalDate.of(2024, Month.DECEMBER, 9))
            .build();

    when(qWeekQuery.getById(qWeekId))
        .thenReturn(
            QWeekResponse.builder().id(qWeekId).start(qWeekStartDate).end(qWeekEndDate).build());

    when(loadPort.loadByDriverIdAndDateStartAndDateEnd(driverId, qWeekStartDate, qWeekEndDate))
        .thenReturn(singletonList(absence));

    // when
    final var daysCount =
        instanceUnderTest.getAbsencesDayCountsByDriverIdAndQWeekId(driverId, qWeekId);

    // then
    assertEquals(6, daysCount);
  }

  @Test
  public void testAbsenceDaysCountInWeekIfTwoAbsencesHappened() {
    // given
    final var driverId = 1L;
    final var qWeekId = 5L;
    final var qWeekStartDate = LocalDate.of(2024, Month.DECEMBER, 2);
    final var qWeekEndDate = LocalDate.of(2024, Month.DECEMBER, 8);
    final var absence1 =
        Absence.builder()
            .dateStart(LocalDate.of(2024, Month.DECEMBER, 2))
            .dateEnd(LocalDate.of(2024, Month.DECEMBER, 4))
            .build();
    final var absence2 =
        Absence.builder()
            .dateStart(LocalDate.of(2024, Month.DECEMBER, 6))
            .dateEnd(LocalDate.of(2024, Month.DECEMBER, 7))
            .build();

    when(qWeekQuery.getById(qWeekId))
        .thenReturn(
            QWeekResponse.builder().id(qWeekId).start(qWeekStartDate).end(qWeekEndDate).build());

    when(loadPort.loadByDriverIdAndDateStartAndDateEnd(driverId, qWeekStartDate, qWeekEndDate))
        .thenReturn(Arrays.asList(absence1, absence2));

    // when
    final var daysCount =
        instanceUnderTest.getAbsencesDayCountsByDriverIdAndQWeekId(driverId, qWeekId);

    // then
    assertEquals(5, daysCount);
  }
}
