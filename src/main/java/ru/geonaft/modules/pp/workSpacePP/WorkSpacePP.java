package ru.geonaft.modules.pp.workSpacePP;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.workSpaces.workSpace.BaseWorkSpace;

public class WorkSpacePP extends BaseWorkSpace {

    public WorkSpacePP(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }


    public WorkSpacePP checkCreateTrackWithCurve(int expectedCountTrack, int expectedCountCurve) {
        checkCountTrack(expectedCountTrack);
        checkHeaderLegendInTrack(expectedCountCurve);
        return this;
    }

    public WorkSpacePP checkCrateTrackZone(int expectedCountTrack) {
        checkCountTrackZones(expectedCountTrack);
        return this;
    }




}
