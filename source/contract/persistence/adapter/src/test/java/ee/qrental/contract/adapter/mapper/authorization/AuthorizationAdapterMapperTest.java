package ee.qrental.contract.adapter.mapper.authorization;

import ee.qrental.contract.adapter.mapper.AuthorizationAdapterMapper;
import ee.qrental.contract.domain.Authorization;
import ee.qrental.contract.entity.jakarta.AuthorizationJakartaEntity;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationAdapterMapperTest {
    private final AuthorizationAdapterMapper instanceUnderTest = new AuthorizationAdapterMapper();

    @Test
    public void testIfMapEntityToDomainIsNull() {

        // given
        AuthorizationJakartaEntity domain = null;

        // when
        final var result = instanceUnderTest.mapToDomain(domain);

        // then
        assertNull(result);
    }

    @Test
    public void testShouldMapEntityToDomainSuccessfully() {

        // given
        AuthorizationJakartaEntity entity =
                AuthorizationJakartaEntity.builder()
                        .id(3L)
                        .driverId(5L)
                        .driverIsikukood(39503150234L)
                        .driverFirstName("Vladimir")
                        .driverLastName("Vladimirovich")
                        .driverEmail("test@gmail.com")
                        .created(LocalDate.of(2024, Month.JANUARY, 13))
                        .build();
        // when
        final var domain = instanceUnderTest.mapToDomain(entity);

        // then
        assertNotNull(domain);
        assertEquals(3L, domain.getId());
        assertEquals(5L, domain.getDriverId());
        assertEquals(39503150234L, domain.getDriverIsikukood());
        assertEquals("Vladimir", domain.getDriverFirstName());
        assertEquals("Vladimirovich", domain.getDriverLastName());
        assertEquals("test@gmail.com", domain.getDriverEmail());
        assertEquals(LocalDate.of(2024, Month.JANUARY, 13), domain.getCreated());
    }

    @Test
    public void testShouldMapToEntitySuccessfully() {

        // given
        Authorization domain =
                Authorization.builder()
                        .id(3L)
                        .driverId(5L)
                        .driverIsikukood(39503150234L)
                        .driverFirstName("Vladimir")
                        .driverLastName("Vladimirovich")
                        .driverEmail("test@gmail.com")
                        .created(LocalDate.of(2024, Month.JANUARY, 13))
                        .build();

        // when
        final var entity = instanceUnderTest.mapToEntity(domain);

        // then
        assertNotNull(entity);
        assertEquals(3L, entity.getId());
        assertEquals(5L, entity.getDriverId());
        assertEquals(39503150234L, entity.getDriverIsikukood());
        assertEquals("Vladimir", entity.getDriverFirstName());
        assertEquals("Vladimirovich", entity.getDriverLastName());
        assertEquals("test@gmail.com", entity.getDriverEmail());
        assertEquals(LocalDate.of(2024, Month.JANUARY, 13), entity.getCreated());
    }
}