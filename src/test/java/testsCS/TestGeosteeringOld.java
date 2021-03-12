package testsCS;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import ru.geonaft.Base;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.cs.GeosteeringOld;

import static ru.geonaft.Base.Appointment.*;
import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.modules.cs.ribbonCS.InstrumentsCsSelector.DipsProcessing;
import static ru.geonaft.modules.cs.workSpaceCS.WorkSpaceAndEditorCS.OrientationTrack.*;


public class TestGeosteeringOld extends BaseTest {

    static String pathCleanProject = "D:\\Data for testing\\Проекты";
    static String nameCleanProject = "ProjectWithWellsForTests3.8.0.24";


//    @BeforeAll
//    public static void openProject() {
//        new StartWindow(desktopSession)
//                .openProject(pathCleanProject, nameCleanProject);
//    }
//
//    @AfterEach
//    public void closeAllTabs() {
//        new BaseWorkSpace(desktopSession)
//                .closeAllTab();
//    }
//
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
        new GeosteeringOld(desktopSession)
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
        new GeosteeringOld(desktopSession)
                .openModuleChooseWellsAndDisplayTrajectory(actualWellInProject)
                .showAllAndMakeWorkSpaceScreen(screenName, PRIMARY)
                .displayPlanTrajectoryInCrossSectionWindow()
                .openEditorTrajectory()
                .changeInclAndAzimTrajectory()
                .showAll()
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
        new GeosteeringOld(desktopSession)
                .openModuleChooseWellsAndDisplayTrajectory(refWellInProject)
                .showAllAndMakeWorkSpaceScreen(screenName, PRIMARY)
                .displayPlanTrajectoryInCrossSectionWindow()
                .openEditorTrajectory()
                .changeInclAndAzimTrajectory()
                .showAll()
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
        new GeosteeringOld(desktopSession)
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
        new GeosteeringOld(desktopSession)
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
        new GeosteeringOld(desktopSession)
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
        new GeosteeringOld(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .clickOnTrack(HORIZONTAL)
                .makeTrackBlockScreen(HORIZONTAL, screenName, PRIMARY)
                .activeCheckBoxLogWellInTreeProject(actualWellInProject.name)
                .makeTrackBlockScreen(HORIZONTAL, screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Display markers reference well on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "Markers")
    @TmsLink("7568")
    public void TestDisplayedMarkersRefWellInCrossSectionWindow(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new GeosteeringOld(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .displayTrajectoryInCrossSectionWindow(refWellInProject.name)
                .showAllAndMakeWorkSpaceScreen(screenName, PRIMARY)
                .clickCheckBoxStratigraphyWellTreeProject()
                .makeWorkSpaceScreen(screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Display contact on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "Contact")
    @TmsLink("7578")
    public void TestDisplayedContactInCrossSectionWindow(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new GeosteeringOld(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .displayTrajectoryInCrossSectionWindow(refWellInProject.name)
                .showAllAndMakeWorkSpaceScreen(screenName, PRIMARY)
                .createContactAndDisplayOnCrossSection()
                .makeWorkSpaceScreen(screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Display target on cross section window")
    @Feature(value = "Geosteering")
    @Story(value = "Target")
    @TmsLink("7589")
    public void TestDisplayedTargetInCrossSectionWindow(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new GeosteeringOld(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .doCopyTrajectoryWellAsPlan(actualWellInProject)
                .displayPlanTrajectoryInCrossSectionWindow()
                .showAllAndMakeWorkSpaceScreen(screenName, PRIMARY)
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
        new GeosteeringOld(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .displayTrajectoryInCrossSectionWindow(actualWellInProject.name)
                .showAllAndMakeWorkSpaceScreen(screenName, PRIMARY)
                .openEditorGeonavigationJournal(actualWellInProject.name)
                .fillFieldsGeonavigationJournalSaveAdnExit()
                .displayGeonavigationJournalInCrossSectionWindow()
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
        new GeosteeringOld(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .displayTrajectoryInCrossSectionWindow(actualWellInProject.name)
                .showAllAndMakeWorkSpaceScreen(screenName, PRIMARY)
                .displayDipSeriesInCrossSectionWindow()
                .makeWorkSpaceScreen(screenName, SECONDARY)
                .checkDisplayedInCrossSectionWindow(screenName);
    }

    @Test
    @DisplayName("Set homeView")
    @Feature(value = "Geosteering")
    @Story(value = "Home view - Ribbon")
    @TmsLink("7891")
    public void TestSetHomeViewRibbon(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new GeosteeringOld(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel()
                .displayTrajectoryInCrossSectionWindow(actualWellInProject.name)
                .showAllAndMakeWorkSpaceScreen(screenName, PRIMARY)
                .setHomeViewRibbon()
                .scalingCrossSection()
                .goHomeViewRibbon()
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
        new GeosteeringOld(desktopSession)
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
                .checkAddDipInEditor()
//                .addNewDipOnCrossSection();
//                .displayDipSeriesInCrossSectionWindow()
//                .makeWorkSpaceScreen(screenName, SECONDARY)
        ;
    }

    @Test
    public void test() {
        new GeosteeringOld(desktopSession)
                .openModule()
                .test();
    }
}
