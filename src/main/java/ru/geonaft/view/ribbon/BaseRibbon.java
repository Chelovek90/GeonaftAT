package ru.geonaft.view.ribbon;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.view.ribbon.buttonsSelector.ModuleSelector;
import ru.geonaft.view.ribbon.buttonsSelector.TabSelector;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class BaseRibbon extends Base {

    protected RemoteWebElement ribbonPanel;
    @WindowsFindBy(accessibility = "RibbonGeo")
    private RemoteWebElement ribbonPanelSelector;

    protected List<WebElement> modulesGroups;

    public BaseRibbon(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        this.ribbonPanel = ribbonPanelSelector;
    }

    private String tabButtonSelector = "RibbonTabHeader";
    private String groupSelector = "RibbonGroup";
    private String isSelectedSelector = "SelectionItem.IsSelected";

    public BaseRibbon clickRibbonTab(TabSelector selector) {
        RemoteWebElement ribbonTab = (RemoteWebElement) ribbonPanel
                .findElementByClassName(selector.tabSelector);
        if (ribbonTab.getAttribute(isSelectedSelector).equals("False")) {
            ribbonTab.findElementByClassName(tabButtonSelector).click();
        }
        this.modulesGroups = ribbonTab
                .findElementsByClassName(groupSelector);
        assertThat("Groups modules in ribbon not found", modulesGroups, is(not(empty())));
        return this;
    }

    public void openModule(ModuleSelector moduleSelector) {
        clickRibbonTab(TabSelector.PROJECT);
        modulesGroups.get(moduleSelector.indexGroup)
                .findElement(By.name(moduleSelector.moduleSelector))
                .click();
    }

    private String fileButton = "RibbonApplicationMenu";
    @WindowsFindBy(accessibility = "bCloseProject")
    private RemoteWebElement closeProjectButton;
    @WindowsFindBy(accessibility = "No")
    private RemoteWebElement noButton;

    public BaseRibbon closeProject() {
        ribbonPanel.findElementByClassName(fileButton).click();
        closeProjectButton.click();
        noButton.click();
        return this;
    }

    private String stateButton = "Toggle.ToggleState";
    public BaseRibbon checkOnOffButton(RemoteWebElement button) {
        int primaryState = Integer.parseInt(
                button.getAttribute(stateButton));
        return this;
    }
}
