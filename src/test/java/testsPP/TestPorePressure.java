package testsPP;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.pp.PorePressure;

import static ru.geonaft.modules.pp.workFlowPP.WorkflowPP.calculateMethodsDExponent.*;

public class TestPorePressure extends BaseTest {

    @Test
    @DisplayName("Checking of calculate overburden pressure")
    @Epic(value = "Pore pressure")
    @Feature(value = "OverburdenPressure")
    @Story(value = " Extrapolation")
    @TmsLink("8200")
    public void TestOverburdenPressureShouldCalculate() {
        new PorePressure(desktopSession)
                .openModule()
                .chooseWellAndLogClickApply()
                .overburdenPressureCalculation();
    }

    @Test
    @DisplayName("Check of calculate overburden pressure")
    @Epic(value = "Pore pressure")
    @Feature(value = "DExponent")
    @Story(value = " Calculate")
    @TmsLink("13118")
    public void TestDExponentShouldCalculated() {
        new PorePressure(desktopSession)
                .openModule()
                .chooseWellLogAndZoneClickApply()
                .clickDExponent()
                .dExponentCalculationCalculatedMethod(calculate);

    }

    @Test
    @DisplayName("Check the range of acceptable values of constants")
    @Epic(value = "Pore pressure")
    @Feature(value = "DExponent")
    @Story(value = " Constants")
    @TmsLink("13120")
    public void TestCellConstantsDExponentShouldMustAcceptValidValues() {
        new PorePressure(desktopSession)
                .openModule()
                .chooseWellAndLogClickApply()
                .dExponentCheckRangeConstants();
    }
}
