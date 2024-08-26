package ee.qrental.transaction.adapter.mapper.rent;

import ee.qrental.transaction.entity.jakarta.rent.RentCalculationJakartaEntity;
import ee.qrental.transaction.entity.jakarta.rent.RentCalculationResultJakartaEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RentCalculationAdapterMapperTest {

  private final RentCalculationAdapterMapper instance = new RentCalculationAdapterMapper();
    private final RentCalculationAdapterMapper instanceUnderTest = new RentCalculationAdapterMapper();

  @Test
  public void checkSmth() {
    @Test
    public void testIfMapEntityToDomainIsNull() {

        // given
        RentCalculationJakartaEntity domain = null;

        // when
        final var result = instanceUnderTest.mapToDomain(domain);

        // then
        assertNull(result);
    }

    @Test
    public void testShouldMapEntityToDomainSuccessfully() {

        // given
        RentCalculationJakartaEntity entity =
                RentCalculationJakartaEntity.builder()
                        .id(3L)
                        .actionDate(LocalDate.of(2024, Month.JANUARY, 13))
                        .qWeekId(44L)
                        .comment("comment")
                        .results(
                                Collections.singletonList(
                                        RentCalculationResultJakartaEntity.builder()
                                                .id(55L)
                                                .transactionId(66L)
                                                .carLinkId(77L)
                                                .build()))
                        .build();

        // when
        final var domain = instanceUnderTest.mapToDomain(entity);

        // then
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
    public void testShouldMapEntityToDomainWhenResultsAreNull() {

        // given
        RentCalculationJakartaEntity entity =
                RentCalculationJakartaEntity.builder()
                        .id(3L)
                        .actionDate(LocalDate.of(2024, Month.JANUARY, 13))
                        .qWeekId(44L)
                        .comment("comment")
                        .results(null)
                        .build();

        // when
        final var domain = instanceUnderTest.mapToDomain(entity);

        // then
        assertNotNull(domain);
        assertEquals(3L, domain.getId());
        assertEquals(LocalDate.of(2024, Month.JANUARY, 13), domain.getActionDate());
        assertEquals(44L, domain.getQWeekId());
        assertEquals("comment", domain.getComment());
        assertNull(domain.getResults());
    }
}
