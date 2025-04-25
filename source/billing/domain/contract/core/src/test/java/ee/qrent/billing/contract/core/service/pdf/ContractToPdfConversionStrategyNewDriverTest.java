package ee.qrent.billing.contract.core.service.pdf;

import ee.qrent.billing.contract.api.out.ContractLoadPort;
import ee.qrent.billing.contract.domain.Contract;
import ee.qrent.billing.contract.domain.ContractDuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContractToPdfConversionStrategyNewDriverTest {
  private ContractToPdfConversionStrategyNewDriver instanceUnderTest;
  private ContractLoadPort loadPort;

  @BeforeEach
  void init() {
    loadPort = mock(ContractLoadPort.class);
    instanceUnderTest = new ContractToPdfConversionStrategyNewDriver(loadPort);
  }

  @Test
  public void testIfHasMoreThenOneContracts() {
    // given
    final var driverId = 11L;
    final var contract2024 =
        Contract.builder()
            .driverId(driverId)
            .dateStart(LocalDate.of(2024, Month.JANUARY, 1))
            .contractDuration(ContractDuration.TWELVE_WEEKS)
            .dateEnd(LocalDate.of(2024, Month.DECEMBER, 31))
            .build();
    final var contract2025 =
        Contract.builder()
            .driverId(driverId)
            .dateStart(LocalDate.of(2025, Month.JANUARY, 1))
            .contractDuration(ContractDuration.TWELVE_WEEKS)
            .dateEnd(null)
            .build();
    final var pdfModel = ContractPdfModel.builder().driverId(driverId).build();
    when(loadPort.loadAllByDriverId(driverId)).thenReturn(asList(contract2024, contract2025));

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testIfHasOneContractNotTwelveWeeks() {
    // given
    final var driverId = 11L;
    final var contract2025 =
        Contract.builder()
            .driverId(driverId)
            .dateStart(LocalDate.of(2025, Month.JANUARY, 1))
            .contractDuration(ContractDuration.FOUR_WEEKS)
            .dateEnd(null)
            .build();
    final var pdfModel = ContractPdfModel.builder().driverId(driverId).build();
    when(loadPort.loadAllByDriverId(driverId)).thenReturn(singletonList(contract2025));

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testIfHasOneContractTwelveWeeksStartedBefore2025() {
    // given
    final var driverId = 11L;
    final var contract2024 =
        Contract.builder()
            .driverId(driverId)
            .dateStart(LocalDate.of(2024, Month.DECEMBER, 31))
            .contractDuration(ContractDuration.TWELVE_WEEKS)
            .dateEnd(null)
            .build();
    final var pdfModel = ContractPdfModel.builder().driverId(driverId).build();
    when(loadPort.loadAllByDriverId(driverId)).thenReturn(singletonList(contract2024));

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testIfHasOneContractTwelveWeeksStartedAfter2024() {
    // given
    final var driverId = 11L;
    final var contract2024 =
        Contract.builder()
            .driverId(driverId)
            .dateStart(LocalDate.of(2025, Month.JANUARY, 11))
            .contractDuration(ContractDuration.TWELVE_WEEKS)
            .dateEnd(null)
            .build();
    final var pdfModel = ContractPdfModel.builder().driverId(driverId).build();
    when(loadPort.loadAllByDriverId(driverId)).thenReturn(singletonList(contract2024));

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertTrue(canApply);
  }
}
