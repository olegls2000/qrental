package ee.qrental.contract.core.service.pdf;

import ee.qrental.contract.domain.AuthorizationBolt;

public class AuthorizationBoltToPdfModelMapper {

  public AuthorizationBoltPdfModel getPdfModel(final AuthorizationBolt authorization) {

    return AuthorizationBoltPdfModel.builder()
        .driverIsikukood(authorization.getDriverIsikukood())
        .driverFirstname(authorization.getDriverFirstName())
        .driverLastname(authorization.getDriverLastName())
        .build();
  }
}
