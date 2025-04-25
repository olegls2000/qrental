package ee.qrent.billing.insurance.core.service.kasko;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrent.billing.contract.api.in.query.GetContractQuery;
import ee.qrent.billing.contract.api.in.response.ContractResponse;
import ee.qrent.billing.insurance.core.service.kasko.QKaskoQueryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

public class QKaskoQueryServiceTest {

  private QKaskoQueryService instanceUnderTest;

  private GetContractQuery contractQuery;

  @BeforeEach
  void setUp() {
    contractQuery = mock(GetContractQuery.class);
    instanceUnderTest = new QKaskoQueryService(contractQuery);
  }

  @Test
  public void testIfNoActiveContract() {
    // given
    final var driverId = 5L;
    final var qWeekId = 60L;
    when(contractQuery.getActiveContractByDriverIdAndQWeekId(driverId, qWeekId)).thenReturn(null);

    // when
    final var result = instanceUnderTest.hasQKasko(driverId, qWeekId);

    // then
    assertFalse(result);
  }

  @Test
  public void testIfActiveContractHasDurationLessThanTwelveWeeks() {
    // given
    final var driverId = 5L;
    final var qWeekId = 60L;
    when(contractQuery.getActiveContractByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(ContractResponse.builder().duration(11).build());

    // when
    final var result = instanceUnderTest.hasQKasko(driverId, qWeekId);

    // then
    assertFalse(result);
  }

  @Test
  public void testIfActiveContractHasDurationTwelveWeeks() {
    // given
    final var driverId = 5L;
    final var qWeekId = 60L;
    when(contractQuery.getActiveContractByDriverIdAndQWeekId(driverId, qWeekId))
        .thenReturn(
            ContractResponse.builder()
                .duration(12)
                .dateStart(LocalDate.of(2024, Month.JANUARY, 21))
                .build());

    // when
    final var result = instanceUnderTest.hasQKasko(driverId, qWeekId);

    // then
    assertTrue(result);
  }
}
