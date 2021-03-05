package ru.geonaft.modules.cs;

import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.NameEntityToProject;
import ru.geonaft.modules.cs.ribbonCS.InstrumentsCsSelector;
import ru.geonaft.modules.cs.ribbonCS.RibbonCS;
import ru.geonaft.modules.cs.workSpace.WorkSpaceAndEditorCS;
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

public class Geosteering extends Base implements OpenModule {

    public RibbonCS ribbon;
    public TreeProjectOld treeProjectOld;
    public WorkSpaceAndEditorCS workSpace;


    public Geosteering(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        ribbon = new RibbonCS(driver);
        treeProjectOld = new TreeProjectOld(driver);
        workSpace = new WorkSpaceAndEditorCS(driver);
    }

    @Step("Open geosteering")
    @Override
    public Geosteering openModule() {
        ribbon.openModule(ModuleSelector.CS);
        workSpace
                .setCsWorkSpace()
                .compareCountHeaders();
        return this;
    }

    @Step("Open geosteering, chose wells and display trajectory")
    public Geosteering openModuleChooseWellsAndDisplayTrajectory(NameEntityToProject wellName) {
        openModule();
        choseWellsOnRibbonPanel();
        doCopyTrajectoryWellAsPlan(wellName);
        activateCheckBoxFolder(TRAJECTORY);
        return this;
    }

    @Step("Set actual and reference wells")
    public Geosteering choseWellsOnRibbonPanel() {
        ribbon
                .chooseActualWell()
                .chooseRefWell()
                .checkActivityRibbonButton();
        workSpace.checkTabName(refWellInProject.name);
        return this;
    }

    @Step("Activate check box actual well")
    public Geosteering activeCheckBoxActualWell() {
        workSpace.clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, actualWellInProject.name)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    @Step("Activate check box reference well")
    public Geosteering activeCheckBoxRefWell() {
        workSpace.clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, refWellInProject.name)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    public Geosteering showAllAndMakeWorkSpaceScreen(String name, Appointment appointment) {
        workSpace
                .showAllClick()
                .takeWorkSpaceScreen(name, appointment);
        return this;
    }
    public Geosteering makeWorkSpaceScreen(String name, Appointment appointment) {
        workSpace
                .takeWorkSpaceScreen(name, appointment);
        return this;
    }

    public Geosteering makeGeonaftScreen(String name, Appointment appointment) {
        workSpace.takeGeonaftScreen(name, appointment);
        return this;
    }

    public Geosteering makeTrackBlockScreen(WorkSpaceAndEditorCS.OrientationTrack track, String name, Appointment appointment) {
        workSpace.takeTracksScreen(track, name, appointment);
        return this;
    }

    @Step("Test result")
    public Geosteering checkDisplayedInCrossSectionWindow(String name) {
        baseAction.takeDiffImage(name);
        baseAction.createGiffFileToAttachOnAllureReport(name);
        return this;
    }

    public Geosteering showAll() {
        workSpace.showAllClick();
        return this;
    }

    @Step("Add {track} track")
    public Geosteering addTrack(WorkSpaceAndEditorCS.OrientationTrack track) {
        workSpace.addTrack(track);
        return this;
    }

    @Step("Add {track} track")
    public Geosteering clickOnTrack(WorkSpaceAndEditorCS.OrientationTrack track) {
        workSpace.clickOnTrack(track);
        return this;
    }

