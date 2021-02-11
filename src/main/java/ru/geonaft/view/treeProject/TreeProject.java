package ru.geonaft.view.treeProject;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import ru.geonaft.Base;
import ru.geonaft.NameEntityToProject;
import ru.geonaft.helpers.BaseAction;
import ru.geonaft.view.treeProject.selectors.RootFolderSelector;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.*;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.*;

public class TreeProject extends Base {

    private WebElement targetFolder;

    private RemoteWebElement treeProjectWindow;

    @WindowsFindBy(accessibility = "TreeView")
    private RemoteWebElement treeProjectSelector;

    public TreeProject(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        this.treeProjectWindow = treeProjectSelector;
    }

    private String nameField = "TextBlock";
    private String attributeName = "Name";
    public String getFolderName(RemoteWebElement element) {
        String fileName;
        fileName = element.findElementByClassName(nameField).getAttribute(attributeName);
        return fileName;
    }

    protected String condition = "ExpandCollapse.ExpandCollapseState";

    public boolean checkExpander(RemoteWebElement element) {
        String expanderCondition = element.getAttribute(condition);
        boolean collapsed = false;
        switch (expanderCondition){
            case "LeafNode":
                assertTrue(collapsed, "Block " + getFolderName(element) + " is empty");
                break;
            case "Expanded":
                break;
            case "Collapsed":
                collapsed = true;
                break;
        }
        return collapsed;
    }

    private String clickablePoint = "TextBlock";
    public RemoteWebElement searchElementByName(SubFolderSelector folder, String folderName) {
        List<WebElement> list = rootTreeFolder.findElementsByName(folder.folderSelector);
        targetFolder = list.stream()
                .filter(entity -> entity.findElement(By.className(clickablePoint)).getText().equals(folderName))
                .findFirst().orElse(null);
        assertTrue(targetFolder != null, "Search element by name " + folderName + " returned no results");
        return (RemoteWebElement) targetFolder;
    }

    @FindBy(className = "ContextMenu")
    private RemoteWebElement contextMenu;
    private String menuItem = "MenuItem";
    @WindowsFindBy(accessibility = "OKButton")
    protected RemoteWebElement okButton;

    public void openEditorFromContextMenu(RemoteWebElement element) {
        baseAction.rightClick(element);
        List<WebElement> items = contextMenu.findElementsByClassName(menuItem);
        items.get(0).click();
    }

    private RemoteWebElement rootTreeFolder;
    public TreeProject unfoldFolder(RootFolderSelector folder) {
        this.rootTreeFolder = (RemoteWebElement) treeProjectWindow.findElementByName(folder.folderSelector);
        if (checkExpander(rootTreeFolder)) {
            baseAction.horizontalScroll(treeProjectWindow, rootTreeFolder);
            baseAction.doubleClick(rootTreeFolder);
        }
        return this;
    }

    public TreeProject unfoldFolder(SubFolderSelector selector, String folderName) {
        searchElementByName(selector, folderName);
        this.rootTreeFolder = (RemoteWebElement)targetFolder;
        if (checkExpander(rootTreeFolder)) {
            baseAction.horizontalScroll(treeProjectWindow, rootTreeFolder);
            baseAction.doubleClick(rootTreeFolder);
        }
        return this;
    }

    public TreeProject unfoldFolder(SubFolderSelector selector) {
        RemoteWebElement subFolder = (RemoteWebElement) rootTreeFolder.findElementByName(selector.folderSelector);
        if (checkExpander(subFolder)) {
            baseAction.horizontalScroll(treeProjectWindow, subFolder);
            baseAction.doubleClick(subFolder);
            this.rootTreeFolder = subFolder;
        }
        return this;
    }

    public void openEditorTargetFolder() {
        baseAction.horizontalScroll(treeProjectWindow, (RemoteWebElement) targetFolder);
        openEditorFromContextMenu((RemoteWebElement)targetFolder);
    }

//    public void openEditorFolder(RootFolderSelector selector) {
//        RemoteWebElement rootFolder = (RemoteWebElement) treeProjectWindow.findElementByName(selector.folderSelector);
//        baseAction.horizontalScroll(treeProjectWindow, rootFolder);
//        openEditorFromContextMenu(rootFolder);
//    }

//    public void checkDataFolder(RootFolderSelector rootFolderSelector, String screenName, SubFolderSelector subFolderSelector) {
//        unfoldFolder(rootFolderSelector);
//        List<WebElement> list = rootTreeFolder.findElementsByClassName(subFolderSelector.folderSelector);
//        baseAction.takeScreenshotToAttachOnAllureReport(treeProjectWindow, screenName , PRIMARY);
//        String message = ("Folder " + rootFolderSelector+ " is empty");
//        assertThat(message, list, is(notNullValue()));
//    }

