package ru.geonaft.modules.CS;

import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.modules.CS.crossSectionSpace.CrossSectionSpace;
import ru.geonaft.modules.CS.ribbon.RibbonCS;
import ru.geonaft.modules.loader.OpenModule;
import ru.geonaft.view.ribbon.modulesSelector.ModuleSelector;
import ru.geonaft.view.treeProject.TreeProject;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;

import java.util.PrimitiveIterator;

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

    public Geosteering choseWellOnRibbonPanel() {
        ribbon.chooseActualWell();
        ribbon.chooseRefWell();
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
}
