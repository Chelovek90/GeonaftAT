package testsLoader;

import io.qameta.allure.Stories;
import io.qameta.allure.Story;
import io.qameta.allure.TmsLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.geonaft.Base;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.loader.Loader;
import ru.geonaft.view.ribbone.Ribbon;
import ru.geonaft.view.treeProject.TreeProject;
import ru.geonaft.view.treeProject.selectors.SubFolderSelector;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.geonaft.view.treeProject.selectors.RootFolderSelector.WELLS;
import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.*;
import static testsLoader.PathsToFiles.pathToLog;

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

    public static Stream<String> surfaceNameGenerator() {
        return Stream.of("Zmap.zmap");
    }

    @Disabled
    @ParameterizedTest
    @MethodSource("surfaceNameGenerator")
    @DisplayName("Check loading surfaces of all formats")
    @Stories({@Story("Loader"), @Story("Surfaces")})
    @TmsLink("7460")
    public void TestLoadedSurfaces(String fileName) {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(PathsToFiles.pathToSurface, fileName, SubFolderSelector.SURFACE)
                .openEditorLoadedFile(SURFACE);
    }

    //    @Disabled
    @Test
    @DisplayName("Check loading log")
    @Stories({@Story("Loader"), @Story("Logs")})
    @TmsLink("7800")
    public void TestLoadedLog() {
        new Loader(desktopSession)
                .openModule()
                .loadEntity(pathToLog, "12_actual_DATA_GEONAFT.las", LOG)
                .openEditorLoadedFile(LOG)
                .checkDataInEditor();
    }

    @Disabled
    @Test
    public void TestMetgods() {
        new TreeProject(desktopSession)
                .unfoldFolder(WELLS)
                .unfoldFolder(WELL, "205")
                .unfoldFolder(LOGS);
    }

    @Disabled
    @Test
    public void test() {
        WebElement el = desktopSession
                .findElementByClassName("Window")
                .findElementByName("ЗАГРУЗЧИК ДАННЫХ");
        WebElement e = el.findElement(By.name("Загрузить"));
        System.out.println(e.getAttribute("IsEnabled"));
        assertThat(e.getAttribute("IsEnabled"), is(equalTo("True")));

    }

}
