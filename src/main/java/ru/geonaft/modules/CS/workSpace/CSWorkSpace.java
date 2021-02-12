package ru.geonaft.modules.CS.workSpace;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CSWorkSpace extends BaseWorkSpace {
    public RemoteWebElement CSWorkSpace;

    private String mainWindowSelector = "ScreenshotProvider";

    public CSWorkSpace(WindowsDriver<RemoteWebElement> driver) {
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

    public static enum OrientationTrack {
        VERTICAL,
        HORIZONTAL;
    }
    @WindowsFindBy(accessibility = "TrackBoxVerGr")
    private RemoteWebElement groupVerticalTrack;
    @WindowsFindBy(accessibility = "TrackBoxHorGr")
    private RemoteWebElement groupHorizontalTrack;
    private String trackSelector = "ListBoxItem";
    public void addTrack(OrientationTrack track) {
        int primaryCount;
        int secondaryCount;
        switch (track) {
            case VERTICAL:
                primaryCount = groupVerticalTrack.findElementsByClassName(trackSelector).size();
                baseAction.clickItemContextMenu(
                        (RemoteWebElement) groupVerticalTrack.findElementByClassName(trackSelector), 0);
                secondaryCount = groupVerticalTrack.findElementsByClassName(trackSelector).size();
                assertThat("Vertical rack was not added", primaryCount, not(equalTo(secondaryCount)));
                break;
            case HORIZONTAL:
                primaryCount = groupHorizontalTrack.findElementsByClassName(trackSelector).size();
                baseAction.clickItemContextMenu(
                        (RemoteWebElement) groupHorizontalTrack.findElementByClassName(trackSelector), 0);
                secondaryCount = groupHorizontalTrack.findElementsByClassName(trackSelector).size();
                assertThat("Horizontal track was not added", primaryCount, not(equalTo(secondaryCount)));
                break;

        }
    }
}
