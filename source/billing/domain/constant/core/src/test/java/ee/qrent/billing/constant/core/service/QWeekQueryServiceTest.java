package ee.qrent.billing.constant.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.constant.api.in.response.qweek.QWeekResponse;
import ee.qrent.billing.constant.api.out.QWeekLoadPort;
import ee.qrent.billing.constant.core.mapper.QWeekResponseMapper;
import ee.qrent.billing.constant.core.mapper.QWeekUpdateRequestMapper;
import ee.qrent.billing.constant.domain.QWeek;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class QWeekQueryServiceTest {
  private QWeekQueryService instanceUnderTest;
  private QWeekLoadPort loadPort;
  private QWeekResponseMapper mapper;
  private QWeekUpdateRequestMapper updateRequestMapper;
  private QDateTime qDateTime;
  private QWeekUseCaseService qWeekUseCaseService;

  @BeforeEach
  void init() {
    loadPort = mock(QWeekLoadPort.class);
    mapper = mock(QWeekResponseMapper.class);
    updateRequestMapper = mock(QWeekUpdateRequestMapper.class);
    qDateTime = mock(QDateTime.class);
    qWeekUseCaseService = mock(QWeekUseCaseService.class);
    instanceUnderTest =
        new QWeekQueryService(
            loadPort, mapper, updateRequestMapper, qDateTime, qWeekUseCaseService);
  }

  @Test
  public void testGetCurrentWeekForTheWeekBetweenYears() {
    // given
    final var qWeek = QWeek.builder().number(1).year(2025).build();
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2024, Month.DECEMBER, 30));
    when(loadPort.loadByYearAndNumber(2025, 1)).thenReturn(qWeek);
    when(mapper.toResponse(qWeek)).thenReturn(QWeekResponse.builder().number(1).year(2025).build());

    // when
    final var currentWeek = instanceUnderTest.getCurrentWeek();

    // then
    assertEquals(2025, currentWeek.getYear());
    assertEquals(1, currentWeek.getNumber());
  }
}
