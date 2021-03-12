package ru.geonaft.modules.loader;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.modules.loader.previewFilds.PreviewFieldsSelector;
import ru.geonaft.modules.loader.treeProjectLoader.TreeProjectLoader;
import ru.geonaft.modules.pp.ribbonPP.RibbonPP;
import ru.geonaft.modules.pp.treeProjectPP.TreeProjectPP;
import ru.geonaft.modules.pp.workSpacePP.WorkSpacePP;
import ru.geonaft.view.ribbon.BaseRibbon;
import ru.geonaft.view.ribbon.buttonsSelector.ModuleSelector;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;
import ru.geonaft.view.workSpaces.editor.Editor;
import ru.geonaft.view.workSpaces.workSpace.BaseWorkSpace;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.geonaft.Base.Appointment.PRIMARY;
import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.NameEntityToProject.patternInProject;
import static ru.geonaft.modules.loader.previewFilds.PreviewFieldsSelector.*;
import static ru.geonaft.modules.loader.previewFilds.PreviewFieldsSelector.ENTITY_ID;

public class Loader extends Base implements OpenModule {

    public BaseRibbon ribbon;
    public TreeProjectLoader treeProject;
    public BaseWorkSpace workSpace;
    public Editor editor;

    public Loader(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        ribbon = new BaseRibbon(driver);
        treeProject = new TreeProjectLoader(driver);
    }

    private String loaderWindowSelector = "ЗАГРУЗЧИК ДАННЫХ";
    private RemoteWebElement loaderWindow;

    @Step("Open loader")
    @Override
    public Loader openModule() {
        ribbon
                .openModule(ModuleSelector.LOADER);
        assertThat("Loader window was not open",
                windowsElement
                        .findElementsByName(loaderWindowSelector)
                        .size(),
                equalTo(1)
        );
        this.loaderWindow =
                (RemoteWebElement) windowsElement
                        .findElementByName(loaderWindowSelector);
        return this;
    }

    private String openFileButtonSelector = "Открыть файл";

    public Loader openFileForPreview(String from, String fileName) {
        loaderWindow
                .findElementByName(openFileButtonSelector).click();
        baseAction
                .loadFile(from, fileName)
                .waitLoading(loaderWindow);
        return this;
    }

    public String getNameByIdFromPreview(PreviewFieldsSelector id) {
        return driver.findElementByAccessibilityId(id.selector).getText();
    }

    private String comboBox = "ComboBox";

    public String getNameBySelectorFromPreview(PreviewFieldsSelector selector) {
        return loaderWindow.findElementByName(selector.selector)
                .findElement(By.className(comboBox))
                .getText();
    }

    public Loader checkRowTablePreview(PreviewFieldsSelector selector) {
        List<WebElement> list =
                loaderWindow
                        .findElementsByName(selector.selector);
        assertThat("The data is not displayed in the preview", list.size(), not(equalTo(0)));
        return this;
    }

