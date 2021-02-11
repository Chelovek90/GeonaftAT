package ru.geonaft.modules.CS.crossSectionSpace;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;

public class CrossSectionSpace extends BaseWorkSpace {
    public RemoteWebElement CSWorkSpace;

    private String crossSectionSelector = "ScreenshotProvider";
    public CrossSectionSpace(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        CSWorkSpace = (RemoteWebElement) workSpaceWindow.findElementByClassName(crossSectionSelector);
    }

    public void clickCrossSectionSpace() {
        CSWorkSpace.click();
    }
}
