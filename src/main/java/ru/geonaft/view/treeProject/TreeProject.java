package ru.geonaft.view.treeProject;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import ru.geonaft.BaseAction;
import ru.geonaft.view.treeProject.selectors.RootFolderSelector;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TreeProject extends BaseAction {
    protected WebElement targetFolder;

    protected RemoteWebElement treeProjectWindow;

    @WindowsFindBy(accessibility = "TreeView")
    private RemoteWebElement treeProjectSelector;

    public TreeProject(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        this.treeProjectWindow = treeProjectSelector;
    }
    protected String condition = "ExpandCollapse.ExpandCollapseState";

    public boolean checkExpander(RemoteWebElement element) {
        String expanderCondition = element.getAttribute(condition);
        boolean collapsed = false;
        switch (expanderCondition){
            case "LeafNode":
                assertTrue(collapsed, "Block " + getFileName(element) + " is empty");
                break;
            case "Expanded":
                break;
            case "Collapsed":
                collapsed = true;
                break;
        }
        return collapsed;
    }
    public void searchElementByName(List<WebElement> list, String folderName) {
        targetFolder = list.stream()
                .filter(surface -> surface.findElement(By.className(clickablePoint)).getText().equals(folderName))
                .findFirst().orElse(null);
        assertTrue(targetFolder != null, "Search element by name " + folderName + " returned no results");
    }

    @FindBy(className = "ContextMenu")
    private RemoteWebElement contextMenu;
    private String menuItem = "MenuItem";
    @WindowsFindBy(accessibility = "OKButton")
    protected RemoteWebElement okButton;

    public void openEditorFromContextMenu(RemoteWebElement element) {
        rightClick(element);
        List<WebElement> items = contextMenu.findElementsByClassName(menuItem);
        items.get(0).click();
    }

    private RemoteWebElement rootTreeFolder;
    public void unfoldRootFolder(RootFolderSelector selector) {
        this.rootTreeFolder = (RemoteWebElement) treeProjectWindow.findElementByName(selector.folderSelector);
        if (checkExpander(rootTreeFolder)) {
            horizontalScroll(treeProjectWindow, rootTreeFolder);
            doubleClick(rootTreeFolder);
        }
    }

    public void unfoldSubFolder(SubFolderSelector selector, String folderName) {
        List<WebElement> list = rootTreeFolder.findElementsByName(selector.folderSelector);
        searchElementByName(list, folderName);
        this.rootTreeFolder = (RemoteWebElement)targetFolder;
        if (checkExpander(rootTreeFolder)) {
            horizontalScroll(treeProjectWindow, rootTreeFolder);
            doubleClick(rootTreeFolder);
        }
    }

    public void unfoldSubFolder(SubFolderSelector selector) {
        this.rootTreeFolder = (RemoteWebElement) rootTreeFolder.findElementsByName(selector.folderSelector);
        if (checkExpander(rootTreeFolder)) {
            horizontalScroll(treeProjectWindow, rootTreeFolder);
            doubleClick(rootTreeFolder);
        }
    }

    public void openEditorTargetFolder() {
        horizontalScroll(treeProjectWindow, (RemoteWebElement) targetFolder);
        openEditorFromContextMenu((RemoteWebElement)targetFolder);
    }

    public void clickCheckBox(SubFolderSelector selector) {
        RemoteWebElement needClick = (RemoteWebElement) rootTreeFolder.findElementByName(selector.folderSelector);
        horizontalScroll(treeProjectWindow, needClick);
        clickCheckBox(needClick);
    }

    public void clickCheckBox(SubFolderSelector selector, String folderName) {
        List<WebElement> list = rootTreeFolder.findElementsByName(selector.folderSelector);
        searchElementByName(list, folderName);
        horizontalScroll(treeProjectWindow, (RemoteWebElement) targetFolder);
        clickCheckBox((RemoteWebElement) targetFolder);
    }



}
