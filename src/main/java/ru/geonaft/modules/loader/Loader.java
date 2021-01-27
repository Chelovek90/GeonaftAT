package ru.geonaft.modules.loader;

import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.BaseAction;
import ru.geonaft.modules.loader.previewFields.FieldSelector;
import ru.geonaft.modules.loader.previewFields.FieldSelectorImpl;
import ru.geonaft.view.ribbone.Ribbon;
import ru.geonaft.view.ribbone.modulesSelector.ModuleSelector;
import ru.geonaft.view.treeProject.TreeProject;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;

import java.util.ArrayList;

public class Loader extends BaseAction implements OpenModule{

    private String wellName;
    private String logName;
    private String surfaceName;
    private String imageName;

    public Loader getTree() {
        this.treeProject = new TreeProject(driver);
        return this;
    }

    public Loader getRibbon() {
        this.ribbon = new Ribbon(driver);
        return this;
    }

    private RemoteWebElement loaderWindow;

    public Loader(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }

    private String loaderWindowSelector = "ЗАГРУЗЧИК ДАННЫХ";

    @Step("Open loader")
    @Override
    public Loader openModule() {
        getRibbon();
        ribbon.openModule(ModuleSelector.LOADER);
        this.loaderWindow = (RemoteWebElement) windowsElement.findElementByName(loaderWindowSelector);
        return this;
    }

    public String getNameById(String id) {
        return driver.findElementByAccessibilityId(id).getText();
    }

    public String getNameBySelector(String selector) {
        return loaderWindow.findElementByName(selector).getText();
    }

    public Loader preview (SubFolderSelector entity) {
        FieldSelector well;
        switch (entity) {
            case WELL:
                well = new FieldSelectorImpl()
                        .setWellFieldId("1")
                        .setLogFieldId("2")
                        .setCurveFieldId("3")
                        .build();
                this.wellName = getNameById(well.getWellFieldId());
                this.logName = getNameById(well.getLogFieldId());
        }

        return this;
    }



    private String openFileButtonSelector = "Открыть файл";
    private String loadButtonSelector = "Загрузить";

    @Step("Loading entity - ")
    public Loader loadEntity(String path, String fileName) {
        this.entityName = new ArrayList<>();
        loaderWindow.findElementByName(openFileButtonSelector).click();
        loadFile(path, fileName);

        loaderWindow.findElementByName(loadButtonSelector).click();
        waitLoading();
        return this;
    }
}
