package ee.qrental.bonus.core.validator;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrental.bonus.api.in.request.ObligationCalculationAddRequest;
import ee.qrental.bonus.api.out.ObligationCalculationLoadPort;
import ee.qrental.constant.api.in.query.GetQWeekQuery;
import ee.qrental.constant.api.in.response.qweek.QWeekResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObligationCalculationAddBusinessRuleValidatorTest {

  private ObligationCalculationAddBusinessRuleValidator instanceUnderTest;

  private GetQWeekQuery qWeekQuery;
  private ObligationCalculationLoadPort loadPort;

  @BeforeEach
  void init() {
    qWeekQuery = mock(GetQWeekQuery.class);
    loadPort = mock(ObligationCalculationLoadPort.class);
    qWeekQuery = mock(GetQWeekQuery.class);
    instanceUnderTest = new ObligationCalculationAddBusinessRuleValidator(qWeekQuery, loadPort);
  }

  @Test
  public void testIfNoPreviousWeek() {
    // given
    final var qWeekId = 5L;
    final var addRequest = new ObligationCalculationAddRequest();
    addRequest.setQWeekId(qWeekId);
    addRequest.setComment("test comment");

    final var currentQWeekId = 10L;
    final var currentQWeek = QWeekResponse.builder().id(currentQWeekId).build();
    when(qWeekQuery.getCurrentWeek()).thenReturn(currentQWeek);
    when(qWeekQuery.getOneBeforeById(qWeekId)).thenReturn(null);

    // when
    final var violationsCollector = instanceUnderTest.validate(addRequest);

    // then
    assertFalse(violationsCollector.hasViolations());
    assertTrue(violationsCollector.getViolations().isEmpty());
  }

  @Test
  public void testIfNoCalculationWasDoneAtAll() {
    // given
    final var qWeekId = 5L;
    final var addRequest = new ObligationCalculationAddRequest();
    addRequest.setQWeekId(qWeekId);
    addRequest.setComment("test comment");

    final var currentQWeekId = 4L;
    final var currentQWeek = QWeekResponse.builder().id(currentQWeekId).build();
    final var previousQWeekId = 4L;
    final var previousQWeek = QWeekResponse.builder().id(previousQWeekId).build();
    when(qWeekQuery.getCurrentWeek()).thenReturn(currentQWeek);
    when(qWeekQuery.getOneBeforeById(qWeekId)).thenReturn(previousQWeek);
    when(loadPort.loadLastCalculatedQWeekId()).thenReturn(null);

    // when
    final var violationsCollector = instanceUnderTest.validate(addRequest);

    // then
    assertFalse(violationsCollector.hasViolations());
    assertTrue(violationsCollector.getViolations().isEmpty());
  }

  @Test
  public void testIfLatestCalculationIsNotForPreviousWeek() {
    // given
    final var qWeekId = 5L;
    final var addRequest = new ObligationCalculationAddRequest();
    addRequest.setQWeekId(qWeekId);
    addRequest.setComment("test comment");

    final var previousQWeekId = 4L;
    final var previousQWeek = QWeekResponse.builder().id(previousQWeekId).build();
    when(qWeekQuery.getOneBeforeById(qWeekId)).thenReturn(previousQWeek);

    final var currentQWeekId = 4L;
    final var currentQWeek = QWeekResponse.builder().id(currentQWeekId).build();
    when(qWeekQuery.getCurrentWeek()).thenReturn(currentQWeek);
    when(loadPort.loadLastCalculatedQWeekId()).thenReturn(99L);

    // when
    final var violationsCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationsCollector.hasViolations());
  }

  @Test
  public void testIfCalculationForCurrentWeek() {
    // given
    final var qWeekId = 5L;
    final var addRequest = new ObligationCalculationAddRequest();
    addRequest.setQWeekId(qWeekId);
    addRequest.setComment("test comment");

    final var previousQWeekId = 4L;
    final var previousQWeek = QWeekResponse.builder().id(previousQWeekId).build();
    when(qWeekQuery.getOneBeforeById(qWeekId)).thenReturn(previousQWeek);

    final var currentQWeekId = 5L;
    final var currentQWeek = QWeekResponse.builder().id(currentQWeekId).build();
    when(qWeekQuery.getCurrentWeek()).thenReturn(currentQWeek);
    when(loadPort.loadLastCalculatedQWeekId()).thenReturn(99L);

    // when
    final var violationsCollector = instanceUnderTest.validate(addRequest);

    // then
    assertTrue(violationsCollector.hasViolations());
  }
}
