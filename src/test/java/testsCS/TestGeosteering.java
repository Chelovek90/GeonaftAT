package testsCS;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import ru.geonaft.base.BaseTest;
import ru.geonaft.modules.cs.Geosteering;

public class TestGeosteering extends BaseTest {

    @Test
    public void TestChooseWells(TestInfo testInfo) {
        new Geosteering(desktopSession)
                .openModule()
                .choseWellsOnRibbonPanel();
    }

}
