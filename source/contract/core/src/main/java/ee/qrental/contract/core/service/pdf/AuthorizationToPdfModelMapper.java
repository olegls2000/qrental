package ee.qrental.contract.core.service.pdf;

import ee.qrental.contract.domain.Authorization;

public class AuthorizationToPdfModelMapper {

  public AuthorizationPdfModel getPdfModel(final Authorization authorization) {

    return AuthorizationPdfModel.builder()
        .driverIsikukood(authorization.getDriverIsikukood())
        .driverFirstname(authorization.getDriverFirstName())
        .driverLastname(authorization.getDriverLastName())
        .created(authorization.getCreated())
        .build();
  }
}
