package ru.geonaft.modules.pp.treeProjectPP;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.treeProject.BaseTreeProject;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;

import static org.hamcrest.MatcherAssert.assertThat;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.*;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.*;

public class TreeProjectPP extends BaseTreeProject {

    public TreeProjectPP(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }

    public TreeProjectPP unfoldLogFolder() {
        unfoldFolder(WELLS);
        unfoldFolder(WELL);
        unfoldFolder(LOGS);
        unfoldFolder(LOG);
        return this;
    }

    public TreeProjectPP checkCreateFolder(SubFolderSelector folder) {
        unfoldLogFolder();
        checkFolder(folder, 1);
        return this;
    }

    public TreeProjectPP checkCalculatedCurveInFolder(int expectedCount) {
        unfoldLogFolder();
        unfoldFolder(PRESSURE);
        checkCurveInFolder(CALC_CURVE, expectedCount);
        return this;
    }
}
