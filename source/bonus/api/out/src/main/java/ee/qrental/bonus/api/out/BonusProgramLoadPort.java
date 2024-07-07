package ee.qrental.bonus.api.out;

import ee.qrent.common.out.port.LoadPort;
import ee.qrental.bonus.domain.BonusProgram;


import java.util.List;

public interface BonusProgramLoadPort extends LoadPort<BonusProgram> {

    List<BonusProgram> loadActive();
}
