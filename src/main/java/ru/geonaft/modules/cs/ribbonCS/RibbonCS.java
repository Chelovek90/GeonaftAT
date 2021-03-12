package ru.geonaft.modules.cs.ribbonCS;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.ribbon.BaseRibbon;
import ru.geonaft.view.ribbon.buttonsSelector.TabSelector;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.geonaft.NameEntityToProject.*;

public class RibbonCS extends BaseRibbon {

    private RemoteWebElement well;
    private int actualWellIndex;
    private int refWellIndex;

    public RibbonCS(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }

    public void clickCSTabHeader() {
        clickRibbonTab(TabSelector.CS);
    }

    @WindowsFindBy(accessibility = "beActualWell")
    private RemoteWebElement actualWellField;

    @WindowsFindBy(accessibility = "PART_Content")
    private RemoteWebElement listBox;
    private String wellBoxSelector = "ListBoxItem";

    public RibbonCS chooseActualWell() {
        clickCSTabHeader();

        WebElement actualWellField = modulesGroups
                .get(0)
                .findElements(By.className("ComboBoxEdit"))
                .get(0);

        actualWellField.click();
        List<WebElement> listWells = listBox
                .findElementsByClassName(wellBoxSelector);
        actualWellIndex = random.nextInt(listWells.size());
        well = (RemoteWebElement) listWells
                .get(actualWellIndex);
        actualWellInProject.setName(baseAction.getFileName(well));
        well.click();
        assertThat("Name actual well in field does not match", actualWellInProject.name, is(equalTo(actualWellField.getText())));
        return this;
    }

    @WindowsFindBy(accessibility = "biOffsetWell")
    private RemoteWebElement refWellField;

    public RibbonCS chooseRefWell() {
        clickCSTabHeader();

        WebElement refWellField = modulesGroups
                .get(0)
                .findElements(By.className("ComboBoxEdit"))
                .get(1);

        refWellField.click();
        List<WebElement> listWells = listBox
                .findElementsByClassName(wellBoxSelector);
        refWellIndex = actualWellIndex;
        while (refWellIndex == actualWellIndex) {
            refWellIndex = random.nextInt(listWells.size());
        }
        well = (RemoteWebElement) listWells
                .get(refWellIndex);
        refWellInProject.setName(baseAction.getFileName(well));
        well.click();
        assertThat("Name actual well in field does not match", refWellInProject.name, is(equalTo(refWellField.getText())));
        return this;
    }

    private String ribbonToggleButtonSelector = "RibbonToggleButton";
    private String ribbonButtonSelector = "RibbonButton";
    private String activitySelector = "IsEnabled";

    public RibbonCS checkActivityRibbonButton() {
        ribbonPanel.findElementsByClassName(ribbonToggleButtonSelector).stream()
                .filter(button -> !(button.getText().equals("Отклонение от плана")))
                .filter(button -> !(button.getText().equals("Deviation from the plan")))
                .forEach(button -> {
                    assertThat("Button - " + button.getText() + " is not active",
                            button.getAttribute(activitySelector), is(equalTo("True")));
                });
        ribbonPanel.findElementsByClassName(ribbonButtonSelector).stream()
                .forEach(button -> {
                    assertThat("Button - " + button.getText() + " is not active",
                            button.getAttribute(activitySelector), is(equalTo("True")));
                });
        return this;
    }

    private String buttonClassName = "RibbonControl";

    public RibbonCS clickInstrument(InstrumentsCsSelector instrument) {
        modulesGroups.get(instrument.indexGroup)
                .findElements(By.className(buttonClassName))
                .get(instrument.indexButton)
                .click();
        return this;
    }
}
