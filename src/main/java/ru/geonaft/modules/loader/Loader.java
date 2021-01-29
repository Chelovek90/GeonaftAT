package ru.geonaft.modules.loader;

import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.BaseAction;
import ru.geonaft.modules.loader.previewFilds.PreviewFieldsSelector;
import ru.geonaft.view.ribbone.Ribbon;
import ru.geonaft.view.ribbone.modulesSelector.ModuleSelector;
import ru.geonaft.view.treeProject.TreeProject;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.geonaft.modules.loader.previewFilds.PreviewFieldsSelector.*;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.SURFACES;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.WELLS;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.*;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.LOGS;

public class Loader extends BaseAction implements OpenModule{

    private String wellName;
    private String logName;
    private String logs;
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

    public Loader getWorkSpace() {
        this.workSpace = new BaseWorkSpace(driver);
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
//        getRibbon();
        ribbon.openModule(ModuleSelector.LOADER);
        this.loaderWindow = (RemoteWebElement) windowsElement.findElementByName(loaderWindowSelector);
        return this;
    }

    public String getNameByIdFromPreview(PreviewFieldsSelector id) {
        return driver.findElementByAccessibilityId(id.value).getText();
    }

    private String comboBox = "ComboBox";
    public String getNameBySelectorFromPreview(PreviewFieldsSelector selector) {
        return loaderWindow.findElementByName(selector.value)
                .findElement(By.className(comboBox))
                .getText();
    }

    public void checkDataPreview(PreviewFieldsSelector selector) {
        List<WebElement> list = loaderWindow.findElementsByName(selector.value);
        assertThat("The data is not displayed in the preview",list, is(notNullValue()));
    }

    public Loader doPreview(SubFolderSelector entity) {
        switch (entity){
            case LOG:
                this.wellName = getNameByIdFromPreview(PreviewFieldsSelector.WELL_ID);
                this.logName = getNameBySelectorFromPreview(PreviewFieldsSelector.LOG_SELECTOR);
                checkDataPreview(CURVE_SELECTOR);
                break;
            case SURFACE:
                this.surfaceName = getNameByIdFromPreview(SURFACE_ID);
                break;
            case IMAGE:
                this.imageName = getNameByIdFromPreview(IMAGE_ID);
                break;
        }
        return this;
    }

    private String openFileButtonSelector = "Открыть файл";
    private String loadButtonSelector = "Загрузить";

    @Step("Uploading to the project - {what}")
    public Loader loadEntity(String from, String fileName, SubFolderSelector what) {
        this.entityName = new ArrayList<>();
        loaderWindow.findElementByName(openFileButtonSelector).click();
        loadFile(from, fileName);
        doPreview(what);
        RemoteWebElement loaderButton = (RemoteWebElement) loaderWindow.findElementByName(loadButtonSelector);
        assertThat(loaderButton.getAttribute("IsEnabled"), is(equalTo("True")));
        loaderButton.click();
        waitLoading();
        return this;
    }

    public Loader openEditorLoadedFile(SubFolderSelector what) {
//        getTree();
        switch (what){
            case LOG:
                treeProject
                        .unfoldFolder(WELLS)
                        .unfoldFolder(WELL, wellName)
                        .unfoldFolder(LOGS)
                        .searchElementByName(LOG, logName)
                        .openEditorTargetFolder();
                clickOkInAttention();
                break;
            case SURFACE:
                treeProject
                        .unfoldFolder(SURFACES)
                        .searchElementByName(SURFACE, surfaceName)
                        .openEditorTargetFolder();
        }
        workSpace.compareCountHeaders();
        return this;
    }

    @Step("Checking the uploaded data in the data editor")
    public Loader checkDataInEditor() {
//        getWorkSpace();
        workSpace.checkDataEditor();
        return this;
    }
}
