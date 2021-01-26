package ru.geonaft.view.ribbone;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.BaseAction;
import ru.geonaft.view.ribbone.modulesSelector.ModuleSelector;
import ru.geonaft.view.ribbone.modulesSelector.TabSelector;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;

public class Ribbon extends BaseAction {

    protected RemoteWebElement ribbonPanel;
    @WindowsFindBy(accessibility = "RibbonGeo")
    private RemoteWebElement ribbonPanelSelector;

    protected List<WebElement> modulesGroups;

    public Ribbon(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        this.ribbonPanel = ribbonPanelSelector;
    }

    private String tabButtonSelector = "RibbonTabHeader";
    private String groupSelector = "RibbonGroup";

    public void clickRibbonTab(TabSelector selector) {
        RemoteWebElement ribbonTab = (RemoteWebElement) ribbonPanel
                .findElementByClassName(selector.tabSelector);
        if (ribbonTab.getAttribute("SelectionItem.IsSelected").equals("False")) {
            RemoteWebElement ribbonButtonTab = (RemoteWebElement) ribbonTab
                    .findElementByClassName(tabButtonSelector);
            ribbonButtonTab.click();
        }
        this.modulesGroups = ribbonTab
                .findElementsByClassName(groupSelector);
        assertThat("Groups modules in ribbon not found", modulesGroups, is(not(empty())));
    }

    public void openModule(ModuleSelector moduleSelector) {
        clickRibbonTab(TabSelector.PROJECT);
        modulesGroups.get(moduleSelector.indexGroup).findElement(By.name(moduleSelector.moduleSelector)).click();

    }
}
