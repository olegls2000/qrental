package ee.qrental.constant.api.out;

import ee.qrental.common.core.out.port.LoadPort;
import ee.qrental.constant.domain.Constant;

public interface ConstantLoadPort extends LoadPort<Constant> {

    Constant loadByName(final String name);

}
