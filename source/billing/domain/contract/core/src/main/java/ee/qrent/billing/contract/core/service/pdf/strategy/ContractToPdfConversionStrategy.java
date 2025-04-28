package ee.qrent.billing.contract.core.service.pdf.strategy;

import ee.qrent.billing.contract.core.service.pdf.ContractPdfModel;

import java.io.InputStream;

public interface ContractToPdfConversionStrategy {
  boolean canApply(final ContractPdfModel contract);

  InputStream getPdfInputStream(final ContractPdfModel model);
}
