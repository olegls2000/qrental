package ee.qrent.common.core.validation;

import ee.qrent.common.in.validation.ViolationsCollector;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class AttributeCheckerImplTest {
  private final AttributeCheckerImpl instanceUnderTest = new AttributeCheckerImpl();

  @Test
  public void testCheckDecimalValueRangeIfParameterAttributeNameIsNull() {
    // given
    final String attributeName = null;
    final BigDecimal attributeValue = BigDecimal.valueOf(2);
    final BigDecimal minValue = BigDecimal.valueOf(1);
    final BigDecimal maxValue = BigDecimal.valueOf(3);
    final ViolationsCollector violationsCollector = new ViolationsCollector();

    // when
    Exception exception = null;
    try {
      instanceUnderTest.checkDecimalValueRange(
          attributeName, attributeValue, minValue, maxValue, violationsCollector);
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertEquals(RuntimeException.class, exception.getClass());
    assertEquals(
        "Invalid AttributeChecker method call. Please check all non null parameters.",
        exception.getMessage());
  }

  @Test
  public void testCheckDecimalValueRangeIfParametersMinValueIsNull() {
    // given
    final String attributeName = "Attribute Name";
    final BigDecimal attributeValue = BigDecimal.valueOf(2);
    final BigDecimal minValue = null;
    final BigDecimal maxValue = BigDecimal.valueOf(3);
    final ViolationsCollector violationsCollector = new ViolationsCollector();

    // when
    Exception exception = null;
    try {
      instanceUnderTest.checkDecimalValueRange(
          attributeName, attributeValue, minValue, maxValue, violationsCollector);
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertEquals(RuntimeException.class, exception.getClass());
    assertEquals(
        "Invalid AttributeChecker method call. Please check all non null parameters.",
        exception.getMessage());
  }

  @Test
  public void testCheckDecimalValueRangeIfParametersMaxValueIsNull() {
    // given
    final String attributeName = "Attribute Name";
    final BigDecimal attributeValue = BigDecimal.valueOf(2);
    final BigDecimal minValue = BigDecimal.valueOf(3);
    final BigDecimal maxValue = null;
    final ViolationsCollector violationsCollector = new ViolationsCollector();

    // when
    Exception exception = null;
    try {
      instanceUnderTest.checkDecimalValueRange(
          attributeName, attributeValue, minValue, maxValue, violationsCollector);
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertEquals(RuntimeException.class, exception.getClass());
    assertEquals(
        "Invalid AttributeChecker method call. Please check all non null parameters.",
        exception.getMessage());
  }

  @Test
  public void testCheckDecimalValueRangeIfParametersViolationsCollectorIsNull() {
    // given
    final String attributeName = "Attribute Name";
    final BigDecimal attributeValue = BigDecimal.valueOf(2);
    final BigDecimal minValue = BigDecimal.valueOf(3);
    final BigDecimal maxValue = BigDecimal.valueOf(5);
    final ViolationsCollector violationsCollector = null;

    // when
    Exception exception = null;
    try {
      instanceUnderTest.checkDecimalValueRange(
          attributeName, attributeValue, minValue, maxValue, violationsCollector);
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertEquals(RuntimeException.class, exception.getClass());
    assertEquals(
        "Invalid AttributeChecker method call. Please check all non null parameters.",
        exception.getMessage());
  }

  @Test
  public void testCheckDecimalValueRangeIfParameterAttributeValueIsNull() {
    // given
    final String attributeName = "some name";
    final BigDecimal attributeValue = null;
    final BigDecimal minValue = BigDecimal.valueOf(1);
    final BigDecimal maxValue = BigDecimal.valueOf(3);
    final ViolationsCollector violationsCollector = new ViolationsCollector();

    // when
    Exception exception = null;
    try {
      instanceUnderTest.checkDecimalValueRange(
          attributeName, attributeValue, minValue, maxValue, violationsCollector);
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertNull(exception);
  }

  @Test
  public void testCheckDecimalValueRangeIfParameterAttributeValueIsInRange() {
    // given
    final String attributeName = "some name";
    final BigDecimal attributeValue = BigDecimal.valueOf(1);
    final BigDecimal minValue = BigDecimal.valueOf(1);
    final BigDecimal maxValue = BigDecimal.valueOf(9);
    final ViolationsCollector violationsCollector = new ViolationsCollector();

    // when
    instanceUnderTest.checkDecimalValueRange(
        attributeName, attributeValue, minValue, maxValue, violationsCollector);

    // then
    assertFalse(violationsCollector.hasViolations());
  }

  @Test
  public void testCheckDecimalValueRangeIfParameterAttributeValueIsOutOfRange1() {
    // given
    final String attributeName = "some name";
    final BigDecimal attributeValue = BigDecimal.valueOf(0);
    final BigDecimal minValue = BigDecimal.valueOf(1);
    final BigDecimal maxValue = BigDecimal.valueOf(9);
    final ViolationsCollector violationsCollector = new ViolationsCollector();

    // when
    instanceUnderTest.checkDecimalValueRange(
        attributeName, attributeValue, minValue, maxValue, violationsCollector);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(
        "Invalid value for some name. Valid value must be in a range: [1 ... 9]",
        violationsCollector.getViolations().get(0));
  }

  @Test
  public void testCheckDecimalValueRangeIfParameterAttributeValueIsOutOfRange2() {
    // given
    final String attributeName = "some name";
    final BigDecimal attributeValue = BigDecimal.valueOf(10);
    final BigDecimal minValue = BigDecimal.valueOf(1);
    final BigDecimal maxValue = BigDecimal.valueOf(9);
    final ViolationsCollector violationsCollector = new ViolationsCollector();

    // when
    instanceUnderTest.checkDecimalValueRange(
        attributeName, attributeValue, minValue, maxValue, violationsCollector);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(
        "Invalid value for some name. Valid value must be in a range: [1 ... 9]",
        violationsCollector.getViolations().get(0));
  }

  @Test
  public void testCheckStringLengthRangeIfParameterAttributeValueLengthIsOutOfRange1() {
    // given
    final String attributeName = "some name";
    final String attributeValue = "123456";
    final Integer minValue = Integer.valueOf(2);
    final Integer maxValue = Integer.valueOf(5);
    final ViolationsCollector violationsCollector = new ViolationsCollector();

    // when
    instanceUnderTest.checkStringLengthRange(
        attributeName, attributeValue, minValue, maxValue, violationsCollector);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(
        "Invalid value for some name. Current length: 6. Valid length must be in a range: [2 ... 5]",
        violationsCollector.getViolations().get(0));
  }

  @Test
  public void testCheckStringLengthRangeIfParameterAttributeValueLengthIsOutOfRange2() {
    // given
    final String attributeName = "some name";
    final String attributeValue = "1";
    final Integer minValue = Integer.valueOf(2);
    final Integer maxValue = Integer.valueOf(5);
    final ViolationsCollector violationsCollector = new ViolationsCollector();

    // when
    instanceUnderTest.checkStringLengthRange(
        attributeName, attributeValue, minValue, maxValue, violationsCollector);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(
        "Invalid value for some name. Current length: 1. Valid length must be in a range: [2 ... 5]",
        violationsCollector.getViolations().get(0));
  }

  @Test
  public void testCheckStringLengthFixedIfParameterAttributeValueLengthIsNotEqual() {
    // given
    final String attributeName = "some name";
    final String attributeValue = "123";
    final Integer length = Integer.valueOf(2);
    final ViolationsCollector violationsCollector = new ViolationsCollector();

    // when
    instanceUnderTest.checkStringLengthFixed(
        attributeName, attributeValue, length, violationsCollector);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(
        "Invalid value for some name. Current length: 3. Valid length must equal to: 2",
        violationsCollector.getViolations().get(0));
  }

  @Test
  public void testCheckRequiredIfParameterAttributeValueIsNull() {
    // given
    final String attributeName = "some name";
    final String attributeValue = null;
    final ViolationsCollector violationsCollector = new ViolationsCollector();

    // when
    instanceUnderTest.checkRequired(attributeName, attributeValue, violationsCollector);

    // then
    assertTrue(violationsCollector.hasViolations());
    assertEquals(
        "Invalid value for some name. Value must be set",
        violationsCollector.getViolations().get(0));
  }
}
