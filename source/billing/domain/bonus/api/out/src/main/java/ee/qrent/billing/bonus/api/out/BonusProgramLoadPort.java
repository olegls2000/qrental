package ee.qrent.billing.bonus.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrent.billing.bonus.domain.BonusProgram;


import java.util.List;

public interface BonusProgramLoadPort extends LoadPort<BonusProgram> {

    List<BonusProgram> loadActive();
}
