package ru.geonaft.modules.loader;

import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.helpers.BaseAction;
import ru.geonaft.modules.loader.previewFilds.PreviewFieldsSelector;
import ru.geonaft.view.ribbone.Ribbon;
import ru.geonaft.view.ribbone.modulesSelector.ModuleSelector;
import ru.geonaft.view.treeProject.TreeProject;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.modules.loader.previewFilds.PreviewFieldsSelector.*;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.PICTURES;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.PICTURE;

public class Loader extends Base implements OpenModule{

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
        this.baseAction = new BaseAction(driver);
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
                wellInProject.setName(getNameByIdFromPreview(PreviewFieldsSelector.WELL_ID));
                logInProject.setName(getNameBySelectorFromPreview(PreviewFieldsSelector.LOG_SELECTOR));
                assertThat("Field with log name is empty", logInProject, is(notNullValue()));
                checkDataPreview(CURVE_SELECTOR);
                break;
            case SURFACE:
                surfaceInProject.setName(getNameByIdFromPreview(SURFACE_ID));
                assertThat("Field with surface name is empty", surfaceInProject, is(notNullValue()));
                break;
            case IMAGE:
                wellInProject.setName(getNameByIdFromPreview(PreviewFieldsSelector.WELL_ID));
                imageInProject.setName(getNameByIdFromPreview(IMAGE_ID));
                assertThat("Field with image name is empty", imageInProject, is(notNullValue()));
                checkDataPreview(SECTOR_SELECTOR);
                break;
            case POLYGON:
                checkDataPreview(SURFACE_SELECTOR);
                break;
            case PICTURE:
                pictureInProject.setName(getNameByIdFromPreview(PICTURE_ID));
                assertThat("Field with picture name is empty", pictureInProject, is(notNullValue()));
                break;
        }
        return this;
    }

    private String openFileButtonSelector = "Открыть файл";
    private String loadButtonSelector = "Загрузить";

    @Step("Uploading to the project - {what}")
    public Loader loadEntity(String from, String fileName, SubFolderSelector what) {
        loaderWindow.findElementByName(openFileButtonSelector).click();
        baseAction.loadFile(from, fileName);
        baseAction.waitLoading();
        doPreview(what);
        RemoteWebElement loaderButton = (RemoteWebElement) loaderWindow.findElementByName(loadButtonSelector);
        assertThat(loaderButton.getAttribute("IsEnabled"), is(equalTo("True")));
        loaderButton.click();
        baseAction.waitLoading();
        return this;
    }

    public Loader openEditorLoadedFile(SubFolderSelector what) {
//        getTree();
        treeProject.openEditorFromContext(what);
        return this;
    }

    @Step("Checking the uploaded data in the data editor")
    public Loader checkDataInEditor(String name) {
//        getWorkSpace();
        workSpace.checkDataEditor(name);
        return this;
    }

    @Step("Checking data in the folder")
    public Loader checkDataFolder(SubFolderSelector subFolder) {
        switch (subFolder){
            case PICTURE:
                treeProject
                        .unfoldFolder(PICTURES)
                        .searchElementByName(PICTURE, pictureInProject.name);
                break;
        }
        return this;
    }
}
