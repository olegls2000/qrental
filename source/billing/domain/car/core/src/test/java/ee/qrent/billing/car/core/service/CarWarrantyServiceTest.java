package ee.qrent.billing.car.core.service;

import ee.qrent.common.in.time.QDateTime;
import ee.qrent.billing.car.domain.Car;
import ee.qrent.billing.car.domain.RagStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CarWarrantyServiceTest {
  private CarWarrantyService instanceUnderTest;
  private QDateTime qDateTime;

  @BeforeEach
  void init() {
    qDateTime = mock(QDateTime.class);
    instanceUnderTest = new CarWarrantyService(qDateTime);
  }

  @Test
  public void testEndWarrantyDateCalculation() {
    // given
    final var car = Car.builder().releaseDate(LocalDate.of(2020, Month.JANUARY, 1)).build();

    // when
    final var warrantyEndDate = instanceUnderTest.getWarrantyEndDate(car);

    // then
    assertEquals(LocalDate.of(2022, Month.JANUARY, 1), warrantyEndDate);
  }

  @Test
  public void testWarrantyMonthsIfWarrantyInDue() {
    // given
    final var car = Car.builder().releaseDate(LocalDate.of(2020, Month.JANUARY, 1)).build();
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2021, Month.JANUARY, 1));

    // when
    final var warrantyMonthsCount = instanceUnderTest.getWarrantyMonths(car);

    // then
    assertEquals(12, warrantyMonthsCount);
  }

  @Test
  public void testWarrantyMonthsIfWarrantyIsExpired() {
    // given
    final var car = Car.builder().releaseDate(LocalDate.of(2020, Month.JANUARY, 1)).build();
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2023, Month.JANUARY, 1));

    // when
    final var warrantyMonthsCount = instanceUnderTest.getWarrantyMonths(car);

    // then
    assertEquals(0, warrantyMonthsCount);
  }

  @Test
  public void testWarrantyRagStatusGreen() {
    // given
    final var car = Car.builder().releaseDate(LocalDate.of(2020, Month.JANUARY, 1)).build();
    when(qDateTime.getToday()).thenReturn(LocalDate.of(2020, Month.JANUARY, 2));

    // when
    final var status = instanceUnderTest.getWarrantyRagStatus(car);

    // then
    assertEquals(RagStatus.GREEN, status);
  }

  @Test
  public void testWarrantyRagStatusAmber() {
    // given
    final var car = Car.builder().releaseDate(LocalDate.of(2020, Month.JANUARY, 1)).build();

    when(qDateTime.getToday()).thenReturn(LocalDate.of(2022, Month.JANUARY, 1).minusDays(551));

    // when
    final var status = instanceUnderTest.getWarrantyRagStatus(car);

    // then
    assertEquals(RagStatus.AMBER, status);
  }

  @Test
  public void testWarrantyRagStatusRed() {
    // given
    final var car = Car.builder().releaseDate(LocalDate.of(2020, Month.JANUARY, 1)).build();

    when(qDateTime.getToday()).thenReturn(LocalDate.of(2022, Month.JANUARY, 1).minusDays(550));

    // when
    final var status = instanceUnderTest.getWarrantyRagStatus(car);

    // then
    assertEquals(RagStatus.RED, status);
  }
}
