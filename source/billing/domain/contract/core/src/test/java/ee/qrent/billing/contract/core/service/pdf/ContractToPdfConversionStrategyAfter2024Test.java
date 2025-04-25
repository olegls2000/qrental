package ee.qrent.billing.contract.core.service.pdf;

import ee.qrent.billing.contract.core.service.pdf.ContractPdfModel;
import ee.qrent.billing.contract.core.service.pdf.ContractToPdfConversionStrategyAfter2024;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class ContractToPdfConversionStrategyAfter2024Test {
  private ContractToPdfConversionStrategyAfter2024 instanceUnderTest =
      new ContractToPdfConversionStrategyAfter2024();

  @Test
  public void testIfContractStartedBeforeTheNewContractsData() {
    // given
    final var pdfModel =
        ContractPdfModel.builder().created(LocalDate.of(2024, Month.DECEMBER, 31)).build();

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertFalse(canApply);
  }

  @Test
  public void testIfContractStartedOnTheNewContractsData() {
    // given
    final var pdfModel =
        ContractPdfModel.builder().created(LocalDate.of(2025, Month.JANUARY, 1)).build();

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertTrue(canApply);
  }

  @Test
  public void testIfContractStartedAfterTheNewContractsData() {
    // given
    final var pdfModel =
        ContractPdfModel.builder().created(LocalDate.of(2025, Month.JANUARY, 2)).build();

    // when
    final var canApply = instanceUnderTest.canApply(pdfModel);

    // then
    assertTrue(canApply);
  }
}
