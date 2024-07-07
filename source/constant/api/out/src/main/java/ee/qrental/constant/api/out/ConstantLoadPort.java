package ee.qrental.constant.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.constant.domain.Constant;

public interface ConstantLoadPort extends LoadPort<Constant> {

    Constant loadByName(final String name);

}
