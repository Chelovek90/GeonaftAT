package ru.geonaft.view.workSpaces.workFlow;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.modules.pp.workFlowPP.WorkflowPP;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.geonaft.NameEntityToProject.*;

public class BaseWorkFlow extends Base {

    protected int numberHeaders;

    protected RemoteWebElement well;

    @WindowsFindBy(accessibility = "Workflow")
    protected RemoteWebElement mainViewID;

    private String headersPanelSelector = "TabHeadersPanel";
    protected RemoteWebElement headersPanel;

    private String headersSelector = "DocumentPaneItem";

    protected String workflowSelector = "DocumentPanel";

    private String panelMenuSelector = "NavPaneActiveGroupControl";
    protected RemoteWebElement panelMenu;

    private String optionViewSelector = "ScrollViewer";
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

        setOptionViewForStepWorkflow();
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

    public BaseWorkFlow setOptionViewForStepWorkflow() {
        this.optionView =
                mainViewID
                        .findElementByClassName(workflowSelector)
                        .findElement(By.className(optionViewSelector));
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

    public BaseWorkFlow checkEnableApplyButton() {
        assertThat("Apply button is enabled",
                optionView
                        .findElementByClassName(applyButtonSelector)
                        .getAttribute(enableButtonAttribute),
                equalTo("False"));
        return this;
    }

    public BaseWorkFlow waitLoading() {
        baseAction
                .waitLoading(
                        (RemoteWebElement) mainViewID
                                .findElementByClassName(workflowSelector)
                );
        return this;
    }

    public BaseWorkFlow primitiveChooseFromComboBox(RemoteWebElement comboBox) {
        comboBox.click();
        actions.moveToElement(comboBox, 20, 35)
                .click()
                .build()
                .perform();
        return this;
    }

    public List<String> getAllTextFromOptionView(RemoteWebElement view) {
        return view
                .findElementsByClassName(textBlockSelector)
                .stream()
                .map(element -> element.getText())
                .distinct()
                .filter(text -> !text.trim().isEmpty())
                .sorted()
                .collect(Collectors.toList());
    }

    private String buttonMenuSelector = "NavBarItemControl";

    public List<String> getAllStepsNameWorkflow() {
        return panelMenu
                .findElementsByClassName(buttonMenuSelector)
                .stream()
                .map(element -> element.getText())
                .sorted()
                .collect(Collectors.toList());
    }

}
