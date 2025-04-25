package ee.qrent.billing.contract.core.service.pdf;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;

import ee.qrent.billing.contract.core.service.pdf.ContractPdfModel;
import ee.qrent.billing.contract.core.service.pdf.ContractToPdfConversionStrategyBefore2025;
import org.junit.jupiter.api.Test;

class ContractToPdfConversionStrategyBefore2025Test {
  private ContractToPdfConversionStrategyBefore2025 instanceUnderTest =
      new ContractToPdfConversionStrategyBefore2025();

  @Test
  public void testIfContractStartedBeforeTheNewContractsData() {
    // given
    final var pdfModel =
        ContractPdfModel.builder().created(LocalDate.of(2024, Month.DECEMBER, 31)).build();

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertTrue(canApply);
  }

  @Test
  public void testIfContractStartedOnTheNewContractsData() {
    // given
    final var pdfModel =
        ContractPdfModel.builder().created(LocalDate.of(2025, Month.JANUARY, 1)).build();

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testIfContractStartedAfterTheNewContractsData() {
    // given
    final var pdfModel =
        ContractPdfModel.builder().created(LocalDate.of(2025, Month.JANUARY, 2)).build();

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertFalse(canApply);
  }
}
