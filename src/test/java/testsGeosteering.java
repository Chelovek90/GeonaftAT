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

import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.modules.CS.workSpace.CsWorkSpace.OrientationTrack.*;


public class testsGeosteering extends BaseTest {

    static String pathCleanProject = "D:\\Data for testing\\Проекты";
    static String nameCleanProject = "clearProjectForTests3.7.37.21";


    @BeforeAll
    public static void openProject() {
//        new StartWindow(desktopSession)
//                .openProject(pathCleanProject, nameCleanProject);
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

    @Test
    @DisplayName("Add vertical track")
    @Feature(value = "Track")
    @Story(value = "Vertical track")
    @TmsLink("7537")
    public void TestAddVerticalTrack(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .makeModuleScreen(screenName, Base.Appointment.PRIMARY)
                .addTrack(VERTICAL)
                .makeModuleScreen(screenName, Base.Appointment.SECONDARY)
                .checkDisplayedOnCrossSectionSpace(screenName);
    }

    @Test
    @DisplayName("Add horizontal track")
    @Feature(value = "Track")
    @Story(value = "Horizontal track")
    @TmsLink("7538")
    public void TestAddHorizontalTrack(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .makeModuleScreen(screenName, Base.Appointment.PRIMARY)
                .addTrack(HORIZONTAL)
                .makeModuleScreen(screenName, Base.Appointment.SECONDARY)
                .checkDisplayedOnCrossSectionSpace(screenName);
    }

    @Test
    @DisplayName("Add target on cross section window")
    @Feature(value = "Track")
    @Story(value = "Horizontal track")
    @TmsLink("7589")
    public void TestDisplayedTargetInCrossSectionWindow (TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .makeModuleScreen(screenName, Base.Appointment.PRIMARY)
                .choseWellsOnRibbonPanel()
                .doCopyTrajectoryWellAsPlan(actualWellInProject)
                .activeCheckBoxPlanTrajectoryWell(actualWellInProject)
                .showAll()
                .makeModuleScreen(screenName, Base.Appointment.SECONDARY)
                .checkDisplayedOnCrossSectionSpace(screenName);
//                .makeModuleScreen(screenName, Base.Appointment.SECONDARY)
//                .checkDisplayedOnCrossSectionSpace(screenName);
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
                .openModule()
                .check();
    }
}
