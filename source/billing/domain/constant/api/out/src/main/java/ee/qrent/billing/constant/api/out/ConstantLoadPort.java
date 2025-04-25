package ee.qrent.billing.constant.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.constant.domain.Constant;

public interface ConstantLoadPort extends LoadPort<Constant> {

    Constant loadByName(final String name);

}
