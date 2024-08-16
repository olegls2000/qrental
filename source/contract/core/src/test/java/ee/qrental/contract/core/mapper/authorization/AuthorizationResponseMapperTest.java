package ee.qrental.contract.core.mapper.authorization;

import ee.qrental.contract.core.mapper.AuthorizationResponseMapper;
import ee.qrental.contract.domain.Authorization;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class AuthorizationResponseMapperTest {
    private final AuthorizationResponseMapper instance = new AuthorizationResponseMapper();

    @Test
    public void testIfResponseIsNull() {

        // given
        Authorization domain = null;

        // when
        final var result = instance.toResponse(domain);

        // then
        assertNull(result);
    }

    @Test
    public void testShouldResponseSuccessfully() {

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
        final var response = instance.toResponse(domain);

        // then
        assertNotNull(response);
        assertEquals("Authorization Bolt for Driver: 39503150234, Vladimir Vladimirovich", instance.toObjectInfo(domain));
        assertEquals(3L, response.getId());
        assertEquals(5L, response.getDriverId());
        assertEquals(39503150234L, response.getDriverIsikukood());
        assertEquals("Vladimir", response.getDriverFirstName());
        assertEquals("Vladimirovich", response.getDriverLastName());
        assertEquals("test@gmail.com", response.getDriverEmail());
        assertEquals(LocalDate.of(2024, Month.JANUARY, 13), response.getCreated());
    }
}