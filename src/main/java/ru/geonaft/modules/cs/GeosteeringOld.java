package ru.geonaft.modules.cs;

import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.NameEntityToProject;
import ru.geonaft.modules.cs.ribbonCS.InstrumentsCsSelector;
import ru.geonaft.modules.cs.ribbonCS.RibbonCS;
import ru.geonaft.modules.cs.workSpaceCS.WorkSpaceAndEditorCS;
import ru.geonaft.modules.loader.OpenModule;
import ru.geonaft.view.ribbon.buttonsSelector.ModuleSelector;
import ru.geonaft.view.ribbon.buttonsSelector.TabSelector;
import ru.geonaft.view.treeProject.TreeProjectOld;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;

import static ru.geonaft.NameEntityToProject.actualWellInProject;
import static ru.geonaft.NameEntityToProject.refWellInProject;
import static ru.geonaft.modules.cs.ribbonCS.InstrumentsCsSelector.*;
import static ru.geonaft.view.ribbon.buttonsSelector.TabSelector.CS_SCALING;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.CONTACTS;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.WELLS;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.*;

public class GeosteeringOld extends Base implements OpenModule {

    public RibbonCS ribbon;
    public TreeProjectOld treeProjectOld;
    public WorkSpaceAndEditorCS workSpace;


    public GeosteeringOld(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        ribbon = new RibbonCS(driver);
        treeProjectOld = new TreeProjectOld(driver);
        workSpace = new WorkSpaceAndEditorCS(driver);
    }

    @Step("Open geosteering")
    @Override
    public GeosteeringOld openModule() {
        ribbon.openModule(ModuleSelector.CS);
        workSpace
                .setCsWorkSpace()
                .compareCountHeaders();
        return this;
    }

    @Step("Open geosteering, chose wells and display trajectory")
    public GeosteeringOld openModuleChooseWellsAndDisplayTrajectory(NameEntityToProject wellName) {
        openModule();
        choseWellsOnRibbonPanel();
        doCopyTrajectoryWellAsPlan(wellName);
        activateCheckBoxFolder(TRAJECTORY);
        return this;
    }

    @Step("Set actual and reference wells")
    public GeosteeringOld choseWellsOnRibbonPanel() {
        ribbon
                .chooseActualWell()
                .chooseRefWell()
                .checkActivityRibbonButton();
        workSpace.checkTabName(refWellInProject.name);
        return this;
    }

    @Step("Activate check box actual well")
    public GeosteeringOld activeCheckBoxActualWell() {
        workSpace.clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, actualWellInProject.name)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    @Step("Activate check box reference well")
    public GeosteeringOld activeCheckBoxRefWell() {
        workSpace.clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, refWellInProject.name)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    public GeosteeringOld showAllAndMakeWorkSpaceScreen(String name, Appointment appointment) {
        workSpace
                .showAllClick()
                .takeWorkSpaceScreen(name, appointment);
        return this;
    }
    public GeosteeringOld makeWorkSpaceScreen(String name, Appointment appointment) {
        workSpace
                .takeWorkSpaceScreen(name, appointment);
        return this;
    }

    public GeosteeringOld makeGeonaftScreen(String name, Appointment appointment) {
        workSpace.takeGeonaftScreen(name, appointment);
        return this;
    }

    public GeosteeringOld makeTrackBlockScreen(WorkSpaceAndEditorCS.OrientationTrack track, String name, Appointment appointment) {
        workSpace.takeTracksScreen(track, name, appointment);
        return this;
    }

    @Step("Test result")
    public GeosteeringOld checkDisplayedInCrossSectionWindow(String name) {
        baseAction.takeDiffImage(name);
        baseAction.createGiffFileToAttachOnAllureReport(name);
        return this;
    }

    public GeosteeringOld showAll() {
        workSpace.showAllClick();
        return this;
    }

    @Step("Add {track} track")
    public GeosteeringOld addTrack(WorkSpaceAndEditorCS.OrientationTrack track) {
        workSpace.addTrack(track);
        return this;
    }

    @Step("Add {track} track")
    public GeosteeringOld clickOnTrack(WorkSpaceAndEditorCS.OrientationTrack track) {
        workSpace.clickOnTrack(track);
        return this;
    }

