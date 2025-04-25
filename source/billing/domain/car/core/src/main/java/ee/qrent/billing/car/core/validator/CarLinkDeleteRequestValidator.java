package ee.qrent.billing.car.core.validator;

import ee.qrent.common.in.validation.DeleteRequestValidator;
import ee.qrent.common.in.validation.ViolationsCollector;
import ee.qrent.billing.car.api.in.request.CarLinkDeleteRequest;
import ee.qrent.billing.car.api.out.CarLinkLoadPort;
import lombok.AllArgsConstructor;

import static java.lang.String.format;

@AllArgsConstructor
public class CarLinkDeleteRequestValidator implements DeleteRequestValidator<CarLinkDeleteRequest> {

    private final CarLinkLoadPort loadPort;

    public ViolationsCollector validate(final CarLinkDeleteRequest request) {
        final var violationsCollector = new ViolationsCollector();
        checkExistence(request, violationsCollector);
        // TODO add 'no balance/ calculation check' check

        return violationsCollector;
    }

    private void checkExistence(
            final CarLinkDeleteRequest request, final ViolationsCollector violationsCollector) {
        final var carLinkId = request.getId();
        if (loadPort.loadById(carLinkId) == null) {
            violationsCollector.collect(format("Car Link with id: %d was not found.", carLinkId));
        }
    }
}