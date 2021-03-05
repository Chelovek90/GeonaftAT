package ru.geonaft.modules.pp.treeProjectPP;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.treeProject.BaseTreeProject;

import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.*;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.*;

public class TreeProjectPP extends BaseTreeProject {

    public TreeProjectPP(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }

    public TreeProjectPP checkCreateSubfolderModule() {
        unfoldFolder(MODULES);
        checkDataFolder(MODULE);
        return this;
    }

    public TreeProjectPP returnSubfolderModuleName() {

        return this;
    }
}