    @Step("Copy trajectory well as plan")
    public GeosteeringOld doCopyTrajectoryWellAsPlan(NameEntityToProject wellName) {
        treeProjectOld
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, wellName.name);
        if (!(treeProjectOld.checkFolderInTreeProject(PLAN_TRAJECTORIES))) {
            treeProjectOld
                    .clickItemFromContextMenu(TRAJECTORY, 5)
                    .checkFolderInTreeProject(PLAN_TRAJECTORIES);
        }
        return this;
    }

    @Step("Activate check box plan trajectory well")
    public GeosteeringOld displayPlanTrajectoryInCrossSectionWindow() {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(PLAN_TRAJECTORIES)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    @Step("Activate check box - {folder}")
    public GeosteeringOld activateCheckBoxFolder(SubFolderSelector folder) {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .clickCheckBoxFolder(folder);
        return this;
    }

    @Step("Activate check box trajectory well - {}")
    public GeosteeringOld displayTrajectoryInCrossSectionWindow(String wellName) {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, wellName)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    @Step("Create targets trajectory")
    public GeosteeringOld unfoldPlanTrajectoryAndCreatedTarget() {
        treeProjectOld
                .unfoldFolder(TRAJECTORY)
                .clickItemFromContextMenu(TARGETS, 0);
        workSpace
                .compareCountHeaders()
                .clickAddRowInEditor()
                .enterMdValue()
                .checkDataEditor("create target")
                .clickSaveAndExit()
                .compareCountHeaders();
        return this;
    }

    @Step("Create contact in editor")
    public GeosteeringOld createContactAndDisplayOnCrossSection() {
        treeProjectOld
                .openEditorRootFolder(CONTACTS);
        workSpace
                .compareCountHeaders()
                .clickAddRowInEditor()
                .enterTvdssValue()
                .checkDataEditor("create contact")
                .clickSaveAndExit()
                .compareCountHeaders();
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(CONTACTS)
                .clickCheckBoxFolder(CONTACT);
        return this;
    }

    @Step("Open editor trajectory")
    public GeosteeringOld openEditorTrajectory() {
        treeProjectOld
                .clickItemFromContextMenu(TRAJECTORY, 0);
        workSpace
                .compareCountHeaders();
        return this;
    }

    @Step("Change incl and azim trajectory in editor")
    public GeosteeringOld changeInclAndAzimTrajectory() {
        workSpace
                .changeValueInclAndAzimTrajectory()
                .checkDataEditor("Change incl and azim trajectory in editor")
                .clickSaveAndExit();
        return this;
    }

    @Step("Display targets in cross section window")
    public GeosteeringOld displayTargetsInCrossSectionWindow() {
//        workSpace
//                .clickCrossSectionSpace();
        treeProjectOld
                .clickCheckBoxFolder(TARGETS);
        return this;
    }

    @Step("Active log check box")
    public GeosteeringOld activeCheckBoxLogWellInTreeProject(String wellName) {
        treeProjectOld
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, wellName)
                .unfoldFolder(LOGS)
                .unfoldFolder(LOG)
                .clickCheckBoxFolder(CURVE);
        return this;
    }

    @Step("Active markers check box")
    public GeosteeringOld clickCheckBoxStratigraphyWellTreeProject() {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(TRAJECTORY)
                .clickCheckBoxFolder(MARKERS);
        return this;
    }

    @Step("Open editor geonavigation journal")
    public GeosteeringOld openEditorGeonavigationJournal(String actualWellName) {
        treeProjectOld
                .unfoldFolder(TRAJECTORY)
                .clickItemFromContextMenu(GEO_JOURNAL, 0);
        workSpace
                .compareCountHeaders()
                .clickAddRowInEditor();
        return this;
    }

    @Step("Fill in the fields geonavigation journal")
    public GeosteeringOld fillFieldsGeonavigationJournalSaveAdnExit() {
        workSpace
                .enterEngineerValue()
                .enterSituationValue()
                .enterRecommendationValue()
                .enterAnnotationValue()
                .enterCustomerValue()
                .enterStateValue()
                .enterMdValue()
                .checkDataEditor("Create geonavigation journal")
                .clickSaveAndExit();
        return this;
    }

    @Step("Display geonavigation journal in cross section window")
    public GeosteeringOld displayGeonavigationJournalInCrossSectionWindow() {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .clickCheckBoxFolder(GEO_JOURNAL);
        return this;
    }

    @Step("Display dip.series journal in cross section window")
    public GeosteeringOld displayDipSeriesInCrossSectionWindow() {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(IMAGES)
                .unfoldFolder(DIP_PICKING)
                .clickCheckBoxFolder(DIP_SERIES);
        return this;
    }

    public GeosteeringOld clickCsTab() {
        ribbon.clickRibbonTab(TabSelector.CS);
        return this;
    }

    @Step("Click instrument - {instrument}")
    public GeosteeringOld clickInstrumentOnRibbon(InstrumentsCsSelector instrument) {
        ribbon.clickInstrument(instrument);
        return this;
    }

    @Step("Add new dip on cross section")
    public GeosteeringOld addNewDipOnCrossSection() {
        ribbon.clickCSTabHeader();
        ribbon.clickInstrument(AddNewDip);
        workSpace.clickCrossSectionSpace();
        return this;
    }

    @Step("Open geomodel table")
    public GeosteeringOld openGeomodelTableSetCountDips() {
        ribbon.clickInstrument(InstrumentsCsSelector.GeomodelTable);
        workSpace
                .setCountDips()
                .clickTabModule(refWellInProject.name);
        return this;
    }

    @Step("switch to tab - {tabName}")
    public GeosteeringOld switchTab(String tabName) {
        workSpace
                .clickTabModule(tabName);
        return this;
    }

    @Step("Add dip - ribbon")
    public GeosteeringOld addDipRibbon() {
        ribbon
                .clickInstrument(AddNewDip);
        workSpace.clickRandomCrossSection();
        return this;
    }

    @Step("Add dip - ribbon")
    public GeosteeringOld checkAddDipInEditor() {
        switchTab("Геологические дипы");
        workSpace.checkCountDips();
        return this;
    }

    @Step("Set home view - ribbon")
    public GeosteeringOld setHomeViewRibbon() {
        ribbon
                .clickRibbonTab(CS_SCALING);
        ribbon
                .clickInstrument(SetHomeView);

        return this;
    }

    @Step("Go home view - ribbon")
    public GeosteeringOld goHomeViewRibbon() {
        ribbon
                .clickRibbonTab(CS_SCALING);
        ribbon
                .clickInstrument(GoHomeView);

        return this;
    }

    @Step("Scaling crossSection")
    public GeosteeringOld scalingCrossSection() {
        workSpace.scaling();
        return this;
    }

    public GeosteeringOld test() {
        workSpace
                .changeValueInclAndAzimTrajectory();
        return this;
    }
}
