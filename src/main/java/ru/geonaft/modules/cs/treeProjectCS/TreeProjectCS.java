package ru.geonaft.modules.cs.treeProjectCS;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.treeProject.BaseTreeProject;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;
import ru.geonaft.view.workSpaces.workSpace.BaseWorkSpace;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.MODULES;

public class TreeProjectCS extends BaseTreeProject {

    public TreeProjectCS(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }

    public TreeProjectCS checkSubfolderModuleName(String nameWell) {
        unfoldFolder(MODULES);
        String subfolderName =
                getFolderNameInRootFolder(SubFolderSelector.MODULE);
        assertThat("Subfolder module does not contains well name - " + nameWell, subfolderName, containsString(nameWell));
        return this;
    }
}
