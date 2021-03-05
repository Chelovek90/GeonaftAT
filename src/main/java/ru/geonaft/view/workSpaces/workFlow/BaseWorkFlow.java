package ru.geonaft.view.workSpaces.workFlow;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.geonaft.NameEntityToProject.*;

public class BaseWorkFlow extends Base {

    protected int numberHeaders;

    protected RemoteWebElement well;

    @WindowsFindBy(accessibility = "Workflow")
    private RemoteWebElement mainViewID;
    protected RemoteWebElement mainView;

    private String headersPanelSelector = "TabHeadersPanel";
    protected RemoteWebElement headersPanel;

    private String headersSelector = "DocumentPaneItem";

    private String workflowSelector = "DocumentPanel";
    protected RemoteWebElement workflow;

    private String panelMenuSelector = "NavPaneActiveGroupControl";
    protected RemoteWebElement panelMenu;

    private String optionViewSelector = "DataWorkflowView";
    protected RemoteWebElement optionView;


    public BaseWorkFlow(WindowsDriver<RemoteWebElement> driver) {
        super(driver);

        this.headersPanel =
                (RemoteWebElement) mainViewID
                        .findElementByClassName(headersPanelSelector);

        this.numberHeaders = headersPanel
                .findElementsByClassName(headersSelector)
                .size();
        assertThat("Workflow module was not open", numberHeaders, equalTo(1));

        this.panelMenu =
                mainViewID
                        .findElementByClassName(workflowSelector)
                        .findElement(By.className(panelMenuSelector));

        this.optionView =
                mainViewID
                        .findElementByClassName(workflowSelector)
                        .findElement(By.className(optionViewSelector));
    }

    public BaseWorkFlow compareCountHeaders() {
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

    public BaseWorkFlow closeAllTab() {
        headersPanel
                .findElementsByClassName(closeTabButtonSelector)
                .stream()
                .forEach(closebtn -> closebtn.click());
        return this;
    }

    private String headersName = "TabCaptionControl";
    private String nameAttribute = "Name";

    public BaseWorkFlow checkTabNameWorkflowHeaders(String wellName, String logName) {
        String tabName = headersPanel
                .findElementByClassName(headersSelector)
                .findElement(By.className(headersName))
                .getAttribute(nameAttribute);
        System.out.println(tabName);
        System.out.println(wellInProject.name);
        System.out.println(logInProject.name);
        assertThat("Tab name does not contains well name - " + wellName, tabName, containsString(wellName));
        assertThat("Tab name does not contains log name - " + logName, tabName, containsString(logName));
        return this;
    }

    private String listWellsNameSelector = "Geosteering.Core.Infrastructure.ViewModels.EntityInfoViewModel`1[System.Guid]";
    private String listWellsClassSelector = "ListBoxItem";
    private String entityNameSelector = "TextBlock";
    public BaseWorkFlow chooseRandomEntityFromList() {
        List<RemoteWebElement> entity = driver
                .findElementsByName(listWellsNameSelector);

        System.out.println(entity.size());

        well = entity.get(2 + random.nextInt((entity.size() - 2)));

        well.click();
        return this;
    }


    public BaseWorkFlow chooseWellForModule() {
        chooseRandomEntityFromList();

        wellInProject.setName(well
                        .findElementByClassName(entityNameSelector)
                        .getText());

        return this;
    }

    public BaseWorkFlow chooseLogForModule() {
        chooseRandomEntityFromList();

        logInProject.setName(well.getText());

        return this;
    }

    protected String applyButtonSelector = "Button";
    public BaseWorkFlow clickApplyButton() {
        optionView
                .findElementByClassName(applyButtonSelector)
                .click();
        return this;
    }



}
