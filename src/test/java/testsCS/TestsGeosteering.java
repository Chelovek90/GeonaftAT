package testsCS;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import ru.geonaft.Base;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.CS.Geosteering;

import static ru.geonaft.Base.Appointment.*;
import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.modules.CS.ribbon.InstrumentsCsSelector.DipsProcessing;
import static ru.geonaft.modules.CS.workSpace.WorkSpaceCs.OrientationTrack.*;


public class TestsGeosteering extends BaseTest {

    static String pathCleanProject = "D:\\Data for testing\\Проекты";
    static String nameCleanProject = "ProjectWithWellsForTests3.8.0.24";


//    @BeforeAll
//    public static void openProject() {
//        new StartWindow(desktopSession)
//                .openProject(pathCleanProject, nameCleanProject);
//    }

//    @AfterEach
//    public void closeAllTabs() {
//        new BaseWorkSpace(desktopSession)
//                .closeAllTab();
//    }

//        @AfterAll
//    public static void closeProject() {
//        new BaseRibbon(desktopSession)
//                .closeProject();
//    }

    @Test
    @DisplayName("Choose actual and reference well on ribbon")
    @Feature(value = "Geosteering")
    @Story(value = "Choose wells in ribbon")
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
    @DisplayName("Display trajectory actual well on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "Trajectory")
    @TmsLink("7535")
    public void TestDisplayedTrajectoryActualWellInCrossSectionWindow(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModuleChooseWellsAndDisplayTrajectory(actualWellInProject)
                .makeWorkSpaceScreen(screenName, PRIMARY)
                .displayPlanTrajectoryInCrossSectionWindow()
                .openEditorTrajectory()
                .changeInclAndAzimTrajectory()
                .makeWorkSpaceScreen(screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Display trajectory reference well on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "Trajectory")
    @TmsLink("7535")
    public void TestDisplayedTrajectoryRefWellInCrossSectionWindow(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModuleChooseWellsAndDisplayTrajectory(refWellInProject)
                .makeWorkSpaceScreen(screenName, PRIMARY)
                .displayPlanTrajectoryInCrossSectionWindow()
                .openEditorTrajectory()
                .changeInclAndAzimTrajectory()
                .makeWorkSpaceScreen(screenName, SECONDARY)
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
                .makeTrackBlockScreen(VERTICAL, screenName, PRIMARY)
                .addTrack(VERTICAL)
                .makeTrackBlockScreen(VERTICAL, screenName, SECONDARY)
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
                .makeTrackBlockScreen(VERTICAL, screenName, PRIMARY)
                .addTrack(HORIZONTAL)
                .makeTrackBlockScreen(VERTICAL, screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Display Log on vertical track")
    @Feature(value = "Geosteering")
    @Story(value = "Vertical track")
    @TmsLink("7539")
    public void TestDisplayLogOnVerticalTrack(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .clickOnTrack(VERTICAL)
                .makeTrackBlockScreen(VERTICAL, screenName, PRIMARY)
                .activeCheckBoxLogWellInTreeProject(refWellInProject.name)
                .makeTrackBlockScreen(VERTICAL, screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Display Log on vertical track")
    @Feature(value = "Geosteering")
    @Story(value = "Horizontal track")
    @TmsLink("7540")
    public void TestDisplayLogOnHorizontalTrack(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .clickOnTrack(HORIZONTAL)
                .makeTrackBlockScreen(HORIZONTAL, screenName, PRIMARY)
                .activeCheckBoxLogWellInTreeProject(actualWellInProject.name)
                .makeTrackBlockScreen(HORIZONTAL, screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Display target on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "Target")
    @TmsLink("7589")
    public void TestDisplayedTargetInCrossSectionWindow(TestInfo testInfo) {
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
    @DisplayName("Display geonavigation journal on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "Geonavigation journal")
    @TmsLink("7604")
    public void TestDisplayedGeonavigationJournalInCrossSectionWindow(TestInfo testInfo) {
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
    @DisplayName("Display markers reference well on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "Markers")
    @TmsLink("7568")
    public void TestDisplayedMarkersRefWellInCrossSectionWindow(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .displayTrajectoryInCrossSectionWindow(refWellInProject.name)
                .makeWorkSpaceScreen(screenName, PRIMARY)
                .clickCheckBoxStratigraphyWellTreeProject()
                .makeWorkSpaceScreen(screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Display dipSeries on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "DipSeries")
    @TmsLink("7839")
    public void TestDisplayedDipSeriesInCrossSectionWindow(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .displayTrajectoryInCrossSectionWindow(actualWellInProject.name)
                .makeWorkSpaceScreen(screenName, PRIMARY)
                .displayDipSeriesInCrossSectionWindow()
                .makeWorkSpaceScreen(screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Add dip on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "Dip")
    @TmsLink("8920")
    public void TestAddDipInCrossSectionWindow(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Geosteering(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
//                .displayTrajectoryInCrossSectionWindow(actualWellInProject.name)
                .clickCsTab()
                .clickInstrumentOnRibbon(DipsProcessing)
                .openGeomodelTableSetCountDips()
                .clickCsTab()
                .addDipRibbon()
//                .makeWorkSpaceScreen(screenName, PRIMARY)
//                .checkDisplayedInCrossSectionWindow(screenName)
                .checkAddDip()
//                .addNewDipOnCrossSection();
//                .displayDipSeriesInCrossSectionWindow()
//                .makeWorkSpaceScreen(screenName, SECONDARY)
        ;
    }

    @Test
    public void test() {
        new Geosteering(desktopSession)
                .openModule()
                .test();
    }
}