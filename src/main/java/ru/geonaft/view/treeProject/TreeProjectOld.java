package ru.geonaft.view.treeProject;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import ru.geonaft.Base;
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

public class TreeProjectOld extends Base {

    private WebElement targetFolder;

    private RemoteWebElement treeProjectWindow;

    @WindowsFindBy(accessibility = "TreeView")
    private RemoteWebElement treeProjectSelector;

    public TreeProjectOld(WindowsDriver<RemoteWebElement> driver) {
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
        switch (expanderCondition) {
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

    public RemoteWebElement searchFolder(SubFolderSelector folder) {
        List<WebElement> list = rootTreeFolder.findElementsByName(folder.folderSelector);
        assertTrue(list != null, "Search folder " + folder + " returned no results");
        targetFolder = list.get(0);
        return (RemoteWebElement) targetFolder;
    }

    @FindBy(className = "ContextMenu")
    private RemoteWebElement contextMenu;
    private String menuItem = "MenuItem";
    @WindowsFindBy(accessibility = "OKButton")
    protected RemoteWebElement okButton;

    public TreeProjectOld openEditorFromContextMenu(RemoteWebElement element) {
        baseAction.rightClick(element);
        List<WebElement> items = contextMenu.findElementsByClassName(menuItem);
        items.get(0).click();
        return this;
    }

    public TreeProjectOld clickItemFromContextMenu(SubFolderSelector folder, int indexMenuItem) {
        RemoteWebElement subFolderText = rootTreeFolder
                .findElementByName(folder.folderSelector)
                .findElement(By.className(clickablePoint));
        baseAction.horizontalScroll(treeProjectWindow, subFolderText);
        baseAction.clickItemContextMenu(subFolderText, indexMenuItem);
        return this;
    }

    private RemoteWebElement rootTreeFolder;

    public TreeProjectOld unfoldFolder(RootFolderSelector folder) {
        this.rootTreeFolder = (RemoteWebElement) treeProjectWindow.findElementByName(folder.folderSelector);
        if (checkExpander(rootTreeFolder)) {
            baseAction.horizontalScroll(treeProjectWindow, rootTreeFolder);
            baseAction.doubleClick(rootTreeFolder);
        }
        return this;
    }

    public TreeProjectOld unfoldFolder(SubFolderSelector selector, String folderName) {
        searchElementByName(selector, folderName);
        this.rootTreeFolder = (RemoteWebElement) targetFolder;
        if (checkExpander(rootTreeFolder)) {
            baseAction.horizontalScroll(treeProjectWindow, rootTreeFolder);
            baseAction.doubleClick(rootTreeFolder);
        }
        return this;
    }

    public TreeProjectOld unfoldFolder(SubFolderSelector folder) {
        RemoteWebElement subFolder = (RemoteWebElement) rootTreeFolder.findElementByName(folder.folderSelector);
        this.rootTreeFolder = subFolder;
        if (checkExpander(subFolder)) {
            baseAction.horizontalScroll(treeProjectWindow, subFolder);
            baseAction.doubleClick(subFolder);
        }
        return this;
    }

    public TreeProjectOld openEditorTargetFolder() {
        baseAction.horizontalScroll(treeProjectWindow, (RemoteWebElement) targetFolder);
        openEditorFromContextMenu((RemoteWebElement) targetFolder);
        return this;
    }

    public TreeProjectOld openEditorRootFolder(RootFolderSelector folder) {
        WebElement rootFolder = treeProjectWindow
                .findElementByName(folder.folderSelector);
        baseAction.horizontalScroll(treeProjectWindow, (RemoteWebElement) rootFolder);
        openEditorFromContextMenu((RemoteWebElement) rootFolder);
        return this;
    }

    public TreeProjectOld openEditorFromContext(SubFolderSelector what) {
        switch (what) {
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
        return this;
    }

    public TreeProjectOld checkDataFolder(SubFolderSelector folder) {
        switch (folder) {
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
                assertThat("The loaded data does not match", fileNameList, equalTo(list));
                break;
//            case PALETTE:
//                unfoldFolder(TEMPLATES);
//                List<WebElement> list = rootTreeFolder.findElementsByClassName(POLYGON.folderSelector);
//                assertThat("Polygon is not loaded", list, is(notNullValue()));
//                break;
        }
        return this;
    }

    private String checkBoxState = "Toggle.ToggleState";
    private String checkBox = "CheckBox";

    public TreeProjectOld clickCheckBoxFolder(SubFolderSelector folder) {
        RemoteWebElement element = (RemoteWebElement) rootTreeFolder.findElementByName(folder.folderSelector);
        int primaryState = Integer.parseInt(
                element.findElementByClassName(checkBox).getAttribute(checkBoxState));
        baseAction.horizontalScroll(treeProjectWindow, element);
        baseAction.clickCheckBox(element);
        int secondaryState = Integer.parseInt(
                element.findElementByClassName(checkBox).getAttribute(checkBoxState));
        assertThat("I couldn't click on the checkbox", primaryState, not(equalTo(secondaryState)));
        return this;
    }

    public boolean checkFolderInTreeProject(SubFolderSelector folder) {
        boolean result = false;
        List<WebElement> subFolder = rootTreeFolder.findElementsByName(folder.folderSelector);
//        assertThat("Folder - " + folder + " was not found", subFolder.size(), not(equalTo(0)) );
        if (subFolder.size() == 1) {
            return result = true;
        }
        return result;
    }
}