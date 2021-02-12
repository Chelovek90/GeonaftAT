package ru.geonaft.modules.CS;

import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.modules.CS.workSpace.CrossSectionSpace;
import ru.geonaft.modules.CS.ribbon.RibbonCS;
import ru.geonaft.modules.loader.OpenModule;
import ru.geonaft.view.ribbon.modulesSelector.ModuleSelector;
import ru.geonaft.view.treeProject.TreeProject;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;

import static ru.geonaft.NameEntityToProject.actualWellInProject;
import static ru.geonaft.NameEntityToProject.refWellInProject;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.WELLS;

public class Geosteering extends Base implements OpenModule {

    public RibbonCS ribbon;
    public TreeProject treeProject;
    public CrossSectionSpace CSWorkSpace;



    public Geosteering(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        ribbon = new RibbonCS(driver);
        treeProject = new TreeProject(driver);
    }

    @Step("Open geosteering")
    @Override
    public Geosteering openModule() {
        ribbon.openModule(ModuleSelector.CS);
        CSWorkSpace = new CrossSectionSpace(driver);
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
                .unfoldFolder(SubFolderSelector.WELL, actualWellInProject.name)
                .clickCheckBoxFolder(SubFolderSelector.TRAJECTORY);
        return this;
    }

    public Geosteering activeCheckBoxRefWell() {
        CSWorkSpace.clickCrossSectionSpace();
        treeProject
                .unfoldFolder(WELLS)
                .unfoldFolder(SubFolderSelector.WELL, refWellInProject.name)
                .clickCheckBoxFolder(SubFolderSelector.TRAJECTORY);
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

    public Geosteering showAll(String name, Appointment appointment) {
        CSWorkSpace.showAllClick();
        return this;
    }

    public Geosteering check() {
        CSWorkSpace = new CrossSectionSpace(driver);
        ribbon.checkActivityRibbonButton();
        return this;
    }
}
