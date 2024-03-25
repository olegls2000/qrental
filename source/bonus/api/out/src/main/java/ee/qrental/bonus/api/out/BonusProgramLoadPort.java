package ee.qrental.bonus.api.out;

import ee.qrental.bonus.domain.BonusProgram;
import ee.qrental.bonus.domain.ObligationCalculation;
import ee.qrental.common.core.out.port.LoadPort;

import java.util.List;

public interface BonusProgramLoadPort extends LoadPort<BonusProgram> {

    List<BonusProgram> loadActive();
}