    @Step("Copy trajectory well as plan")
    public Geosteering doCopyTrajectoryWellAsPlan(NameEntityToProject wellName) {
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
    public Geosteering displayPlanTrajectoryInCrossSectionWindow() {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(PLAN_TRAJECTORIES)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    @Step("Activate check box - {folder}")
    public Geosteering activateCheckBoxFolder(SubFolderSelector folder) {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .clickCheckBoxFolder(folder);
        return this;
    }

    @Step("Activate check box trajectory well - {}")
    public Geosteering displayTrajectoryInCrossSectionWindow(String wellName) {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, wellName)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    @Step("Create targets trajectory")
    public Geosteering unfoldPlanTrajectoryAndCreatedTarget() {
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
    public Geosteering createContactAndDisplayOnCrossSection() {
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
    public Geosteering openEditorTrajectory() {
        treeProjectOld
                .clickItemFromContextMenu(TRAJECTORY, 0);
        workSpace
                .compareCountHeaders();
        return this;
    }

    @Step("Change incl and azim trajectory in editor")
    public Geosteering changeInclAndAzimTrajectory() {
        workSpace
                .changeValueInclAndAzimTrajectory()
                .checkDataEditor("Change incl and azim trajectory in editor")
                .clickSaveAndExit();
        return this;
    }

    @Step("Display targets in cross section window")
    public Geosteering displayTargetsInCrossSectionWindow() {
//        workSpace
//                .clickCrossSectionSpace();
        treeProjectOld
                .clickCheckBoxFolder(TARGETS);
        return this;
    }

    @Step("Active log check box")
    public Geosteering activeCheckBoxLogWellInTreeProject(String wellName) {
        treeProjectOld
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, wellName)
                .unfoldFolder(LOGS)
                .unfoldFolder(LOG)
                .clickCheckBoxFolder(CURVE);
        return this;
    }

    @Step("Active markers check box")
    public Geosteering clickCheckBoxStratigraphyWellTreeProject() {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(TRAJECTORY)
                .clickCheckBoxFolder(MARKERS);
        return this;
    }

    @Step("Open editor geonavigation journal")
    public Geosteering openEditorGeonavigationJournal(String actualWellName) {
        treeProjectOld
                .unfoldFolder(TRAJECTORY)
                .clickItemFromContextMenu(GEO_JOURNAL, 0);
        workSpace
                .compareCountHeaders()
                .clickAddRowInEditor();
        return this;
    }

    @Step("Fill in the fields geonavigation journal")
    public Geosteering fillFieldsGeonavigationJournalSaveAdnExit() {
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
    public Geosteering displayGeonavigationJournalInCrossSectionWindow() {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .clickCheckBoxFolder(GEO_JOURNAL);
        return this;
    }

    @Step("Display dip.series journal in cross section window")
    public Geosteering displayDipSeriesInCrossSectionWindow() {
        workSpace
                .clickCrossSectionSpace();
        treeProjectOld
                .unfoldFolder(IMAGES)
                .unfoldFolder(DIP_PICKING)
                .clickCheckBoxFolder(DIP_SERIES);
        return this;
    }

    public Geosteering clickCsTab() {
        ribbon.clickRibbonTab(TabSelector.CS);
        return this;
    }

    @Step("Click instrument - {instrument}")
    public Geosteering clickInstrumentOnRibbon(InstrumentsCsSelector instrument) {
        ribbon.clickInstrument(instrument);
        return this;
    }

    @Step("Add new dip on cross section")
    public Geosteering addNewDipOnCrossSection() {
        ribbon.clickCSTabHeader();
        ribbon.clickInstrument(AddNewDip);
        workSpace.clickCrossSectionSpace();
        return this;
    }

    @Step("Open geomodel table")
    public Geosteering openGeomodelTableSetCountDips() {
        ribbon.clickInstrument(InstrumentsCsSelector.GeomodelTable);
        workSpace
                .setCountDips()
                .clickTabModule(refWellInProject.name);
        return this;
    }

    @Step("switch to tab - {tabName}")
    public Geosteering switchTab(String tabName) {
        workSpace
                .clickTabModule(tabName);
        return this;
    }

    @Step("Add dip - ribbon")
    public Geosteering addDipRibbon() {
        ribbon
                .clickInstrument(AddNewDip);
        workSpace.clickRandomCrossSection();
        return this;
    }

    @Step("Add dip - ribbon")
    public Geosteering checkAddDipInEditor() {
        switchTab("Геологические дипы");
        workSpace.checkCountDips();
        return this;
    }

    @Step("Set home view - ribbon")
    public Geosteering setHomeViewRibbon() {
        ribbon
                .clickRibbonTab(CS_SCALING);
        ribbon
                .clickInstrument(SetHomeView);

        return this;
    }

    @Step("Go home view - ribbon")
    public Geosteering goHomeViewRibbon() {
        ribbon
                .clickRibbonTab(CS_SCALING);
        ribbon
                .clickInstrument(GoHomeView);

        return this;
    }

    @Step("Scaling crossSection")
    public Geosteering scalingCrossSection() {
        workSpace.scaling();
        return this;
    }


    public Geosteering test() {
        workSpace
                .changeValueInclAndAzimTrajectory();
        return this;
    }
}
