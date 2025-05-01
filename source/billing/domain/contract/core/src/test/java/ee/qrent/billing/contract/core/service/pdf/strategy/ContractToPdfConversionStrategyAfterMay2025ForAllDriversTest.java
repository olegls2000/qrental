package ee.qrent.billing.contract.core.service.pdf.strategy;

import ee.qrent.billing.contract.api.out.ContractLoadPort;
import ee.qrent.billing.contract.core.service.pdf.ContractPdfModel;
import ee.qrent.billing.contract.domain.Contract;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContractToPdfConversionStrategyAfterMay2025ForAllDriversTest {
  private ContractToPdfConversionStrategyAfterMay2025ForAllDrivers instanceUnderTest;
  private ContractLoadPort loadPort;

  @BeforeEach
  void init() {
    loadPort = mock(ContractLoadPort.class);
    instanceUnderTest = new ContractToPdfConversionStrategyAfterMay2025ForAllDrivers(loadPort);
  }

  @Test
  public void testIfDriverIsNotNewTwelveWeeksStartedBeforeMay2025AndDriversContractsMoreThan1() {
    // given
    final var driverId = 11L;
    final var contract = Contract.builder().build();
    final var contract2 = Contract.builder().build();
    final var pdfModel =
            ContractPdfModel.builder()
                    .driverId(driverId)
                    .dateStart(LocalDate.of(2025, Month.APRIL, 28))
                    .duration("TWELVE_WEEKS")
                    .build();
    when(loadPort.loadAllByDriverId(driverId)).thenReturn(List.of(contract, contract2));

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertFalse(canApply);
  }

//  @Test
//  public void testIfDriverIsNotNewTwelveWeeksStartedBeforeMay2025AndDriversContractsMoreThan1_2() {
//    // given
//    final var driverId = 11L;
//    final var contract = Contract.builder().build();
//    final var contract2 = Contract.builder().build();
//    final var pdfModel =
//            ContractPdfModel.builder()
//                    .driverId(driverId)
//                    .dateStart(LocalDate.of(2025, Month.APRIL, 27))
//                    .duration("kaksteist")
//                    .build();
//    when(loadPort.loadAllByDriverId(driverId)).thenReturn(List.of(contract, contract2));
//
//    // when
//    final var canApply = instanceUnderTest.canApply(pdfModel);
//
//    // then
//    assertFalse(canApply);
//  }
}
