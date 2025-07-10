package ee.qrent.billing.e2e;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import ee.qrent.billing.app.BillingApplication;
import ee.qrent.billing.car.api.in.query.GetCarQuery;
import ee.qrent.billing.car.api.in.request.CarAddRequest;
import ee.qrent.billing.car.api.in.usecase.CarAddUseCase;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.time.LocalDate;
import java.time.Month;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = BillingApplication.class)
class IntegrationTest {

  @LocalServerPort private Integer port;

  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @BeforeAll
  static void beforeAll() {
    postgres.start();
  }

  @AfterAll
  static void afterAll() {
    postgres.stop();
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired CarAddUseCase carAddUseCase;
  @Autowired GetCarQuery carQuery;

  @BeforeEach
  void setUp() {
    // RestAssured.baseURI = "http://localhost:" + port;

  }

  @Test
  void shouldGetAllCustomers() {

    final var carAddRequest = new CarAddRequest();
    carAddRequest.setActive(true);
    carAddRequest.setStatus("IN_USE");
    carAddRequest.setQRent(true);
    carAddRequest.setRegNumber("123ABC");
    carAddRequest.setVin("1HGBH41JXMN109186");
    carAddRequest.setReleaseDate(LocalDate.of(2020, Month.JANUARY, 1));
    carAddRequest.setManufacturer("Toyota");
    carAddRequest.setModel("Corolla");
    carAddRequest.setAppropriation(true);
    carAddRequest.setElegance(false);
    carAddRequest.setGearType("automatic");
    carAddRequest.setFuelType("petrol");
    carAddRequest.setLpg(false);
    carAddRequest.setDateInstallLpg(LocalDate.of(2020, Month.JANUARY, 30));
    carAddRequest.setInsuranceFirm("Insurance Co.");
    carAddRequest.setInsuranceDateStart(LocalDate.of(2020, Month.FEBRUARY, 25));
    carAddRequest.setInsuranceDateEnd(LocalDate.of(2021, Month.FEBRUARY, 25));
      carAddRequest.setSCard(true);
    carAddRequest.setKey2(true);
    carAddRequest.setGps(true);
    carAddRequest.setTechnicalInspectionEnd(LocalDate.of(2021, Month.MARCH, 30));
    carAddRequest.setGasInspectionEnd(LocalDate.of(2021, Month.APRIL, 30));
    carAddRequest.setComment("Test comment");
    carAddRequest.setDateEndLpg(LocalDate.of(2021, Month.MAY, 30));
    carAddRequest.setBrandingQrent(true);
    carAddRequest.setBrandingBolt(false);
    carAddRequest.setBrandingForus(false);
    carAddRequest.setBrandingUber(false);
    carAddRequest.setBrandingTallink(false);

    final var savedId = carAddUseCase.add(carAddRequest);
    final var savedCar = carQuery.getById(savedId);

    assertNotNull(savedCar);
  }
}
