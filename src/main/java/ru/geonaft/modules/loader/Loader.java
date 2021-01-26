package ru.geonaft.modules.loader;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.BaseAction;

import java.util.List;

public class Loader extends BaseAction {

    public Loader(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }


}
