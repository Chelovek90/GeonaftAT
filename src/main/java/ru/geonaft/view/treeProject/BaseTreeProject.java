package ru.geonaft.view.treeProject;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.view.treeProject.selectors.RootFolderSelector;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.geonaft.NameEntityToProject.fileNameList;
import static ru.geonaft.NameEntityToProject.pictureInProject;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.*;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.PICTURE;

public class BaseTreeProject extends Base {


    @WindowsFindBy(accessibility = "TreeView")
    private RemoteWebElement treeProjectSelector;

    public BaseTreeProject(WindowsDriver<RemoteWebElement> driver) {
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
        List<WebElement> list =
                rootTreeFolder
                        .findElementsByName(folder.folderSelector);

        targetFolder =
                (RemoteWebElement) list.stream()
                        .filter(entity -> entity.findElement(By.className(clickablePoint)).getText().equals(folderName))
                        .findFirst().orElse(null);

        assertTrue(targetFolder != null, "Search element by name " + folderName + " returned no results");
        return targetFolder;
    }

    private RemoteWebElement treeProjectWindow;
    private RemoteWebElement rootTreeFolder;
    private RemoteWebElement targetFolder;

    public BaseTreeProject unfoldFolder(RootFolderSelector folder) {
        this.rootTreeFolder =
                (RemoteWebElement) treeProjectWindow
                        .findElementByName(folder.folderSelector);
        if (checkExpander(rootTreeFolder)) {
            baseAction
                    .horizontalScroll(treeProjectWindow, rootTreeFolder);
            baseAction
                    .doubleClick(rootTreeFolder);
        }
        return this;
    }

    public BaseTreeProject unfoldFolder(SubFolderSelector selector, String folderName) {
        searchElementByName(selector, folderName);
        this.rootTreeFolder = (RemoteWebElement) targetFolder;
        if (checkExpander(rootTreeFolder)) {
            baseAction.horizontalScroll(treeProjectWindow, rootTreeFolder);
            baseAction.doubleClick(rootTreeFolder);
        }
        return this;
    }

    public BaseTreeProject unfoldFolder(SubFolderSelector folder) {
        RemoteWebElement subFolder = (RemoteWebElement) rootTreeFolder.findElementByName(folder.folderSelector);
        this.rootTreeFolder = subFolder;
        if (checkExpander(subFolder)) {
            baseAction.horizontalScroll(treeProjectWindow, subFolder);
            baseAction.doubleClick(subFolder);
        }
        return this;
    }

    public BaseTreeProject checkDataFolder(SubFolderSelector folder) {
        assertThat("Submodule folder not found",
                rootTreeFolder
                        .findElementsByName(folder.folderSelector)
                        .size(),
                not(equalTo(0))
        );
        return this;
    }
}
