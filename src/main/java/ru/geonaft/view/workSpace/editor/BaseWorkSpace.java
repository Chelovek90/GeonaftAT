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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
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
        this.numberHeaders = workSpaceWindow
                .findElementByClassName(tabHeadersPanel)
                .findElements(By.className(closeButtonSelector))
                .size();
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
}
