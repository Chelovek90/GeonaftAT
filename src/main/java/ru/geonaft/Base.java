package ru.geonaft;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.PageFactory;
import ru.geonaft.view.ribbone.Ribbon;
import ru.geonaft.view.treeProject.TreeProject;

import java.awt.*;
import java.util.List;

public abstract class Base {

    protected Ribbon ribbon;
    protected TreeProject treeProject;

    protected List<String> entityName;

    protected WindowsDriver<RemoteWebElement> driver;
    protected Robot robot;
    protected Actions actions;

    public enum appointment {
        ACTUAL,
        EXPECTED
    }

    public Base(WindowsDriver<RemoteWebElement> driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

}