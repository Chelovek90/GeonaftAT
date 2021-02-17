package ru.geonaft.modules.CS.workSpace;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CsWorkSpace extends BaseWorkSpace {

    public RemoteWebElement csWorkSpace;

    private String mainWindowSelector = "ScreenshotProvider";

    public CsWorkSpace(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }

    public CsWorkSpace setCsWorkSpace() {
        csWorkSpace = (RemoteWebElement) workSpaceWindow.findElementByClassName(mainWindowSelector);
        return this;
    }

    private String crossSectionSelector = "MarkerEditor";
    @WindowsFindBy(accessibility = "MarkersLayerForWellSection")
    private RemoteWebElement crossSectionId;
    public CsWorkSpace clickCrossSectionSpace() {
        crossSectionId.click();
        return this;
    }

    public CsWorkSpace showAllClick() {
        baseAction.clickItemContextMenu(crossSectionId, 2);
        return this;
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
    public CsWorkSpace addTrack(OrientationTrack track) {
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
        return this;
    }

    @WindowsFindBy(accessibility = "Engineer")
    private RemoteWebElement engineerValueField;
    public CsWorkSpace enterEngineerValue() {
        String engineer = faker.superhero().name();
        baseAction.copyInBuffer(engineer);
        engineerValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "Situation")
    private RemoteWebElement situationValueField;
    public CsWorkSpace enterSituationValue() {
        String situation = faker.chuckNorris().fact();
        baseAction.copyInBuffer(situation);
        situationValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "Recommendation")
    private RemoteWebElement recommendationValueField;
    public CsWorkSpace enterRecommendationValue() {
        String recommendation = faker.beer().name();
        baseAction.copyInBuffer(recommendation);
        recommendationValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "Annotation")
    private RemoteWebElement annotationValueField;
    public CsWorkSpace enterAnnotationValue() {
        String annotation = faker.beer().name();
        baseAction.copyInBuffer(annotation);
        annotationValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "Customer")
    private RemoteWebElement customerValueField;
    public CsWorkSpace enterCustomerValue() {
        String customer = faker.artist().name();
        baseAction.copyInBuffer(customer);
        customerValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "State")
    private RemoteWebElement stateValueField;
    public CsWorkSpace enterStateValue() {
        String state = faker.howIMetYourMother().catchPhrase();
        baseAction.copyInBuffer(state);
        stateValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }
}
