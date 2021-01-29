package ru.geonaft.view.workSpace.editor;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BaseWorkSpace extends Base {


    protected RemoteWebElement WorkSpaceWindow;
    protected RemoteWebElement headersPanel;
    @WindowsFindBy(accessibility = "DocumentHost")
    private RemoteWebElement rootWindowSelector;
    @WindowsFindBy(accessibility = "TabHeadersPanel")
    private RemoteWebElement tabHeadersPanel;

    private String closeButtonSelector = "ControlBoxButtonPresenter";

    public BaseWorkSpace(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        this.WorkSpaceWindow = rootWindowSelector;
        this.numberHeaders = tabHeadersPanel.findElementsByClassName(closeButtonSelector).size();
    }

    @WindowsFindBy(accessibility = "dataPresenter")
    private RemoteWebElement dataTable;
    private String dataString = "System.Data.DataRowView";
    public void checkDataEditor(){
        List<WebElement> list = dataTable.findElementsByName(dataString);
        assertTrue(list.size() != 0, "Data is empty");
    }

    @WindowsFindBy(accessibility = "PART_CloseButton")
    private List<RemoteWebElement> closeButtons;
    public void compareCountHeaders() {
        int count = tabHeadersPanel.findElementsByClassName(closeButtonSelector).size();
        assertThat("New tab not opened",count, not(equalTo(numberHeaders)));
        if (numberHeaders != count) {
            numberHeaders = count;
        }
    }
}
