package ee.qrental.transaction.adapter.mapper.rent;

import ee.qrental.transaction.entity.jakarta.rent.RentCalculationJakartaEntity;
import ee.qrental.transaction.entity.jakarta.rent.RentCalculationResultJakartaEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RentCalculationAdapterMapperTest {

  private final RentCalculationAdapterMapper instance = new RentCalculationAdapterMapper();

  @Test
  public void checkSmth() {

    RentCalculationJakartaEntity entity = null;

    final var result = instance.mapToDomain(entity);

    assertNull(result);
  }

  @Test
  public void checkSmth2() {

    RentCalculationJakartaEntity entity =
        RentCalculationJakartaEntity.builder()
            .id(3L)
            .actionDate(LocalDate.of(2024, Month.JANUARY, 13))
            .qWeekId(44L)
            .comment("comment")
            .results(
                Arrays.asList(
                    RentCalculationResultJakartaEntity.builder()
                        .id(55L)
                        .transactionId(66L)
                        .carLinkId(77L)
                        .build()))
            .build();

    final var domain = instance.mapToDomain(entity);

    assertNotNull(domain);
    assertEquals(3L, domain.getId());
    assertEquals(LocalDate.of(2024, Month.JANUARY, 13), domain.getActionDate());
    assertEquals(44L, domain.getQWeekId());
    assertEquals("comment", domain.getComment());
    assertEquals(1, domain.getResults().size());

    final var calculationResult = domain.getResults().get(0);
    assertEquals(55L, calculationResult.getId());
    assertEquals(66L, calculationResult.getTransactionId());
    assertEquals(77L, calculationResult.getCarLinkId());
  }

  @Test
  public void checkSmth3() {
    RentCalculationJakartaEntity entity =
        RentCalculationJakartaEntity.builder()
            .id(3L)
            .actionDate(LocalDate.of(2024, Month.JANUARY, 13))
            .qWeekId(44L)
            .comment("comment")
            .results(null)
            .build();

    final var domain = instance.mapToDomain(entity);

    assertNotNull(domain);
    assertEquals(3L, domain.getId());
    assertEquals(LocalDate.of(2024, Month.JANUARY, 13), domain.getActionDate());
    assertEquals(44L, domain.getQWeekId());
    assertEquals("comment", domain.getComment());
    assertNull(domain.getResults());
  }
}
