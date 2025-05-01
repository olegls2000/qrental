package ee.qrent.billing.contract.core.service.pdf.strategy;

import ee.qrent.billing.contract.api.out.ContractLoadPort;
import ee.qrent.billing.contract.core.service.pdf.ContractPdfModel;
import ee.qrent.billing.contract.domain.Contract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ContractToPdfConversionStrategyAfterMay2025For12WeeksNewDriversTest {
  private ContractToPdfConversionStrategyAfterMay2025For12WeeksNewDrivers instanceUnderTest;
  private ContractLoadPort loadPort;

  @BeforeEach
  void init() {
    loadPort = mock(ContractLoadPort.class);
    instanceUnderTest =
        new ContractToPdfConversionStrategyAfterMay2025For12WeeksNewDrivers(loadPort);
  }

  @Test
  public void testIfDriverIsNewTwelveWeeksStartedBeforeMay2025AndDriversContractsMoreThan1() {
    // given
    final var driverId = 11L;
    final var contract = Contract.builder().build();
    final var contract2 = Contract.builder().build();
    final var pdfModel =
        ContractPdfModel.builder()
            .driverId(driverId)
            .dateStart(LocalDate.of(2025, Month.APRIL, 29))
            .duration("kaksteist")
            .build();
    when(loadPort.loadAllByDriverId(driverId)).thenReturn(List.of(contract, contract2));

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testIfDriverIsNewTwelveWeeksStartedBeforeMay2025() {
    // given
    final var driverId = 11L;
    final var contract = Contract.builder().build();
    final var contract2 = Contract.builder().build();
    final var pdfModel =
        ContractPdfModel.builder()
            .driverId(driverId)
            .dateStart(LocalDate.of(2025, Month.APRIL, 27))
            .duration("kaksteist")
            .build();
    when(loadPort.loadAllByDriverId(driverId)).thenReturn(List.of(contract, contract2));

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testIfDriverIsNewFourWeeksStartedAfterMay2025() {
    // given
    final var driverId = 11L;
    final var contract = Contract.builder().build();
    final var pdfModel =
        ContractPdfModel.builder()
            .driverId(driverId)
            .dateStart(LocalDate.of(2025, Month.APRIL, 28))
            .duration("neli")
            .build();
    when(loadPort.loadAllByDriverId(driverId)).thenReturn(singletonList(contract));

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testIfDriverIsNewTwelveWeeksStartedAfterMay2025() {
    // given
    final var driverId = 11L;
    final var contract = Contract.builder().build();
    final var pdfModel =
        ContractPdfModel.builder()
            .driverId(driverId)
            .dateStart(LocalDate.of(2025, Month.APRIL, 28))
            .duration("kaksteist")
            .build();
    when(loadPort.loadAllByDriverId(driverId)).thenReturn(singletonList(contract));

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertTrue(canApply);
  }
}
