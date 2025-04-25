package ee.qrent.billing.contract.core.service.pdf;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.util.List;

import static java.lang.String.format;

@RequiredArgsConstructor
public class ContractToPdfConverter {

  private final List<ContractToPdfConversionStrategy> strategies;

  @SneakyThrows
  public InputStream getPdfInputStream(final ContractPdfModel model) {

    return strategies.stream()
        .filter(strategy -> strategy.canApply(model))
        .findFirst()
        .orElseThrow(
            () ->
                new RuntimeException(
                    format(
                        "Contract %s has no Strategy for conversion for PDF!", model.getNumber())))
        .getPdfInputStream(model);
  }
}
