import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.CS.Geosteering;
import ru.geonaft.view.startWindow.StartWindow;
import ru.geonaft.view.workSpace.editor.BaseWorkSpace;


public class testsGeosteering extends BaseTest {

    static String pathCleanProject = "D:\\Data for testing\\Проекты";
    static String nameCleanProject = "clearProjectForTests3.7.37.21";


    @BeforeAll
    public static void openProject() {
//        new StartWindow(desktopSession)
//                .openProject(pathCleanProject, nameCleanProject);
        new BaseWorkSpace(desktopSession)
                .setCountHeaders();
    }

    @Test
    public void TestStartCS() {
        new Geosteering(desktopSession)
                .openModule()
                .choseWellOnRibbonPanel()
                .activeCheckBoxActualWell()
                .activeCheckBoxRefWell();
    }

    @Test public void test() {
        new BaseWorkSpace(desktopSession)
                .checkTabName("504_ПЛ_1_Копия_2");
    }
}
