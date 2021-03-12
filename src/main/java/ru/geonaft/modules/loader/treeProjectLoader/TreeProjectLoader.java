package ru.geonaft.modules.loader.treeProjectLoader;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.treeProject.BaseTreeProject;
import ru.geonaft.view.treeProject.TreeProjectOld;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.*;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.*;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.IMAGE;

public class TreeProjectLoader extends BaseTreeProject {

    public TreeProjectLoader(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }

    public TreeProjectLoader checkDataFolder(SubFolderSelector folder) {
        switch (folder) {
            case PICTURE:
                unfoldFolder(PICTURES);
                searchElementByName(PICTURE, pictureInProject.name);
                break;
            case POLYGON:
                unfoldFolder(POLYGONS);
                List<WebElement> polygonList = rootTreeFolder.findElementsByName(folder.folderSelector);
                baseAction.horizontalScroll(treeProjectWindow, rootTreeFolder);
                baseAction.takeScreenshotToAttachOnAllureReport(treeProjectWindow, "Load polygons", Appointment.PRIMARY);
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
        }
        return this;
    }

    public TreeProjectLoader openEditorFromContext(SubFolderSelector what) {
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
}