    public void openEditorFromContext(SubFolderSelector what) {
//        getTree();
        switch (what){
            case LOG:
                unfoldFolder(WELLS);
                unfoldFolder(WELL, wellInProject.name);
                unfoldFolder(LOGS);
                searchElementByName(LOG, logInProject.name);
                openEditorTargetFolder();
                baseAction.clickOkInAttention((RemoteWebElement) driver.findElementByClassName("DataEditorView"));
                break;
            case TRAJECTORY:
                unfoldFolder(WELLS);
                unfoldFolder(WELL, wellInProject.name);
                searchElementByName(TRAJECTORY, trajectoryInProject.name);
                openEditorTargetFolder();
                break;
            case SURFACE:
                unfoldFolder(SURFACES);
                searchElementByName(SURFACE, surfaceInProject.name);
                openEditorTargetFolder();
                break;
            case POLYGON:
                unfoldFolder(POLYGONS);
                openEditorTargetFolder();
                break;
            case IMAGE:
                unfoldFolder(WELLS);
                unfoldFolder(WELL, wellInProject.name);
                unfoldFolder(IMAGES);
                searchElementByName(IMAGE, imageInProject.name);
                openEditorTargetFolder();
                break;
            case PICTURE:
                unfoldFolder(PICTURES);
                searchElementByName(PICTURE, pictureInProject.name);
                openEditorTargetFolder();
                break;
        }
    }

    public void checkDataFolder(SubFolderSelector folder) {
        switch (folder){
            case PICTURE:
                unfoldFolder(PICTURES);
                searchElementByName(PICTURE, pictureInProject.name);
                break;
            case POLYGON:
                unfoldFolder(POLYGONS);
                List<WebElement> polygonList = rootTreeFolder.findElementsByName(folder.folderSelector);
                baseAction.horizontalScroll(treeProjectWindow, rootTreeFolder);
                baseAction.takeScreenshotToAttachOnAllureReport(treeProjectWindow, "polygons", Appointment.PRIMARY);
                assertThat("Polygon is not loaded", polygonList.size(), not(equalTo(0)));
                break;
            case SURFACE:
                unfoldFolder(SURFACES);
                List<String> list = rootTreeFolder.findElementsByName(folder.folderSelector)
                        .stream()
                        .map(surface -> getFolderName((RemoteWebElement) surface))
                        .sorted()
                        .collect(Collectors.toList());
                assertThat("Surfaces folder is empty", list.size(), not(equalTo(0)));
                fileNameList.sort(Comparator.naturalOrder());
                list.sort(Comparator.naturalOrder());
                baseAction.horizontalScroll(treeProjectWindow, rootTreeFolder);
                baseAction.takeScreenshotToAttachOnAllureReport(treeProjectWindow, list.toString(), Appointment.PRIMARY);
                assertThat("The loaded data does not match",fileNameList, equalTo(list));
                break;
//            case PALETTE:
//                unfoldFolder(TEMPLATES);
//                List<WebElement> list = rootTreeFolder.findElementsByClassName(POLYGON.folderSelector);
//                assertThat("Polygon is not loaded", list, is(notNullValue()));
//                break;
        }
    }

    public void clickCheckBoxFolder(SubFolderSelector folder, NameEntityToProject entity) {
        RemoteWebElement element = searchElementByName(folder, entity.name);
        baseAction.horizontalScroll(treeProjectWindow, element);
        baseAction.clickCheckBox(element);
    }

    public void clickCheckBoxFolder(SubFolderSelector folder) {
        RemoteWebElement element= (RemoteWebElement)rootTreeFolder.findElementByName(folder.folderSelector);
        baseAction.horizontalScroll(treeProjectWindow, element);
        baseAction.clickCheckBox(element);
    }
}
