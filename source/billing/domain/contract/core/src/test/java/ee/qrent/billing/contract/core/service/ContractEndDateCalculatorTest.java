package ee.qrent.billing.contract.core.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.contract.domain.Contract;
import ee.qrent.billing.contract.domain.ContractDuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

class ContractEndDateCalculatorTest {
  private ContractEndDateCalculator instanceUnderTest;
  private QDateTime qDateTime;

  @BeforeEach
  void init() {
    qDateTime = mock(QDateTime.class);
    instanceUnderTest = new ContractEndDateCalculator(qDateTime);
  }

  @Test
  public void testIfContractIsNull() {
    // given
    final Contract contract = null;

    // when
    instanceUnderTest.setEndDate(contract);

    // then
    assertNull(contract);
  }

  @Test
  public void testIfContractEndDateIsNull() {
    // given
    final var contract = Contract.builder().dateEnd(LocalDate.of(2024, Month.JANUARY, 01)).build();

    // when
    instanceUnderTest.setEndDate(contract);

    // then
    final var contractEndDate = contract.getDateEnd();
    assertEquals(LocalDate.of(2024, Month.JANUARY, 01), contractEndDate);
  }

  @Test
  public void testIfContractEndDateIsNotNullAndDuration4WeeksAndPassed2WeeksFromStart() {
    // given
    final var contract =
        Contract.builder()
            .dateStart(LocalDate.of(2024, Month.JANUARY, 01)) // MONDAY
            .contractDuration(ContractDuration.FOUR_WEEKS)
            .dateEnd(null)
            .build();
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2024, Month.JANUARY, 15)); // SUNDAY

    // when
    instanceUnderTest.setEndDate(contract);

    // then
    final var contractEndDate = contract.getDateEnd();
    assertEquals(LocalDate.of(2024, Month.JANUARY, 29), contractEndDate);
  }

  @Test
  public void testIfContractEndDateIsNotNullAndDuration4WeeksAndPassed5WeeksFromStart() {
    // given
    final var contract =
            Contract.builder()
                    .dateStart(LocalDate.of(2024, Month.JANUARY, 01)) // MONDAY
                    .contractDuration(ContractDuration.FOUR_WEEKS)
                    .dateEnd(null)
                    .build();
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2024, Month.FEBRUARY, 5)); // SUNDAY

    // when
    instanceUnderTest.setEndDate(contract);

    // then
    final var contractEndDate = contract.getDateEnd();
    assertEquals(LocalDate.of(2024, Month.FEBRUARY, 26), contractEndDate);
  }
}