    public Loader doPreview(SubFolderSelector entity) {
        switch (entity) {
            case LOG:
                wellInProject.setName(getNameByIdFromPreview(PreviewFieldsSelector.WELL_ID));
                logInProject.setName(getNameBySelectorFromPreview(LOG_SELECTOR));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, wellInProject.name, PRIMARY);
                assertThat("Field with log name is empty", logInProject, is(notNullValue()));
                checkRowTablePreview(CURVE_SELECTOR);
                break;
            case TRAJECTORY:
                wellInProject.setName(getNameByIdFromPreview(WELL_ID));
                trajectoryInProject.setName(getNameByIdFromPreview(TRAJECTORY_ID));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, wellInProject.name, PRIMARY);
                assertThat("Field with trajectory name is empty", trajectoryInProject, is(notNullValue()));
                break;
            case SURFACE:
                surfaceInProject.setName(getNameByIdFromPreview(SURFACE_ID));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, surfaceInProject.name, PRIMARY);
                assertThat("Field with surface name is empty", surfaceInProject, is(notNullValue()));
                break;
            case IMAGE:
                wellInProject.setName(getNameByIdFromPreview(WELL_ID));
                imageInProject.setName(getNameByIdFromPreview(IMAGE_ID));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, wellInProject.name, PRIMARY);
                assertThat("Field with image name is empty", imageInProject, is(notNullValue()));
                checkRowTablePreview(SECTOR_SELECTOR);
                break;
            case POLYGON:
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, "Polygon", PRIMARY);
                checkRowTablePreview(SURFACE_SELECTOR);
                break;
            case PICTURE:
                pictureInProject.setName(getNameByIdFromPreview(ENTITY_ID));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, pictureInProject.name, PRIMARY);
                assertThat("Field with picture name is empty", pictureInProject, is(notNullValue()));
                break;
            case PALETTE:
                paletteInProject.setName(getNameByIdFromPreview(ENTITY_ID));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, paletteInProject.name, PRIMARY);
                assertThat("Field with palette name is empty", paletteInProject, is(notNullValue()));
                break;
            case PATTERN:
                patternInProject.setName(getNameByIdFromPreview(ENTITY_ID));
                baseAction.takeScreenshotToAttachOnAllureReport(loaderWindow, patternInProject.name, PRIMARY);
                assertThat("Field with pattern name is empty", patternInProject, is(notNullValue()));
                break;
        }
        return this;
    }

    private String loadButtonSelector = "Загрузить";

    public Loader clickLoad() {
        RemoteWebElement loaderButton =
                (RemoteWebElement) loaderWindow
                        .findElementByName(loadButtonSelector);
        assertThat("Loader button is not active", loaderButton.getAttribute(enableButtonAttribute), is(equalTo("True")));
        loaderButton.click();
        baseAction.waitLoading(loaderWindow);
        return this;
    }

    public Loader renameFieldPreview(SubFolderSelector entity) {
        switch (entity) {
            case TRAJECTORY:
                driver
                        .findElementByAccessibilityId(WELL_ID.selector)
                        .click();
                baseAction
                        .copyInBuffer(faker.superhero().name())
                        .pastFromBuffer()
                        .enterClick();
                driver
                        .findElementByAccessibilityId(TRAJECTORY_ID.selector)
                        .click();
                baseAction
                        .ctr_A()
                        .copyInBuffer(faker.superhero().name())
                        .pastFromBuffer();
                break;
        }
        return this;
    }

    public Loader changeIndefiniteValueInPreview(String screenName) {
        RemoteWebElement textValue =
                (RemoteWebElement) loaderWindow
                        .findElementByClassName(textBlockSelector);
        String value = textValue.getText();
        textValue.click();
        baseAction
                .copyInBuffer(((Integer) faker.number().numberBetween(-9999, 9999)).toString())
                .pastFromBuffer();
        String newValue = textValue.getText();
        assertThat("Value in the field was not updated", value, not(equalTo(newValue)));
        return this;
    }

    @WindowsFindBy(accessibility = "CloseButton")
    private RemoteWebElement closeButton;
    private String textBlockSelector = "TextBox";
    private String exitButtonSelector = "Закрыть";

    @Step("Check work preview window")
    public void checkWorkPreviewWindow(String screenName) {
        changeIndefiniteValueInPreview(screenName);
        closeButton.click();
        openModule();
        loaderWindow
                .findElementByName(exitButtonSelector)
                .click();
    }

    @Step("Uploading to the project - {what}")
    public Loader loadEntity(String from, String fileName, SubFolderSelector what) {
        openFileForPreview(from, fileName);
        doPreview(what);
        clickLoad();
        return this;
    }

    @Step("Checking data in the folder")
    public Loader checkLoadInTreeProject(SubFolderSelector subFolder) {
        treeProject
                .checkDataFolder(subFolder);
        return this;
    }

    @Step("Open editor loaded file")
    public Loader openEditorLoadedFile(SubFolderSelector what) {
        treeProject
                .openEditorFromContext(what);
        workSpace
                .compareCountHeaders();
        return this;
    }



}
