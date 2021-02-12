import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import ru.geonaft.Base;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.CS.Geosteering;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;


public class testsGeosteering extends BaseTest {

    static String pathCleanProject = "D:\\Data for testing\\Проекты";
    static String nameCleanProject = "clearProjectForTests3.7.37.21";


    @BeforeAll
    public static void openProject() {
//        new StartWindow(desktopSession)
//                .openProject(pathCleanProject, nameCleanProject);
        new BaseWorkSpace(desktopSession)
                .setCountHeaders();
    }

    @Test
    @DisplayName("Choose actual and reference well on ribbon")
    @Feature(value = "Geosteering")
    @Story(value = "Choose well")
    @TmsLink("7015")
    public void TestChooseWells(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .makeModuleScreen(screenName, Base.Appointment.PRIMARY)
                .choseWellsOnRibbonPanel()
                .makeModuleScreen(screenName, Base.Appointment.SECONDARY)
                .checkDisplayedOnCrossSectionSpace(screenName);
    }

//    @Test
//    @DisplayName("Checking loading polygon")
//    @Feature(value = "Geosteering")
//    @Story(value = "Polygon")
//    @TmsLink("7015")
//    public void TestChooseTrajectory(TestInfo testInfo) {
//        new Geosteering(desktopSession)
//                .openModule()
//                .makeModuleScreen(testInfo.getDisplayName(), Base.Appointment.PRIMARY)
//                .choseWellOnRibbonPanel()
//                .activeCheckBoxActualWell()
//                .activeCheckBoxRefWell();
//    }

    @Test
    public void test() {
        new Geosteering(desktopSession)
                .check();
    }
}
