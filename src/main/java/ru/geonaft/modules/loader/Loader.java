package ru.geonaft.modules.loader;

import com.github.javafaker.Faker;
import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.NameEntityToProject;
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
import static ru.geonaft.Base.Appointment.PRIMARY;
import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.modules.loader.previewFilds.PreviewFieldsSelector.*;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.PICTURES;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.POLYGONS;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.PICTURE;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.POLYGON;

public class Loader extends Base implements OpenModule{

    Faker faker = new Faker();

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
                logInProject.setName(getNameBySelectorFromPreview(LOG_SELECTOR));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, wellInProject.name , PRIMARY);
                assertThat("Field with log name is empty", logInProject, is(notNullValue()));
                checkDataPreview(CURVE_SELECTOR);
                break;
            case TRAJECTORY:
                wellInProject.setName(getNameByIdFromPreview(WELL_ID));
                trajectoryInProject.setName(getNameByIdFromPreview(TRAJECTORY_ID));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, wellInProject.name , PRIMARY);
                assertThat("Field with trajectory name is empty", trajectoryInProject, is(notNullValue()));
                checkDataPreview(CURVE_SELECTOR);
                break;
            case SURFACE:
                surfaceInProject.setName(getNameByIdFromPreview(SURFACE_ID));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, surfaceInProject.name , PRIMARY);
                assertThat("Field with surface name is empty", surfaceInProject, is(notNullValue()));
                break;
            case IMAGE:
                wellInProject.setName(getNameByIdFromPreview(WELL_ID));
                imageInProject.setName(getNameByIdFromPreview(IMAGE_ID));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, wellInProject.name , PRIMARY);
                assertThat("Field with image name is empty", imageInProject, is(notNullValue()));
                checkDataPreview(SECTOR_SELECTOR);
                break;
            case POLYGON:
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, "Polygon" , PRIMARY);
                checkDataPreview(SURFACE_SELECTOR);
                break;
            case PICTURE:
                pictureInProject.setName(getNameByIdFromPreview(ENTITY_ID));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, pictureInProject.name , PRIMARY);
                assertThat("Field with picture name is empty", pictureInProject, is(notNullValue()));
                break;
            case PALETTE:
                paletteInProject.setName(getNameByIdFromPreview(ENTITY_ID));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, paletteInProject.name , PRIMARY);
                assertThat("Field with palette name is empty", paletteInProject, is(notNullValue()));
                break;
        }
        return this;
    }

    public Loader renameFieldPreview(SubFolderSelector entity) {
        switch (entity){
            case TRAJECTORY:
                driver.findElementByAccessibilityId(WELL_ID.value).click();
                baseAction.copyInBuffer(faker.superhero().name());
                baseAction.pastFromBuffer();
                baseAction.enterClick();
                driver.findElementByAccessibilityId(TRAJECTORY_ID.value).click();
                baseAction.ctr_A();
                baseAction.copyInBuffer(faker.superhero().name());
                baseAction.pastFromBuffer();
                break;
        }
        return this;
    }

    private void loadFileFromWindow(String from, String fileName) {
        loaderWindow.findElementByName(openFileButtonSelector).click();
        baseAction.loadFile(from, fileName);
        baseAction.waitLoading(loaderWindow);
    }

    private void loadFileToProject() {
        RemoteWebElement loaderButton = (RemoteWebElement) loaderWindow.findElementByName(loadButtonSelector);
        assertThat(loaderButton.getAttribute(IsEnabledSelector), is(equalTo("True")));
        loaderButton.click();
        baseAction.waitLoading(loaderWindow);
    }


    private String openFileButtonSelector = "Открыть файл";
    private String loadButtonSelector = "Загрузить";
    private String IsEnabledSelector = "IsEnabled";

    @Step("Uploading to the project - {what}")
    public Loader loadEntity(String from, String fileName, SubFolderSelector what) {
        loadFileFromWindow(from, fileName);
        doPreview(what);
        loadFileToProject();
        return this;
    }


    @Step("Uploading to the project - {what}")
    public Loader loadEntityWithRename(String from, String fileName, SubFolderSelector what) {
        loadFileFromWindow(from, fileName);
        renameFieldPreview(what);
        doPreview(what);
        loadFileToProject();
        return this;
    }

    public Loader openEditorLoadedFile(SubFolderSelector what) {
        treeProject.openEditorFromContext(what);
        return this;
    }

    @Step("Checking the uploaded data in the data editor")
    public Loader checkDataInEditor(NameEntityToProject entity) {
        workSpace.checkDataEditor(entity.name);
        workSpace.closeFirstTab();
        return this;
    }

    @Step("Checking data in the folder")
    public Loader checkDataFolder(SubFolderSelector subFolder) {
        treeProject.checkDataFolder(subFolder);
        return this;
    }

    @WindowsFindBy(accessibility = "CloseButton")
    private RemoteWebElement closeButton;
    private String textBlockSelector = "TextBox";
    private String exitButtonSelector = "Закрыть";
    public void checkWorkElementsPreviewWindow() {
        RemoteWebElement textValue = (RemoteWebElement) loaderWindow.findElementByClassName(textBlockSelector);
        String value = textValue.getText();
        textValue.click();
        baseAction.copyInBuffer( ((Integer)faker.number().numberBetween(-9999, 9999)).toString());
        baseAction.pastFromBuffer();
        String newValue = textValue.getText();
        baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, "firstStatePreview", PRIMARY);
        assertThat("Value in the field was not updated", value, not(equalTo(newValue)));
        closeButton.click();
        openModule();
        baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, "secondStatePreview", PRIMARY);
        loaderWindow.findElementByName(exitButtonSelector).click();
    }
}
