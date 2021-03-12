package ru.geonaft.view.workSpaces.workSpace;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BaseWorkSpace extends Base {

    protected int numberHeaders;

    @WindowsFindBy(accessibility = "DocumentHost")
    protected RemoteWebElement mainViewID;

    private String headersPanelSelector = "TabHeadersPanel";
    protected RemoteWebElement headersPanel;

    private String headersSelector = "DocumentPaneItem";

    public BaseWorkSpace(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        this.headersPanel =
                (RemoteWebElement) mainViewID
                        .findElementByClassName(headersPanelSelector);
        this.numberHeaders = headersPanel
                .findElementsByClassName(headersSelector)
                .size();
    }

    private String workSpaceSelector = "ScreenshotProvider";
    protected RemoteWebElement workSpace;

    public BaseWorkSpace getWorkSpace() {
        workSpace =
                (RemoteWebElement) mainViewID
                        .findElementByClassName(workSpaceSelector);
        return this;
    }

    public BaseWorkSpace compareCountHeaders() {
        int count = headersPanel
                .findElementsByClassName(headersSelector)
                .size();
        assertThat("New tab not opened", count, not(equalTo(numberHeaders)));
        if (numberHeaders != count) {
            numberHeaders = count;
        }
        return this;
    }

    private String closeTabButtonSelector = "ControlBoxButtonPresenter";

    public void closeAllTab() {
        headersPanel
                .findElementsByClassName(closeTabButtonSelector)
                .stream()
                .forEach(closebtn -> closebtn.click());
    }

    private String headersName = "TabCaptionControl";
    private String nameAttribute = "Name";

    public BaseWorkSpace checkHeadersName(String nameWell) {
        String tabName = headersPanel
                .findElementByClassName(headersSelector)
                .getText();
        assertThat("Tab name does not contains well name - " + nameWell, tabName, containsString(nameWell));
        return this;
    }

    private String trackSelector = "Geosteering.UI.Infrastructure.ViewModels.Tracks.GraphicTrackViewModel";

    @WindowsFindBy(accessibility = "TracksListBox")
    private RemoteWebElement trackBox;
    public BaseWorkSpace checkCountTrack(int expectedCount) {
        assertThat("Tracks count not equal expected",
                trackBox
                        .findElements(By.name(trackSelector))
                        .size(),
                equalTo(expectedCount));
        return this;
    }

    private String trackZonesSelector = "Geosteering.UI.Infrastructure.ViewModels.Tracks.ZonesTrackViewModel";

    public BaseWorkSpace checkCountTrackZones(int expectedCount) {
        assertThat("Track zones count not equal expected",
                trackBox
                        .findElements(By.name(trackZonesSelector))
                        .size(),
                equalTo(expectedCount));
        return this;
    }

    @WindowsFindBy(accessibility = "VerticalHeaderListBox")
    private RemoteWebElement headersVerticalTrackBox;
    private String headersLegendSelector = "Geosteering.UI.Infrastructure.ViewModels.Tracks.Legend.TrackLegendItemViewModel";

    public BaseWorkSpace checkHeaderLegendInTrack(int expectedCount) {
        assertThat("Track header is empty",
                headersVerticalTrackBox
                        .findElementsByName(headersLegendSelector)
                        .size(),
                equalTo(expectedCount));
        return this;
    }

}
