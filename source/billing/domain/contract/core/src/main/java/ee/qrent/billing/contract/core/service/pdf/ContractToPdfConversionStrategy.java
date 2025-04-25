package ee.qrent.billing.contract.core.service.pdf;

import java.io.InputStream;

public interface ContractToPdfConversionStrategy {
  boolean canApply(final ContractPdfModel contract);

  InputStream getPdfInputStream(final ContractPdfModel model);
}
