package ru.geonaft;

import com.github.javafaker.Faker;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.geonaft.helpers.BaseAction;

import java.awt.*;
import java.util.Random;

public abstract class Base {

    @WindowsFindBy(accessibility = "MainShall")
    protected RemoteWebElement geonaftWindow;
    @FindBy(className = "Window")
    protected RemoteWebElement windowsElement;

    protected BaseAction baseAction;

    protected WindowsDriver<RemoteWebElement> driver;
    public static Robot robot;
    public static Actions actions;
    public static Random random;
    public static Faker faker;

    protected String enableButtonAttribute = "IsEnabled";


    public enum Appointment {
        PRIMARY,
        SECONDARY
    }

    public Base(WindowsDriver<RemoteWebElement> driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        try {
            this.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        faker = new Faker();
        baseAction = new BaseAction(driver);
        random = new Random();
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

}
