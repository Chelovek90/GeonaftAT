package testsPP;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.pp.PorePressure;

public class TestPorePressure extends BaseTest {

    @Test
    @DisplayName("Display contact on cross section window")
    @Epic(value = "Pore pressure")
    @Feature(value = "OverburdenPressure")
    @Story(value = " Extrapolation")
    @TmsLink("8200")
    public void TestOverburdenPressure() {
        new PorePressure(desktopSession)
                .openModule()
                .chooseWellAndLogClickApply()
                .overburdenPressureCalculation();
    }
}
