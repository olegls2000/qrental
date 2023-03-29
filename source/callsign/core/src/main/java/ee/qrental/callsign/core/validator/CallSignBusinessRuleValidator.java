package ee.qrental.callsign.core.validator;

import static java.lang.String.format;

import ee.qrental.callsign.api.out.CallSignLoadPort;
import ee.qrental.callsign.domain.CallSign;
import ee.qrental.common.core.in.validation.QValidator;
import ee.qrental.common.core.in.validation.ViolationsCollector;
import java.util.Objects;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CallSignBusinessRuleValidator implements QValidator<CallSign> {

    private final CallSignLoadPort callSignLoadPort;

    @Override
    public ViolationsCollector validate(final CallSign domain) {
        final var violationsCollector = new ViolationsCollector();
        checkUniqueness(domain, violationsCollector);

        return violationsCollector;
    }

    private void checkUniqueness(final CallSign domain, final ViolationsCollector violationCollector) {
        final var callSign = domain.getCallSign();
        final var domainFromDb = callSignLoadPort.loadByCallSign(callSign);
        if (domainFromDb == null) {
            return;
        }
        if (Objects.equals(domainFromDb.getId(),domain.getId())) {
            return;
        }
        violationCollector.collect(format("Call Sign %d already exists", callSign));
    }
}
