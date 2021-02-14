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
        workSpace.setCsWorkSpace();
        workSpace.compareCountHeaders();
        return this;
    }

    @Step("Set actual and reference wells")
    public Geosteering choseWellsOnRibbonPanel() {
        ribbon.chooseActualWell();
        ribbon.chooseRefWell();
        ribbon.checkActivityRibbonButton();
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

    public Geosteering makeCrossSectionWindowScreen(String name, Appointment appointment) {
        workSpace.showAllClick();
        workSpace.takeCrossSectionScreen(name, appointment);
        return this;
    }

    public Geosteering makeModuleScreen(String name, Appointment appointment) {
        workSpace.takeCSModuleScreen(name, appointment);
        return this;
    }

    public Geosteering checkDisplayedOnCrossSectionSpace(String name) {
        baseAction.takeDiffImage(name);
        baseAction.createGiffFileToAttachOnAllureReport(name);
        return this;
    }

    public Geosteering showAll() {
        workSpace.showAllClick();
        return this;
    }

    @Step("Add {CsWorkSpace.OrientationTrack track} track")
    public Geosteering addTrack(CsWorkSpace.OrientationTrack track) {
        workSpace.addTrack(track);
        return this;
    }

    @Step("Copy trajectory well as plan")
    public Geosteering doCopyTrajectoryWellAsPlan(NameEntityToProject wellName) {
        treeProject.unfoldFolder(WELLS);
        treeProject.unfoldFolder(WELL, wellName.name);
        treeProject.clickItemFromContextMenu(TRAJECTORY, 5);
        treeProject.checkFolderInTreeProject(PLAN_TRAJECTORIES);
        return this;
    }

    @Step("Activate check box plan trajectory well")
    public Geosteering activeCheckBoxPlanTrajectoryWell(NameEntityToProject wellName) {
        workSpace.clickCrossSectionSpace();
        treeProject
//                .unfoldFolder(WELLS)
//                .unfoldFolder(WELL, wellName.name)
                .unfoldFolder(PLAN_TRAJECTORIES)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    public Geosteering check() {
        workSpace.testTab();
        return this;
    }
}
