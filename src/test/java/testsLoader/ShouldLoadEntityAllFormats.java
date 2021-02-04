package testsLoader;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.geonaft.Base;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.loader.Loader;
import ru.geonaft.view.ribbone.Ribbon;
import ru.geonaft.view.treeProject.TreeProject;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;

import java.util.stream.Stream;

import static ru.geonaft.NameEntityToProject.logInProject;
import static ru.geonaft.NameEntityToProject.surfaceInProject;
import static ru.geonaft.base.TestsDataEnums.*;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.*;

public class ShouldLoadEntityAllFormats extends BaseTest {

    @BeforeEach
    public void openProject() {
//        new StartWindow(desktopSession)
//                .openProject(PathsToFiles.pathCleanProject, "clearProjectForTests");
        Base.ribbon = new Ribbon(desktopSession);
        Base.treeProject = new TreeProject(desktopSession);
        Base.workSpace = new BaseWorkSpace(desktopSession);
    }
//
//    @AfterEach
//    public void closeProject() {
//        new Ribbon(desktopSession)
//                .closeProject();
//    }


    //    @Disabled
    @Test
    @DisplayName("Check loading polygon")
    @Feature(value = "Loader")
    @Story(value = "Polygon")
    @TmsLink("6582")
    public void TestLoadedPolygon() {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(polygonForTest.path, polygonForTest.name, POLYGON)
                .checkDataInEditor(polygonForTest.name);
    }


    public static Stream<String> surfaceNameGenerator() {
        return Stream.of("CPS.cps3", "GRD.grd","Zmap.zmap", "IRAP.irap");
    }

    //    @Disabled
    @ParameterizedTest
    @MethodSource("surfaceNameGenerator")
    @DisplayName("Check loading surfaces of all formats")
    @Feature(value = "Loader")
    @Story(value = "Surfaces")
    @TmsLink("7460")
    public void TestLoadedSurfaces(String fileName) {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(surfaceForTest.path, fileName, SubFolderSelector.SURFACE)
                .openEditorLoadedFile(SURFACE)
                .checkDataInEditor(surfaceInProject.name);
    }

    //    @Disabled
    @Test
    @DisplayName("Check loading trajectory")
    @Feature(value = "Loader")
    @Story(value = "Trajectory")
    @TmsLink("7798")
    public void TestLoadedTrajectory() {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(trajectoryForTest.path, trajectoryForTest.name, TRAJECTORY)
                .openEditorLoadedFile(LOG)
                .checkDataInEditor(logForTest.name);
    }

    //    @Disabled
    @Test
    @DisplayName("Check loading log")
    @Feature(value = "Loader")
    @Story(value = "Log")
    @TmsLink("7800")
    public void TestLoadedLog() {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(logForTest.path, logForTest.name, LOG)
                .openEditorLoadedFile(LOG)
                .checkDataInEditor(logInProject.name);
    }

    //    @Disabled
    @Test
    @DisplayName("Check loading image")
    @Feature(value = "Loader")
    @Story(value = "Image")
    @TmsLink("7802")
    public void TestLoadedImage() {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(imageForTest.path, imageForTest.name, IMAGE)
                .openEditorLoadedFile(IMAGE)
                .checkDataInEditor(imageForTest.name);
    }

    public static Stream<String> pictureNameGenerator() {
        return Stream.of("bmp.bmp", "jpeg.jpg", "png.png", "tif.tif");
    }

    //    @Disabled
    @ParameterizedTest
    @MethodSource("pictureNameGenerator")
    @DisplayName("Check loading picture")
    @Feature(value = "Loader")
    @Story(value = "Picture")
    @TmsLink("9326")
    public void TestLoadedPicture(String name) {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(pictureForTest.path, name, PICTURE)
                .checkDataFolder(PICTURE);
    }


}
