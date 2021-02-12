package ru.geonaft.modules.CS.workSpace;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;

public class CrossSectionSpace extends BaseWorkSpace {
    public RemoteWebElement CSWorkSpace;

    private String mainWindowSelector = "ScreenshotProvider";

    public CrossSectionSpace(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        CSWorkSpace = (RemoteWebElement) workSpaceWindow.findElementByClassName(mainWindowSelector);
    }

    private String crossSectionSelector = "MarkerEditor";

    public void clickCrossSectionSpace() {
        CSWorkSpace
                .findElementByClassName(crossSectionSelector)
                .click();
    }

    public void takeCrossSectionScreen(String screenName, Appointment appointment) {
        baseAction.takeScreenshotToAttachOnAllureReport((RemoteWebElement) CSWorkSpace
                .findElementByClassName(crossSectionSelector), screenName, appointment);
    }

    public void takeCSModuleScreen(String screenName, Appointment appointment) {
        baseAction.takeScreenshotToAttachOnAllureReport(geonaftWindow, screenName, appointment);
    }

    public void showAllClick() {
        RemoteWebElement crossSection = (RemoteWebElement) CSWorkSpace
                .findElementByClassName(crossSectionSelector);
        baseAction.clickItemContextMenu(crossSection, 2);
    }
}
