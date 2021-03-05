package testsPP;

import org.junit.jupiter.api.Test;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.pp.PorePressure;

public class TestPorePressure extends BaseTest {

    @Test
    public void TestMethodsPP() {
        new PorePressure(desktopSession)
                .openModule()
                .chooseWellAndLogClickApply();
    }
}
