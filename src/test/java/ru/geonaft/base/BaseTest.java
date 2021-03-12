package ru.geonaft.base;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebElement;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public abstract class BaseTest {
    protected static Properties properties = new Properties();
    protected static WindowsDriver<RemoteWebElement> desktopSession;
//    private static String geonaftPath = "D:\\Installer\\Geonaft\\3.7.37.23\\717\\Geonaft.exe";
    private static String geonaftPath = "D:\\Installer\\Geonaft\\3.8.0.26\\720\\Geonaft.exe";
    private static String winAppDriverPath = "C:\\Program Files (x86)\\Windows Application Driver\\WinAppDriver.exe";
    private static Runtime runtime = Runtime.getRuntime();
    private static Process geonaftProcess;

    @WindowsFindBy(accessibility = "MainShall")
    protected static List<RemoteWebElement> geonaftWindow;
    @BeforeAll
    public static void setUp() throws IOException, InterruptedException {

        File file = new File(winAppDriverPath);
        Desktop.getDesktop().open(file);
//
//        geonaftProcess = runtime.exec(geonaftPath);

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("deviceName", "WindowsPC");
        capabilities.setCapability("app", "Root");

        desktopSession = new WindowsDriver<RemoteWebElement>(new URL("http://127.0.0.1:4723"), capabilities);
        desktopSession.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

//        boolean loadApp = true;
//        while (loadApp) {
//            List<RemoteWebElement> app = desktopSession.findElementsByAccessibilityId("MainShall");
//            if (app.size() != 0) {
//                loadApp = false;
//            }
//        }

    }

    @AfterAll
    public static void tearDown() throws IOException {
//        geonaftProcess.destroy();
        Runtime.getRuntime().exec("taskkill /F /IM WinAppDriver.exe");
        desktopSession.quit();
    }

    public void restartDesktopSession() throws MalformedURLException {
        desktopSession.quit();
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Windows");
        capabilities.setCapability("deviceName", "WindowsPC");
        capabilities.setCapability("app", "Root");
        desktopSession = new WindowsDriver<RemoteWebElement>(new URL("http://127.0.0.1:4723"), capabilities);
        desktopSession.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    protected static void loadProperties(String propertyName) {
        try {
            properties.load(new FileInputStream("src\\test\\resources\\" + propertyName + ".properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
