package ee.qrental.transaction.adapter.mapper.balance;

import ee.qrental.transaction.domain.balance.Balance;
import ee.qrental.transaction.entity.jakarta.balance.BalanceJakartaEntity;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class BalanceAdapterMapperTest {
    private final BalanceAdapterMapper instance = new BalanceAdapterMapper();

    @Test
    public void testIfMapEntityToDomainIsNull() {

        // given
        BalanceJakartaEntity domain = null;

        // when
        final var result = instance.mapToDomain(domain);

        // then
        assertNull(result);
    }

    @Test
    public void testShouldMapEntityToDomainSuccessfully() {

        // given
        BalanceJakartaEntity entity =
                BalanceJakartaEntity.builder()
                        .id(3L)
                        .driverId(5L)
                        .created(LocalDate.of(2024, Month.FEBRUARY, 21))
                        .feeAbleAmount(BigDecimal.valueOf(10.5))
                        .nonFeeAbleAmount(BigDecimal.valueOf(5.1))
                        .positiveAmount(BigDecimal.valueOf(2.7))
                        .feeAmount(BigDecimal.valueOf(8.1))
                        .repairmentAmount(BigDecimal.valueOf(25.9))
                        .qWeekId(45L)
                        .derived(true)
                        .build();
        // when
        final var domain = instance.mapToDomain(entity);

        // then
        assertNotNull(domain);
        assertEquals(3L, domain.getId());
        assertEquals(5L, domain.getDriverId());
        assertEquals(LocalDate.of(2024, Month.FEBRUARY, 21), domain.getCreated());
        assertEquals(BigDecimal.valueOf(10.5), domain.getFeeAbleAmount());
        assertEquals(BigDecimal.valueOf(5.1), domain.getNonFeeAbleAmount());
        assertEquals(BigDecimal.valueOf(2.7), domain.getPositiveAmount());
        assertEquals(BigDecimal.valueOf(8.1), domain.getFeeAmount());
        assertEquals(BigDecimal.valueOf(25.9), domain.getRepairmentAmount());
        assertEquals(45L, domain.getQWeekId());
        assertTrue(domain.getDerived());
    }

    @Test
    public void testShouldMapToEntitySuccessfully() {

        // given
        Balance domain =
                Balance.builder()
                        .id(3L)
                        .driverId(5L)
                        .created(LocalDate.of(2024, Month.FEBRUARY, 21))
                        .feeAbleAmount(BigDecimal.valueOf(10.5))
                        .nonFeeAbleAmount(BigDecimal.valueOf(5.1))
                        .positiveAmount(BigDecimal.valueOf(2.7))
                        .feeAmount(BigDecimal.valueOf(8.1))
                        .repairmentAmount(BigDecimal.valueOf(25.9))
                        .qWeekId(45L)
                        .derived(true)
                        .build();

        // when
        final var entity = instance.mapToEntity(domain);
        final var amountSumWithoutRepairment = domain.getAmountsSumWithoutRepairment();
        final var stringBalance = domain.toString();

        // then
        assertEquals("Balance{id=3, driverId=5, qWeekId=45, created=2024-02-21, feeAbleAmount=10.5, nonFeeAbleAmount=5.1, feeAmount=8.1, repairmentAmount=25.9, positiveAmount=2.7, derived=true}", stringBalance);
        assertEquals(BigDecimal.valueOf(-21.0), amountSumWithoutRepairment);
        assertEquals(3L, entity.getId());
        assertEquals(5L, entity.getDriverId());
        assertEquals(LocalDate.of(2024, Month.FEBRUARY, 21), entity.getCreated());
        assertEquals(BigDecimal.valueOf(10.5), entity.getFeeAbleAmount());
        assertEquals(BigDecimal.valueOf(5.1), entity.getNonFeeAbleAmount());
        assertEquals(BigDecimal.valueOf(2.7), entity.getPositiveAmount());
        assertEquals(BigDecimal.valueOf(8.1), entity.getFeeAmount());
        assertEquals(BigDecimal.valueOf(25.9), entity.getRepairmentAmount());
        assertEquals(45L, entity.getQWeekId());
        assertTrue(entity.getDerived());
    }
}