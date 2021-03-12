package ru.geonaft.modules.cs.workSpaceCS;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.view.workSpaces.workSpace.BaseWorkSpace;

public class WorkSpaceCS extends BaseWorkSpace {

    protected int numberHeaders;

    public WorkSpaceCS(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }
}
