package testsLoader;

import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.loader.Loader;
import ru.geonaft.view.startWindow.StartWindow;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.geonaft.NameEntityToProject.*;
import static ru.geonaft.base.TestsDataEnums.*;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.*;
import static testsLoader.FilesForTestLoader.*;

public class ShouldLoadEntityAllFormats extends BaseTest {

    @BeforeAll
    public static void openProject() {
        new StartWindow(desktopSession)
                .openProject(pathCleanProject, nameCleanProject);
        new BaseWorkSpace(desktopSession)
                .setCountHeaders();
    }

//    @AfterEach
//    public void closeProject() {
//        new Ribbon(desktopSession)
//                .closeProject();
//    }


        @Disabled
    @Test
    @DisplayName("Checking the activity of preview window elements")
    @Feature(value = "Loader")
    @Story(value = "Preview")
    @TmsLink("6439")
    public void TestWorkWithWindow() {
        new Loader(desktopSession)
                .openModule()
                .checkWorkElementsPreviewWindow();
    }


//        @Disabled
    @Test
    @DisplayName("Checking loading polygon")
    @Feature(value = "Loader")
    @Story(value = "Polygon")
    @TmsLink("6582")
    public void TestLoadedPolygon() {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(polygonForTest.path, polygonForTest.name, POLYGON)
                .checkDataFolder(POLYGON);
    }


    public static Stream<String> surfaceNameGenerator() {
        return Stream.of("CPS.cps3", "GRD.grd", "Zmap.zmap", "IRAP.irap");
    }

    //    @Disabled
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
                .openEditorLoadedFile(SURFACE)
                .checkDataInEditor(surfaceInProject);
    }

        @Disabled
    @Test
    @DisplayName("Renaming a trajectory in the loader")
    @Feature(value = "Loader")
    @Story(value = "Trajectory")
    @TmsLink("7456")
    public void TestChangeNameLoaderTrajectory() {
        new Loader(desktopSession)
                .openModule()
                .loadEntityWithRename(trajectoryForTest.path, trajectoryForTest.name, TRAJECTORY)
                .openEditorLoadedFile(TRAJECTORY)
                .checkDataInEditor(trajectoryInProject);
    }

        @Disabled
    @Test
    @DisplayName("Checking loading trajectory")
    @Feature(value = "Loader")
    @Story(value = "Trajectory")
    @TmsLink("7798")
    public void TestLoadedTrajectory() {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(trajectoryForTest.path, trajectoryForTest.name, TRAJECTORY)
                .openEditorLoadedFile(TRAJECTORY)
                .checkDataInEditor(trajectoryInProject);
    }

        @Disabled
    @Test
    @DisplayName("Checking loading log")
    @Feature(value = "Loader")
    @Story(value = "Log")
    @TmsLink("7800")
    public void TestLoadedLog() {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(logForTest.path, logForTest.name, LOG)
                .openEditorLoadedFile(LOG)
                .checkDataInEditor(logInProject);
    }

        @Disabled
    @Test
    @DisplayName("Checking loading image")
    @Feature(value = "Loader")
    @Story(value = "Image")
    @TmsLink("7802")
    public void TestLoadedImage() {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(imageForTest.path, imageForTest.name, IMAGE)
                .openEditorLoadedFile(IMAGE)
                .checkDataInEditor(imageInProject);
    }

    /*
     * Подумать о необходимости этого кейса тут, не могу проверить,
     * что тип данны в поле предпросмотра совпадает
     */
    public static Stream<String> patternNameGenerator() {
        return Stream.of("Шаблон карты.json", "Шаблон разреза.json", "Шаблон скважины.json");
    }

        @Disabled
    @ParameterizedTest
    @MethodSource("patternNameGenerator")
    @DisplayName("Checking preview in the window loader pattern: Map, Well, Cross section")
    @Feature(value = "Loader")
    @Story(value = "Pattern")
    @TmsLink("7802")
    public void TestLoadedPattern_Map_Well_CrossSection(String name) {
        new Loader(desktopSession)
                .openModule()
                .loadFileFromWindow(patternForTest.path, name)
                .doPreview(PATTERN);
        assertThat("Pattern name does not match", name, is(containsString(patternInProject.name)));
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

        @Disabled
    @Test
    @DisplayName("Checking multi-file pictures loading")
    @Feature(value = "Loader")
    @Story(value = "Multi-file(pictures)")
    @TmsLink("9327")
    public void TestMultiFileLoadedPicture() {
        new Loader(desktopSession)
                .openModule()
                .multiFileLoad(pictureForTest.path, PICTURE)
                .doMultiFilePreview(PICTURE);
//                .checkDataFolder(PICTURE);
    }


    /*
     *Исправить номер кейса для многофайловой загрузки
     */
        @Disabled
    @Test
    @DisplayName("Checking multi-file surfaces loading")
    @Feature(value = "Loader")
    @Story(value = "Multi-file(surfaces)")
    @TmsLink("11382")
    public void TestMultiFileLoadedSurfaces() {
        new Loader(desktopSession)
                .openModule()
                .multiFileLoad(surfaceForTest.path, SURFACE)
                .checkDataFolder(SURFACE);
    }


    /*
     * Фактически выполняется во всех кейчас на загрузку
     */
    @Disabled
    @Test
    @DisplayName("Check preview window")
    @Feature(value = "Loader")
    @Story(value = "Log")
    @TmsLink("12821")
    public void TestPreviewInTheWindowLoader() {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(logForTest.path, logForTest.name, LOG);
    }

//    @Disabled
//    @ParameterizedTest
//    @MethodSource("pictureNameGenerator")
//    @DisplayName("Check loading picture")
//    @Feature(value = "Loader")
//    @Story(value = "Picture")
//    @TmsLink("16054")
//    public void TestLoadedPalette(String name) {
//        new Loader(desktopSession)
//                .openModule()
//                .loadEntity(paletteForTest.path, paletteForTest.name, PICTURE)
//                .checkDataFolder(PICTURE);
//    }

}
