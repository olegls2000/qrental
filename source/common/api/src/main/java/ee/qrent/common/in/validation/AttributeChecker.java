package ee.qrent.common.in.validation;

import java.math.BigDecimal;

public interface AttributeChecker {
  void checkStringLengthRange(
      final String attributeName,
      final String attributeValue,
      final Integer minLength,
      final Integer maxLength,
      final ViolationsCollector violationsCollector);

  void checkStringLengthMax(
      final String attributeName,
      final String attributeValue,
      final Integer maxLength,
      final ViolationsCollector violationsCollector);

  void checkStringLengthFixed(
      final String attributeName,
      final Object attributeValue,
      final Integer length,
      final ViolationsCollector violationsCollector);

  void checkRequired(
      final String attributeName,
      final Object attributeValue,
      final ViolationsCollector violationsCollector);

  void checkDecimalValueRange(
      final String attributeName,
      final BigDecimal attributeValue,
      final BigDecimal minValue,
      final BigDecimal maxValue,
      final ViolationsCollector violationsCollector);
}
