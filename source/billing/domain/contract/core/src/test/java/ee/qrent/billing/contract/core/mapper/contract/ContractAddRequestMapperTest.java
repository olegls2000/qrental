package ee.qrent.billing.contract.core.mapper.contract;

import ee.qrent.billing.contract.api.in.request.ContractAddRequest;
import ee.qrent.billing.contract.core.mapper.ContractAddRequestMapper;
import ee.qrent.billing.contract.domain.ContractDuration;
import ee.qrent.billing.driver.api.in.query.GetDriverQuery;
import ee.qrent.billing.driver.api.in.response.DriverResponse;
import ee.qrent.billing.firm.api.in.query.GetFirmQuery;
import ee.qrent.billing.firm.api.in.response.FirmResponse;
import ee.qrent.common.in.time.QDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ContractAddRequestMapperTest {

  private GetDriverQuery driverQuery;
  private GetFirmQuery firmQuery;
  private QDateTime qDateTime;
  private ContractAddRequestMapper instanceUnderTest;

  @BeforeEach
  void init() {
    driverQuery = mock(GetDriverQuery.class);
    firmQuery = mock(GetFirmQuery.class);
    qDateTime = mock(QDateTime.class);
    instanceUnderTest = new ContractAddRequestMapper(driverQuery, firmQuery, qDateTime);
  }

  @Test
  void testIfPersonIsValid() {
    // given
    final var request = new ContractAddRequest();
    request.setDateStart(LocalDate.of(2025, 3, 20));
    request.setDriverId(5L);
    request.setQFirmId(4L);
    request.setContractDuration(ContractDuration.FOUR_WEEKS.getLabel());
    when(driverQuery.getById(5L))
        .thenReturn(
            DriverResponse.builder()
                .legalEntityType("PERSON")
                .firstName("Vova")
                .lastName("EOS")
                .taxNumber(123L)
                .address("Addr")
                .build());
    when(qDateTime.getNow()).thenReturn(LocalDateTime.now());
    when(firmQuery.getById(4L))
        .thenReturn(
            FirmResponse.builder()
                .name("Firm")
                .registrationNumber("341234")
                .postAddress("90000")
                .email("firm@gmail.com")
                .ceoName("Vova")
                .deputies(List.of("Deputy1", "Deputy2"))
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.ofYearDay(2024, 1));

    // when
    final var contract = instanceUnderTest.toDomain(request);

    // then
    assertEquals(contract.getRenter(), "Vova EOS");
    assertEquals(contract.getRenterRegistrationNumber(), "123");
    assertEquals(contract.getRenterSignerName(), "Vova EOS");
    assertEquals(contract.getRenterSignerTaxNumber(), 123L);
    assertEquals(contract.getRenterAddress(), "Addr");
    assertEquals(contract.getContractDuration().getLabel(), ContractDuration.FOUR_WEEKS.getLabel());
  }

  @Test
  void testIfLHVAccountIsValid() {
    // given
    final var request = new ContractAddRequest();
    request.setDateStart(LocalDate.of(2025, 3, 20));
    request.setDriverId(5L);
    request.setQFirmId(4L);
    request.setContractDuration(ContractDuration.FOUR_WEEKS.getLabel());
    when(driverQuery.getById(5L))
        .thenReturn(
            DriverResponse.builder()
                .legalEntityType("LHV_ACCOUNT")
                .firstName("Vova")
                .lastName("EOS")
                .taxNumber(123L)
                .address("Addr")
                .build());
    when(qDateTime.getNow()).thenReturn(LocalDateTime.now());
    when(firmQuery.getById(4L))
        .thenReturn(
            FirmResponse.builder()
                .name("Firm")
                .registrationNumber("341234")
                .postAddress("90000")
                .email("firm@gmail.com")
                .ceoName("Vova")
                .deputies(List.of("Deputy1", "Deputy2"))
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.ofYearDay(2024, 1));

    // when
    final var contract = instanceUnderTest.toDomain(request);

    // then
    assertEquals(contract.getRenter(), "Vova EOS");
    assertEquals(contract.getRenterRegistrationNumber(), "123");
    assertEquals(contract.getRenterSignerName(), "Vova EOS");
    assertEquals(contract.getRenterSignerTaxNumber(), 123L);
    assertEquals(contract.getRenterAddress(), "Addr");
    assertEquals(contract.getContractDuration().getLabel(), ContractDuration.FOUR_WEEKS.getLabel());
  }

  @Test
  void testIfSelfEmployedIsValid() {
    // given
    final var request = new ContractAddRequest();
    request.setDateStart(LocalDate.of(2025, 3, 20));
    request.setDriverId(5L);
    request.setQFirmId(4L);
    request.setContractDuration(ContractDuration.TWELVE_WEEKS.getLabel());
    when(driverQuery.getById(5L))
        .thenReturn(
            DriverResponse.builder()
                .legalEntityType("SELF_EMPLOYED")
                .firstName("Vova")
                .lastName("EOS")
                .taxNumber(123L)
                .address("Addr")
                .companyRegistrationNumber("1234")
                .build());
    when(qDateTime.getNow()).thenReturn(LocalDateTime.now());
    when(firmQuery.getById(4L))
        .thenReturn(
            FirmResponse.builder()
                .name("Firm")
                .registrationNumber("341234")
                .postAddress("90000")
                .email("firm@gmail.com")
                .ceoName("Vova")
                .deputies(List.of("Deputy1", "Deputy2"))
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.ofYearDay(2024, 1));

    // when
    final var contract = instanceUnderTest.toDomain(request);

    // then
    assertEquals(contract.getRenter(), "Vova EOS FIE");
    assertEquals(contract.getRenterRegistrationNumber(), "1234");
    assertEquals(contract.getRenterSignerName(), "Vova EOS");
    assertEquals(contract.getRenterSignerTaxNumber(), 123L);
    assertEquals(contract.getRenterAddress(), "Addr");
    assertEquals(
        contract.getContractDuration().getLabel(), ContractDuration.TWELVE_WEEKS.getLabel());
  }

  @Test
  void testIfCompanyIsValid() {
    // given
    final var request = new ContractAddRequest();
    request.setDateStart(LocalDate.of(2025, 3, 20));
    request.setDriverId(5L);
    request.setQFirmId(4L);
    request.setContractDuration(ContractDuration.TWELVE_WEEKS.getLabel());
    when(driverQuery.getById(5L))
        .thenReturn(
            DriverResponse.builder()
                .legalEntityType("COMPANY")
                .taxNumber(123L)
                .companyRegistrationNumber("1234")
                .companyName("Company")
                .companyCeoName("Company CEO")
                .companyCeoTaxNumber(34L)
                .companyAddress("Test address")
                .build());
    when(qDateTime.getNow()).thenReturn(LocalDateTime.now());
    when(firmQuery.getById(4L))
        .thenReturn(
            FirmResponse.builder()
                .name("Firm")
                .registrationNumber("341234")
                .postAddress("90000")
                .email("firm@gmail.com")
                .ceoName("Vova")
                .deputies(List.of("Deputy1", "Deputy2"))
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.ofYearDay(2024, 1));

    // when
    final var contract = instanceUnderTest.toDomain(request);

    // then
    assertEquals(contract.getRenter(), "Company");
    assertEquals(contract.getRenterRegistrationNumber(), "1234");
    assertEquals(contract.getRenterSignerName(), "Company CEO");
    assertEquals(contract.getRenterSignerTaxNumber(), 34L);
    assertEquals(contract.getRenterAddress(), "Test address");
    assertEquals(
        contract.getContractDuration().getLabel(), ContractDuration.TWELVE_WEEKS.getLabel());
    assertEquals(contract.getContractDuration().getWeeksCount(), 12);
  }

  @Test
  void testIfContractIsContractDurationInvalid() {
    // given
    final var request = new ContractAddRequest();
    request.setDateStart(LocalDate.of(2025, 3, 20));
    request.setDriverId(5L);
    request.setQFirmId(4L);
    request.setContractDuration("test");
    when(driverQuery.getById(5L))
        .thenReturn(
            DriverResponse.builder()
                .legalEntityType("PERSON")
                .firstName("Vova")
                .lastName("EOS")
                .taxNumber(123L)
                .address("Addr")
                .build());
    when(qDateTime.getNow()).thenReturn(LocalDateTime.now());
    when(firmQuery.getById(4L))
        .thenReturn(
            FirmResponse.builder()
                .name("Firm")
                .registrationNumber("341234")
                .postAddress("90000")
                .email("firm@gmail.com")
                .ceoName("Vova")
                .deputies(List.of("Deputy1", "Deputy2"))
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.ofYearDay(2024, 1));

    // when
    Exception exception = null;
    try {
      instanceUnderTest.toDomain(request);
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertEquals(RuntimeException.class, exception.getClass());
    assertEquals("No contract duration found for label 'test'", exception.getMessage());
  }

  @Test
  void testIfRenterIsInvalid() {
    // given
    final var request = new ContractAddRequest();
    request.setDateStart(LocalDate.of(2025, 3, 20));
    request.setDriverId(5L);
    request.setQFirmId(4L);
    request.setContractDuration(ContractDuration.FOUR_WEEKS.getLabel());
    when(driverQuery.getById(5L))
        .thenReturn(
            DriverResponse.builder()
                .legalEntityType("test")
                .firstName("Vova")
                .lastName("EOS")
                .taxNumber(123L)
                .address("Addr")
                .build());
    when(qDateTime.getNow()).thenReturn(LocalDateTime.now());
    when(firmQuery.getById(4L))
        .thenReturn(
            FirmResponse.builder()
                .name("Firm")
                .registrationNumber("341234")
                .postAddress("90000")
                .email("firm@gmail.com")
                .ceoName("Vova")
                .deputies(List.of("Deputy1", "Deputy2"))
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.ofYearDay(2024, 1));

    // when
    Exception exception = null;
    try {
      instanceUnderTest.toDomain(request);
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertEquals(RuntimeException.class, exception.getClass());
    assertEquals("Unknown legal entity type: test", exception.getMessage());
  }

  @Test
  void testIfRenterSignerNameIsInvalid() {
    // given
    final var request = new ContractAddRequest();
    request.setDateStart(LocalDate.of(2025, 3, 20));
    request.setDriverId(5L);
    request.setQFirmId(4L);
    request.setContractDuration(ContractDuration.FOUR_WEEKS.getLabel());
    when(driverQuery.getById(5L))
        .thenReturn(
            DriverResponse.builder()
                .legalEntityType("test")
                .firstName("Vova")
                .lastName("EOS")
                .taxNumber(123L)
                .address("Addr")
                .build());
    when(qDateTime.getNow()).thenReturn(LocalDateTime.now());
    when(firmQuery.getById(4L))
        .thenReturn(
            FirmResponse.builder()
                .name("Firm")
                .registrationNumber("341234")
                .postAddress("90000")
                .email("firm@gmail.com")
                .ceoName("Vova")
                .deputies(List.of("Deputy1", "Deputy2"))
                .build());
    when(qDateTime.getToday()).thenReturn(LocalDate.ofYearDay(2024, 1));

    // when
    Exception exception = null;
    try {
      instanceUnderTest.toDomain(request);
    } catch (Exception e) {
      exception = e;
    }

    // then
    assertEquals(RuntimeException.class, exception.getClass());
    assertEquals("Unknown legal entity type: test", exception.getMessage());
  }
}
