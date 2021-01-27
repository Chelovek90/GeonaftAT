package ru.geonaft.modules.loader;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.BaseAction;
import ru.geonaft.view.ribbone.Ribbon;
import ru.geonaft.view.ribbone.modulesSelector.ModuleSelector;
import ru.geonaft.view.treeProject.TreeProject;

import java.util.ArrayList;
import java.util.List;

public class Loader extends BaseAction implements OpenModule{

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

    public Loader preview() {

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
