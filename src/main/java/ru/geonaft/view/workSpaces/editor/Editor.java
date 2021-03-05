package ru.geonaft.view.workSpaces.editor;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.workSpaces.workSpaceWithEditor.BaseWorkSpaceAndEditor;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.geonaft.Base.Appointment.PRIMARY;

public class Editor extends BaseWorkSpaceAndEditor {

    private RemoteWebElement tableEditor;

    public Editor(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }

    @WindowsFindBy(accessibility = "dataPresenter")
    protected RemoteWebElement dataTable;
    protected String dataString = "System.Data.DataRowView";
    public BaseWorkSpaceAndEditor checkDataEditor(String name){
        baseAction.takeScreenshotToAttachOnAllureReport(workSpaceWindow, name , PRIMARY);
        List<WebElement> list = dataTable.findElementsByName(dataString);
        assertTrue(list.size() != 0, "Data is empty");
        return this;
    }

    private String panelButtonsSelector = "Bar";
    private String buttonsSelector = "BarButtonItemLinkControl";
    public BaseWorkSpaceAndEditor clickAddRowInEditor() {
        workSpaceWindow
                .findElementByClassName(panelButtonsSelector)
                .findElements(By.className(buttonsSelector)).get(2)
                .click();
        return this;
    }

    @WindowsFindBy(accessibility = "MD")
    private RemoteWebElement mdValueField;
    public BaseWorkSpaceAndEditor enterMdValue() {
        int MD = 500 + random.nextInt(500);
        baseAction.copyInBuffer(String.valueOf(MD));
        mdValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "TVDSS")
    private RemoteWebElement tvdssValueField;
    public BaseWorkSpaceAndEditor enterTvdssValue() {
        int TVDSS = -500 + random.nextInt(500)*-1;
        baseAction.copyInBuffer(String.valueOf(TVDSS));
        tvdssValueField.click();
        baseAction.pastFromBuffer();
        return this;
    }

    @WindowsFindBy(accessibility = "BarButtonItemLinkBSaveClose")
    private RemoteWebElement saveAndExitButton;
    public BaseWorkSpaceAndEditor clickSaveAndExit() {
        workSpaceWindow
                .findElementByClassName(panelButtonsSelector)
                .findElements(By.className(buttonsSelector)).get(1)
                .click();
        return this;
    }
}
