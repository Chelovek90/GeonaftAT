package ru.geonaft.modules.CS.ribbon;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.ribbon.BaseRibbon;
import ru.geonaft.view.ribbon.modulesSelector.TabSelector;

import java.util.List;

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
//    @FindBy(className = "ListBoxItem")
    private String wellBoxSelector = "ListBoxItem";

    public void chooseActualWell() {
        clickCSTabHeader();
        actualWellField.click();
        List<WebElement> listWells = listBox
                .findElementsByClassName(wellBoxSelector);
        actualWellIndex = random.nextInt(listWells.size());
        well = (RemoteWebElement) listWells
                .get(actualWellIndex);
        actualWellInProject.setName(baseAction.getFileName(well));
        well.click();
    }

    @WindowsFindBy(accessibility = "biOffsetWell")
    private RemoteWebElement refWellField;
    public void chooseRefWell() {
        clickCSTabHeader();
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
    }

}
