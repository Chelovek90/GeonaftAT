package ru.geonaft.modules.CS;

import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.NameEntityToProject;
import ru.geonaft.modules.CS.workSpace.CSWorkSpace;
import ru.geonaft.modules.CS.ribbon.RibbonCS;
import ru.geonaft.modules.loader.OpenModule;
import ru.geonaft.view.ribbon.modulesSelector.ModuleSelector;
import ru.geonaft.view.treeProject.TreeProject;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;

import static org.hamcrest.MatcherAssert.assertThat;
import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.modules.CS.workSpace.CSWorkSpace.OrientationTrack.*;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.*;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.*;

public class Geosteering extends Base implements OpenModule {

    public RibbonCS ribbon;
    public TreeProject treeProject;
    public CSWorkSpace CSWorkSpace;


    public Geosteering(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        ribbon = new RibbonCS(driver);
        treeProject = new TreeProject(driver);
    }

    @Step("Open geosteering")
    @Override
    public Geosteering openModule() {
        ribbon.openModule(ModuleSelector.CS);
        CSWorkSpace = new CSWorkSpace(driver);
        CSWorkSpace.compareCountHeaders();
        return this;
    }

    public Geosteering choseWellsOnRibbonPanel() {
        ribbon.chooseActualWell();
        ribbon.chooseRefWell();
        ribbon.checkActivityRibbonButton();
        return this;
    }

    public Geosteering activeCheckBoxActualWell() {
        CSWorkSpace.clickCrossSectionSpace();
        treeProject
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, actualWellInProject.name)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    public Geosteering activeCheckBoxRefWell() {
        CSWorkSpace.clickCrossSectionSpace();
        treeProject
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, refWellInProject.name)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    public Geosteering makeCrossSectionWindowScreen(String name, Appointment appointment) {
        CSWorkSpace.showAllClick();
        CSWorkSpace.takeCrossSectionScreen(name, appointment);
        return this;
    }

    public Geosteering makeModuleScreen(String name, Appointment appointment) {
        CSWorkSpace.takeCSModuleScreen(name, appointment);
        return this;
    }

    public Geosteering checkDisplayedOnCrossSectionSpace(String name) {
        baseAction.takeDiffImage(name);
        baseAction.createGiffFileToAttachOnAllureReport(name);
        return this;
    }

    public Geosteering showAll() {
        CSWorkSpace.showAllClick();
        return this;
    }

    public Geosteering addTrack(CSWorkSpace.OrientationTrack track) {
        CSWorkSpace.addTrack(track);
        return this;
    }

    public Geosteering doCopyTrajectoryWellAsPlan(NameEntityToProject wellName) {
        treeProject.unfoldFolder(WELLS);
        treeProject.unfoldFolder(WELL, wellName.name);
        treeProject.clickItemFromContextMenu(TRAJECTORY, 5);
        treeProject.checkFolderInTreeProject(PLAN_TRAJECTORIES);
        return this;
    }

    public Geosteering activeCheckBoxPlanTrajectoryWell(NameEntityToProject wellName) {
        CSWorkSpace.clickCrossSectionSpace();
        treeProject
//                .unfoldFolder(WELLS)
//                .unfoldFolder(WELL, wellName.name)
                .unfoldFolder(PLAN_TRAJECTORIES)
                .clickCheckBoxFolder(TRAJECTORY);
        return this;
    }

    public Geosteering check() {
        CSWorkSpace = new CSWorkSpace(driver);
        return this;
    }
}
