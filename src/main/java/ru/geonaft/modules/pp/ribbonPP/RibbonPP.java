package ru.geonaft.modules.pp.ribbonPP;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.ribbon.BaseRibbon;

public class RibbonPP extends BaseRibbon {

    public RibbonPP(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }
}
