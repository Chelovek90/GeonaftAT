import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.openqa.selenium.interactions.Actions;
import ru.geonaft.Base;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.CS.Geosteering;

import static ru.geonaft.Base.Appointment.*;
import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.modules.CS.workSpace.CsWorkSpace.OrientationTrack.*;


public class testsGeosteering extends BaseTest {

    static String pathCleanProject = "D:\\Data for testing\\Проекты";
    static String nameCleanProject = "ProjectWithWellsForTests3.8.0.24";


//    @BeforeAll
//    public static void openProject() {
//        new StartWindow(desktopSession)
//                .openProject(pathCleanProject, nameCleanProject);
//    }

    @Test
    @DisplayName("Choose actual and reference well on ribbon")
    @Feature(value = "Geosteering")
    @Story(value = "Choose well in ribbon")
    @TmsLink("7015")
    public void TestChooseWells(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .makeGeonaftScreen(screenName, PRIMARY)
                .choseWellsOnRibbonPanel()
                .makeGeonaftScreen(screenName, Base.Appointment.SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Add vertical track")
    @Feature(value = "Geosteering")
    @Story(value = "Vertical track")
    @TmsLink("7537")
    public void TestAddVerticalTrack(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .makeGeonaftScreen(screenName, PRIMARY)
                .addTrack(VERTICAL)
                .makeGeonaftScreen(screenName, Base.Appointment.SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Add horizontal track")
    @Feature(value = "Geosteering")
    @Story(value = "Horizontal track")
    @TmsLink("7538")
    public void TestAddHorizontalTrack(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .makeGeonaftScreen(screenName, PRIMARY)
                .addTrack(HORIZONTAL)
                .makeGeonaftScreen(screenName, Base.Appointment.SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Add target on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "CS window")
    @TmsLink("7589")
    public void TestDisplayedTargetInCrossSectionWindow (TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .doCopyTrajectoryWellAsPlan(actualWellInProject)
                .displayPlanTrajectoryInCrossSectionWindow()
                .makeWorkSpaceScreen(screenName, PRIMARY)
                .unfoldPlanTrajectoryAndCreatedTarget()
                .displayTargetsInCrossSectionWindow()
                .makeWorkSpaceScreen(screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Add geonavigation journal on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "CS window")
    @TmsLink("7604")
    public void TestDisplayedGeonavigationJournalInCrossSectionWindow (TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .displayTrajectoryInCrossSectionWindow(actualWellInProject.name)
                .makeWorkSpaceScreen(screenName, PRIMARY)
                .openEditorGeonavigationJournal(actualWellInProject.name)
                .fillFieldsGeonavigationJournalSaveAdnExit()
                .displayGeonavigationJournalInCrossSectionWindow()
                .makeWorkSpaceScreen(screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    public void test () {
        Actions actions = new Actions(desktopSession);
        actions.moveByOffset(100, 100);
    }
}
