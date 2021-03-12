package ru.geonaft.modules.cs.workSpaceCS;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.workSpaces.workSpaceWithEditor.BaseWorkSpaceAndEditor;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkSpaceAndEditorCS extends BaseWorkSpaceAndEditor {

    int countDips;
    WebElement crossSectionWindow;

    public RemoteWebElement csWorkSpace;

    private String mainWindowSelector = "ScreenshotProvider";

    public WorkSpaceAndEditorCS(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }

    @WindowsFindBy(accessibility = "MarkersLayerForWellSection")
    private RemoteWebElement crossSectionId;
    public WorkSpaceAndEditorCS setCsWorkSpace() {
        csWorkSpace =
                (RemoteWebElement) workSpaceWindow
                .findElementByClassName(mainWindowSelector);
        crossSectionWindow = crossSectionId;
        return this;
    }

    public WorkSpaceAndEditorCS clickCrossSectionSpace() {
        crossSectionWindow.click();
        return this;
    }

    public WorkSpaceAndEditorCS showAllClick() {
        baseAction
                .clickItemContextMenu((RemoteWebElement) crossSectionWindow, 2);
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

    public WorkSpaceAndEditorCS addTrack(OrientationTrack track) {
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

    public WorkSpaceAndEditorCS clickOnTrack(OrientationTrack track) {
        switch (track) {
            case VERTICAL:
                groupVerticalTrack.click();
                break;
            case HORIZONTAL:
                groupHorizontalTrack.click();
                break;
        }
        return this;
    }

    public BaseWorkSpaceAndEditor takeTracksScreen(OrientationTrack track, String screenName, Appointment appointment) {
        baseAction.takeScreenshot(workSpaceWindow, screenName, appointment);
        switch (track) {
            case VERTICAL:
                baseAction.takeScreenshot(groupVerticalTrack, screenName, appointment);
                break;
            case HORIZONTAL:
                baseAction.takeScreenshot(groupHorizontalTrack, screenName, appointment);
                break;
        }
        return this;
    }

    public WorkSpaceAndEditorCS selectCurveOnHorizontalTrack() {
        int heightElement = groupHorizontalTrack.getSize().getHeight();
        actions.moveToElement(groupHorizontalTrack, 8, heightElement / 2)
                .click()
                .build()
                .perform();
        return this;
    }

    public WorkSpaceAndEditorCS selectCurveOnVerticalTrack() {
        int widthElement = groupVerticalTrack.getSize().getWidth();
        actions.moveToElement(groupVerticalTrack, widthElement / 2, 34)
                .click()
                .build()
                .perform();
        return this;
    }

    @WindowsFindBy(accessibility = "Engineer")
    private RemoteWebElement engineerValueField;

    public WorkSpaceAndEditorCS enterEngineerValue() {
        String engineer = faker.superhero().name();
        baseAction.copyInBuffer(engineer);
        engineerValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "Situation")
    private RemoteWebElement situationValueField;

    public WorkSpaceAndEditorCS enterSituationValue() {
        String situation = faker.chuckNorris().fact();
        baseAction.copyInBuffer(situation);
        situationValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "Recommendation")
    private RemoteWebElement recommendationValueField;

    public WorkSpaceAndEditorCS enterRecommendationValue() {
        String recommendation = faker.beer().name();
        baseAction.copyInBuffer(recommendation);
        recommendationValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "Annotation")
    private RemoteWebElement annotationValueField;

    public WorkSpaceAndEditorCS enterAnnotationValue() {
        String annotation = faker.beer().name();
        baseAction.copyInBuffer(annotation);
        annotationValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "Customer")
    private RemoteWebElement customerValueField;

    public WorkSpaceAndEditorCS enterCustomerValue() {
        String customer = faker.artist().name();
        baseAction.copyInBuffer(customer);
        customerValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "State")
    private RemoteWebElement stateValueField;

    public WorkSpaceAndEditorCS enterStateValue() {
        String state = faker.howIMetYourMother().catchPhrase();
        baseAction.copyInBuffer(state);
        stateValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }


    public WorkSpaceAndEditorCS checkAddDipOnTable() {
        List<WebElement> list = dataTable.findElementsByName(dataString);
        assertTrue(list.size() != 0, "Table is empty");
        return this;
    }

    public WorkSpaceAndEditorCS setCountDips() {
        List<WebElement> list = dataTable.findElementsByName(dataString);
        assertTrue(list.size() != 0, "Table is empty");
        countDips = list.size();
        return this;
    }

    public WorkSpaceAndEditorCS checkCountDips() {
        List<WebElement> list = dataTable.findElementsByName(dataString);
        assertTrue(list.size() != 0, "Table is empty");
        assertThat("New dip was not added", countDips, not(equalTo(list.size())));
        countDips = list.size();
        return this;
    }

//    public WorkSpaceCs addDipHotKey() {
//        int offSet = crossSectionId.getSize().getWidth() / (random.nextInt(5) + 1);
//        actions
//                .moveToElement(crossSectionId, offSet, 100)
//                .click()
//                .build()
//                .perform();
//        actions
//                .moveToElement(crossSectionId, offSet, 100)
//                .click()
//                .keyUp(Keys.LEFT_CONTROL)
//                .keyUp(Keys.LEFT_ALT)
//                .click()
//                .build()
//                .perform();
//        robot.keyPress(KeyEvent.VK_CONTROL);
//        robot.keyPress(KeyEvent.VK_ALT);
//        actions.click();
//        return this;
//    }

    public WorkSpaceAndEditorCS clickRandomCrossSection() {
        int xOffSet = crossSectionWindow.getSize().getWidth() / (random.nextInt(15) + 2);
        int yOffSet = crossSectionWindow.getSize().getHeight() / (random.nextInt(15) + 2);
        actions
                .moveToElement(crossSectionWindow, xOffSet, yOffSet)
                .click()
                .build()
                .perform();
        return this;
    }

    @WindowsFindBy(accessibility = "Incl")
    private List<RemoteWebElement> inclFields;
    @WindowsFindBy(accessibility = "Azim")
    private List<RemoteWebElement> azimFields;
    public BaseWorkSpaceAndEditor changeValueInclAndAzimTrajectory() {
        inclFields.stream()
                .skip(1)
                .forEach(cell -> {
                    cell.click();
//                    baseAction.copyInBuffer(String.valueOf(random.nextDouble()*100));
                    baseAction.copyInBuffer(String.valueOf(random.nextInt(50)));
                    baseAction.pastFromBuffer();
                });
        azimFields.stream()
                .skip(1)
                .forEach(cell -> {
                    cell.click();
//                    baseAction.copyInBuffer(String.valueOf(random.nextDouble()*100));
                    baseAction.copyInBuffer(String.valueOf(200 + random.nextInt(100)));
                    baseAction.pastFromBuffer();
                });
        return this;
    }


    public WorkSpaceAndEditorCS scaling () {
        baseAction.moveTo((RemoteWebElement) crossSectionWindow);
        robot.mouseWheel(+100);
        return this;
    }
}
