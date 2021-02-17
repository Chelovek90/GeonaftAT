package ru.geonaft.modules.CS;

import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.NameEntityToProject;
import ru.geonaft.modules.CS.workSpace.CsWorkSpace;
import ru.geonaft.modules.CS.ribbon.RibbonCS;
import ru.geonaft.modules.loader.OpenModule;
import ru.geonaft.view.ribbon.modulesSelector.ModuleSelector;
import ru.geonaft.view.treeProject.TreeProject;

import static org.hamcrest.MatcherAssert.assertThat;
import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.*;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.*;

public class Geosteering extends Base implements OpenModule {

    public RibbonCS ribbon;
    public TreeProject treeProject;
    public CsWorkSpace workSpace;


    public Geosteering(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        ribbon = new RibbonCS(driver);
        treeProject = new TreeProject(driver);
        workSpace = new CsWorkSpace(driver);
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
        treeProject
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, actualWellInProject.name)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    @Step("Activate check box reference well")
    public Geosteering activeCheckBoxRefWell() {
        workSpace.clickCrossSectionSpace();
        treeProject
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, refWellInProject.name)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    public Geosteering makeWorkSpaceScreen(String name, Appointment appointment) {
        workSpace
                .showAllClick()
                .takeWorkSpaceScreen(name, appointment);
        return this;
    }

    public Geosteering makeGeonaftScreen(String name, Appointment appointment) {
        workSpace.takeGeonaftScreen(name, appointment);
        return this;
    }

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
    public Geosteering addTrack(CsWorkSpace.OrientationTrack track) {
        workSpace.addTrack(track);
        return this;
    }

    @Step("Copy trajectory well as plan")
    public Geosteering doCopyTrajectoryWellAsPlan(NameEntityToProject wellName) {
        treeProject
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, wellName.name);
        if (!(treeProject.checkFolderInTreeProject(PLAN_TRAJECTORIES))) {
            treeProject
                    .clickItemFromContextMenu(TRAJECTORY, 5)
                    .checkFolderInTreeProject(PLAN_TRAJECTORIES);
        }
        return this;
    }

    @Step("Activate check box plan trajectory well")
    public Geosteering displayPlanTrajectoryInCrossSectionWindow() {
        workSpace
                .clickCrossSectionSpace();
        treeProject
                .unfoldFolder(PLAN_TRAJECTORIES)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    @Step("Activate check box trajectory well")
    public Geosteering displayTrajectoryInCrossSectionWindow(String actualWellName) {
        workSpace
                .clickCrossSectionSpace();
        treeProject
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, actualWellName)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    @Step("Create targets trajectory")
    public Geosteering unfoldPlanTrajectoryAndCreatedTarget() {
        treeProject
                .unfoldFolder(TRAJECTORY)
                .clickItemFromContextMenu(TARGETS, 0);
        workSpace
                .compareCountHeaders()
                .clickAddRowInEditor()
                .enterMdValue()
                .checkDataEditor("create targets")
                .clickSaveAndExit();
        return this;
    }

    @Step("Display targets in cross section window")
    public Geosteering displayTargetsInCrossSectionWindow() {
        workSpace
                .clickCrossSectionSpace();
        treeProject
                .clickCheckBoxFolder(TARGETS);
        return this;
    }

    @Step("Open editor geonavigation journal")
    public Geosteering openEditorGeonavigationJournal(String actualWellName) {
        treeProject
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
        treeProject
                .clickCheckBoxFolder(GEO_JOURNAL);
        return this;
    }
}
