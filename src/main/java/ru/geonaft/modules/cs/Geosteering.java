package ru.geonaft.modules.cs;

import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.modules.cs.ribbonCS.RibbonCS;
import ru.geonaft.modules.cs.treeProjectCS.TreeProjectCS;
import ru.geonaft.modules.cs.workSpaceCS.WorkSpaceAndEditorCS;
import ru.geonaft.modules.cs.workSpaceCS.WorkSpaceCS;
import ru.geonaft.modules.loader.OpenModule;
import ru.geonaft.view.ribbon.buttonsSelector.ModuleSelector;
import ru.geonaft.view.treeProject.TreeProjectOld;

import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.view.ribbon.buttonsSelector.ModuleSelector.*;

public class Geosteering extends Base implements OpenModule {

    public RibbonCS ribbon;
    public TreeProjectCS treeProject;
    public WorkSpaceCS workSpace;

    public Geosteering(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        ribbon = new RibbonCS(driver);
        treeProject = new TreeProjectCS(driver);
        workSpace = new WorkSpaceCS(driver);
    }

    @Step("Open geosteering")
    @Override
    public Geosteering openModule() {
        ribbon
                .openModule(CS);
        workSpace
                .compareCountHeaders()
                .getWorkSpace();
        treeProject
                .checkCreateSubfolderModule(1);
        return this;
    }

    @Step("Set actual and reference wells")
    public Geosteering choseWellsOnRibbonPanel() {
        ribbon
                .chooseActualWell()
                .chooseRefWell()
                .checkActivityRibbonButton();
        workSpace
                .checkHeadersName(refWellInProject.name);
        treeProject
                .checkSubfolderModuleName(refWellInProject.name);
        return this;
    }

}
