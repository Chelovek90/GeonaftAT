package testsLoader;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.loader.Loader;
import ru.geonaft.view.ribbon.BaseRibbon;
import ru.geonaft.view.startWindow.StartWindow;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;

import java.util.stream.Stream;

import static ru.geonaft.base.TestsDataEnums.polygonForTest;
import static ru.geonaft.base.TestsDataEnums.surfaceForTest;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.POLYGON;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.SURFACE;

public class TestLoader extends BaseTest {

    private static String pathToSurface;
    private static String pathToLog;
    private static String pathToTimeLog;
    private static String pathToImage;
    private static String pathToTrajectory;
    private static String pathToPolygon;

    private static String pathCleanProject;
    private static String nameCleanProject;

    @BeforeAll
    static void dataPreparation() {
        loadProperties("loader");
        pathCleanProject = properties.getProperty("pathCleanProject");
        nameCleanProject = properties.getProperty("nameCleanProject");
    }

    @BeforeEach
    public void openProject() {
        new StartWindow(desktopSession)
                .openProject(pathCleanProject, nameCleanProject);
    }

    @AfterEach
    public void closeProject() {
        new BaseRibbon(desktopSession)
                .closeProject();
    }

    @Test
    @DisplayName("Checking the activity of preview window elements")
    @Feature(value = "Loader")
    @Story(value = "Preview")
    @TmsLink("6439")
    public void TestWorkWithWindow(TestInfo testInfo) {
        String screenName = testInfo.getDisplayName();
        new Loader(desktopSession)
                .openModule()
                .checkWorkPreviewWindow(screenName);
    }

    @Test
    @DisplayName("Checking loading polygon")
    @Feature(value = "Loader")
    @Story(value = "Polygon")
    @TmsLink("6582")
    public void TestLoadedPolygon() {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(polygonForTest.path, polygonForTest.name, POLYGON)
                .checkLoadInTreeProject(POLYGON);
    }

    public static Stream<String> surfaceNameGenerator() {
        return Stream.of("CPS.cps3", "GRD.grd", "Zmap.zmap", "IRAP.irap");
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("surfaceNameGenerator")
    @DisplayName("Checking loading surfaces of all formats")
    @Feature(value = "Loader")
    @Story(value = "Surfaces")
    @TmsLink("7460")
    public void TestLoadedSurfaces(String fileName) {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(surfaceForTest.path, fileName, SubFolderSelector.SURFACE)
                .openEditorLoadedFile(SURFACE);
//                .checkDataInEditor(surfaceInProject);
    }

}
