package ru.geonaft.view.workSpace.editor;

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

public class BaseWorkSpace extends Base {


    protected RemoteWebElement workSpaceWindow;
    protected RemoteWebElement headersPanel;
    @WindowsFindBy(accessibility = "DocumentHost")
    private RemoteWebElement rootWindowSelector;
    private String tabHeadersPanel = "TabHeadersPanel";

    private String closeButtonSelector = "ControlBoxButtonPresenter";

    public BaseWorkSpace(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        this.baseAction = new BaseAction(driver);
        this.workSpaceWindow = rootWindowSelector;
    }

    @WindowsFindBy(accessibility = "dataPresenter")
    private RemoteWebElement dataTable;
    private String dataString = "System.Data.DataRowView";
    public void checkDataEditor(String name){
        baseAction.takeScreenshotToAttachOnAllureReport(workSpaceWindow, name , PRIMARY);
        List<WebElement> list = dataTable.findElementsByName(dataString);
        assertTrue(list.size() != 0, "Data is empty");
    }

    @WindowsFindBy(accessibility = "PART_CloseButton")
    private List<RemoteWebElement> closeButtons;
    public void compareCountHeaders() {
        int count = workSpaceWindow
                .findElementByClassName(tabHeadersPanel)
                .findElements(By.className(closeButtonSelector))
                .size();
        assertThat("New tab not opened",count, not(equalTo(numberHeaders)));
        if (numberHeaders != count) {
            numberHeaders = count;
        }
    }

    public void setCountHeaders() {
        this.numberHeaders = workSpaceWindow
                .findElementByClassName(tabHeadersPanel)
                .findElements(By.className(closeButtonSelector))
                .size();
    }

    public void closeFirstTab() {
        workSpaceWindow
                .findElementByClassName(tabHeadersPanel)
                .findElement(By.className(closeButtonSelector))
                .click();

    }

    private String tabNameValue = "HelpTextHelpText";
    private String tabNameSelector = "TabCaptionControl";
    public void checkTabName(String nameWell) {
        String tabName = workSpaceWindow
                .findElementByClassName(tabHeadersPanel)
                .findElement(By.className(tabNameSelector))
                .getAttribute(tabNameValue);
        assertThat("Tab name does not contains well name", tabName, containsString(nameWell) );

    }
}
