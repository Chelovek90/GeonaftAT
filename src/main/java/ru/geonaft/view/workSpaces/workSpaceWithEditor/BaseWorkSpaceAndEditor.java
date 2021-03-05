package ru.geonaft.view.workSpaces.workSpaceWithEditor;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.helpers.BaseAction;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.geonaft.Base.Appointment.*;

public class BaseWorkSpaceAndEditor extends Base {

    protected int numberHeaders;

    @WindowsFindBy(accessibility = "DocumentHost")
    private RemoteWebElement rootWindowSelector;
    protected RemoteWebElement workSpaceWindow;

    private String tabHeadersPanel = "TabHeadersPanel";
    protected RemoteWebElement headersPanel;

    private String closeButtonSelector = "ControlBoxButtonPresenter";

    public BaseWorkSpaceAndEditor(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        this.baseAction = new BaseAction(driver);
        this.workSpaceWindow = rootWindowSelector;
        this.numberHeaders = workSpaceWindow
                .findElementByClassName(tabHeadersPanel)
                .findElements(By.className(closeButtonSelector))
                .size();
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

    @WindowsFindBy(accessibility = "PART_CloseButton")
    private List<RemoteWebElement> closeButtons;
    public BaseWorkSpaceAndEditor compareCountHeaders() {
        int count = workSpaceWindow
                .findElementByClassName(tabHeadersPanel)
                .findElements(By.className(closeButtonSelector))
                .size();
        assertThat("New tab not opened",count, not(equalTo(numberHeaders)));
        if (numberHeaders != count) {
            numberHeaders = count;
        }
        return this;
    }

    public void closeAllTab() {
        workSpaceWindow
                .findElementByClassName(tabHeadersPanel)
                .findElements(By.className(closeButtonSelector))
                .stream()
                .forEach(closeBtn -> closeBtn.click());
    }

    private String tabHeaderSelector = "DocumentPaneItem";
    private String nameAttribute = "Name";
    public BaseWorkSpaceAndEditor checkTabName(String nameWell) {
        String tabName = workSpaceWindow
                .findElementByClassName(tabHeadersPanel)
                .findElement(By.className(tabHeaderSelector))
                .getAttribute(nameAttribute);
        assertThat("Tab name does not contains well name - " + nameWell, tabName, containsString(nameWell) );
        return this;
    }

    public BaseWorkSpaceAndEditor clickTabModule(String tabName) {
        WebElement tabModule = workSpaceWindow
                .findElementByClassName(tabHeadersPanel)
                .findElements(By.className(tabHeaderSelector))
                .stream()
                .filter(tab -> tab.getAttribute(nameAttribute).contains(tabName))
                .findFirst().orElse(null);
        assertTrue(tabModule != null, "Search tab with - " + tabName + " returned no results");
        tabModule.click();
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

    public BaseWorkSpaceAndEditor takeWorkSpaceScreen(String screenName, Appointment appointment) {
        baseAction.takeScreenshot(workSpaceWindow, screenName, appointment);
        return this;
    }

    public BaseWorkSpaceAndEditor takeGeonaftScreen(String screenName, Appointment appointment) {
        baseAction.takeScreenshot(geonaftWindow, screenName, appointment);
        return this;
    }
}
