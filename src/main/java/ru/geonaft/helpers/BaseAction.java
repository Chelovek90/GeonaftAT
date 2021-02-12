package ru.geonaft.helpers;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.geonaft.ScreenshotPaths;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static ru.geonaft.Base.*;

public class BaseAction {

    WindowsDriver<RemoteWebElement> driver;

    public BaseAction(WindowsDriver<RemoteWebElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void copyInBuffer(String s) {
        StringSelection stringSelection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public void pastFromBuffer() {
        actions.sendKeys(Keys.chord(Keys.LEFT_CONTROL, "v")).build().perform();
    }

    public void ctr_A() {
        actions.sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a")).build().perform();
    }

    public void enterClick() {
        actions.sendKeys(Keys.chord(Keys.ENTER)).build().perform();
    }

    private String nameField = "TextBlock";
    private String attributeName = "Name";

    public String getFileName(RemoteWebElement element) {
        String fileName;
        fileName = element.findElementByClassName(nameField).getAttribute(attributeName);
        return fileName;
    }

//    @Attachment(value = "Test screenshot", type = "image/png")
//    public byte[] takeScreenshotToAttachOnAllureReport(RemoteWebElement element, String fileName, Appointment appointment) {
//        byte[] bytes = null;
//        File screen = element.getScreenshotAs(OutputType.FILE);
//        if (appointment == appointment.SECONDARY) {
//            try {
//                FileUtils.copyFile(screen, new File(ScreenshotPaths.secondaryDir + fileName + ".png"));
//                bytes = Files.readAllBytes(Paths.get(String.valueOf(screen)));
//            } catch (IOException e) {
//                System.out.println("Secondary screenshot is not created");
//            }
//        } else if (appointment == appointment.PRIMARY){
//            try {
//                FileUtils.copyFile(screen, new File(ScreenshotPaths.primaryDir + fileName + ".png"));
//                bytes = Files.readAllBytes(Paths.get(String.valueOf(screen)));
//            } catch (IOException e) {
//                System.out.println("Primary screenshot is not created");
//            }
//        }
//        return bytes;
//    }

    @Attachment(value = "Test screenshot", type = "image/png")
    public byte[] takeScreenshotToAttachOnAllureReport(RemoteWebElement element, String fileName, Appointment appointment) {
        byte[] bytes = null;
        File screenshot = element.getScreenshotAs(OutputType.FILE);
        switch (appointment) {
            case PRIMARY:
                try {
                    FileUtils.copyFile(screenshot, new File(ScreenshotPaths.primaryDir + fileName + ".png"));
                    bytes = Files.readAllBytes(Paths.get(String.valueOf(screenshot)));
                } catch (IOException e) {
                    System.out.println("Primary screenshot is not created");
                }

                break;
            case SECONDARY:
                try {
                    FileUtils.copyFile(screenshot, new File(ScreenshotPaths.secondaryDir + fileName + ".png"));
                    bytes = Files.readAllBytes(Paths.get(String.valueOf(screenshot)));
                } catch (IOException e) {
                    System.out.println("Secondary screenshot is not created");
                }
        }
        return bytes;
    }

//    public int takeDiffImage(String fileName) {
//        int diffPoint = 1;
//        try {
//            Screenshot secondaryScreenshot = new Screenshot(ImageIO.read(new File(ScreenshotPaths.secondaryDir + fileName + ".png")));
//            Screenshot primaryScreenshot = new Screenshot(ImageIO.read(new File(ScreenshotPaths.primaryDir + fileName + ".png")));
//            ImageDiff diff = new ImageDiffer().makeDiff(primaryScreenshot, secondaryScreenshot);
//            File diffFile = new File(ScreenshotPaths.diffDir + fileName + ".png");
//            ImageIO.write(diff.getMarkedImage(), "png", diffFile);
//            diffPoint = diff.getDiffSize();
////            Assertions.assertNotEquals(diffPoint, 0);
//        } catch (IOException e) {
//            System.out.println("File different is not created");
//        }
//        return diffPoint;
//    }


    public int takeDiffImage(String fileName) {
        int diffPoint = 1;

        try {
            Files.createDirectories(Path.of(ScreenshotPaths.diffDir));

            Screenshot primaryScreen = new Screenshot(ImageIO.read(new File(ScreenshotPaths.primaryDir + fileName + ".png")));
            Screenshot secondaryScreen = new Screenshot(ImageIO.read(new File(ScreenshotPaths.secondaryDir + fileName + ".png")));
            ImageDiff differ = new ImageDiffer().makeDiff(primaryScreen, secondaryScreen);


            File diffImage = new File(ScreenshotPaths.diffDir + fileName + ".png");
            ImageIO.write(differ.getMarkedImage(), "png", diffImage);
        } catch (IOException e) {
            System.out.println("Picture different is not created");
        }
        return diffPoint;
    }


    public byte[] createGiffFileToAttachOnAllureReport(String fileName) {
        byte[] bytes = null;
        try {
            Files.createDirectories(Path.of(ScreenshotPaths.resultGifsDir));

            BufferedImage first = ImageIO.read(new File(ScreenshotPaths.primaryDir + fileName + ".png"));
            ImageOutputStream output = new FileImageOutputStream(new File(ScreenshotPaths.resultGifsDir + fileName + ".gif"));
            GifSequenceWriter writer = new GifSequenceWriter(output, first.getType(), 550, true);
            writer.writeToSequence(first);
            File[] images = new File[]{
                    new File(ScreenshotPaths.primaryDir + fileName + ".png"),
                    new File(ScreenshotPaths.secondaryDir + fileName + ".png"),
                    new File(ScreenshotPaths.diffDir + fileName + ".png"),
            };
            for (File image : images) {
                BufferedImage next = ImageIO.read(image);
                writer.writeToSequence(next);
            }
            writer.close();
            output.close();
        } catch (Exception e) {
            System.out.println("Gif picture file is not created");
        }
        return bytes;
    }

    private String clickablePoint = "TextBlock";

    public void doubleClick(RemoteWebElement element) {
        RemoteWebElement elementButton = (RemoteWebElement) element.findElementByClassName(clickablePoint);
        actions.doubleClick(elementButton).perform();
    }

    public void rightClick(RemoteWebElement element) {
        RemoteWebElement elementButton = (RemoteWebElement) element.findElementByClassName(clickablePoint);
        actions.contextClick(elementButton).perform();
    }

    private String checkBox = "CheckBox";

    public void clickCheckBox(RemoteWebElement element) {
        RemoteWebElement checkBox = (RemoteWebElement) element.findElementByClassName(this.checkBox);
        checkBox.click();
    }

    public void moveTo(RemoteWebElement element) {
        actions.moveToElement(element).build().perform();
    }

    public void click(WebElement element) {
        actions.moveToElement(element).build().perform();
        element.click();
    }

    public void horizontalScroll(RemoteWebElement mainView, RemoteWebElement element) {

        int topMainView = mainView.getLocation().getY();
        int heightMainView = mainView.getSize().getHeight();
        int centerMainView = topMainView + heightMainView / 2;
        int bottomMainView = topMainView + heightMainView - 40;

        int topElement = element.getLocation().getY();
        int heightElement = element.getSize().getHeight();
        int bottomElement = topElement + heightElement;

        if (topElement < topMainView) {
            moveTo(mainView);
            while (element.getLocation().getY() < topMainView) {
                robot.mouseWheel(-1);
            }
        } else if (bottomElement > bottomMainView) {
            moveTo(mainView);
            while (element.getLocation().getY() > centerMainView) {
                robot.mouseWheel(+1);
            }
        }
    }

    @FindBy(name = "Открытие")
    private RemoteWebElement openingWindowSelector;
    private String pathFieldSelector = "Предыдущие расположения";
    private String nameFieldSelector = "Имя файла:";
    private String openButtonSelector = "Открыть";
    private String CancelButtonSelector = "Отмена";


    public void multiFileLoad(String path) {
        copyInBuffer(path);
        RemoteWebElement window = openingWindowSelector;
        window.findElementByName(pathFieldSelector).click();
        pastFromBuffer();
        enterClick();
        window.findElementByClassName("UIItemsView").click();
        ctr_A();
        window.findElementByName(openButtonSelector).click();
    }


    public void loadFile(String path, String fileName) {
        copyInBuffer(path);
        RemoteWebElement window = openingWindowSelector;
        click(window.findElementByName(pathFieldSelector));
        pastFromBuffer();
        enterClick();
        copyInBuffer(fileName);
        java.util.List<WebElement> nameFiled = window.findElementsByName(nameFieldSelector);
        click(nameFiled.get(1));
        pastFromBuffer();
        click(window.findElementByName(openButtonSelector));
    }

    //    @WindowsFindBy(accessibility = "IndicatorText")
    private List<WebElement> indicatorLoad;
    private String loadIndicatorSelector = "WaitIndicator";

    public void waitLoading(RemoteWebElement window) {
        boolean load = true;
        while (load) {
            List<WebElement> indicator = window.findElementsByClassName(loadIndicatorSelector);
            if (indicator.size() == 0) {
                load = false;
            }
        }
    }

    @FindBy(className = "Window")
    protected RemoteWebElement windowsElement;
    private String attentionWindowSelector = "Предупреждение";
    @WindowsFindBy(accessibility = "OKButton")
    private RemoteWebElement okAttentionWindow;

    public void clickOkInAttention(RemoteWebElement window) {
        for (int i = 0; i <= 3; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<WebElement> list = windowsElement.findElementsByName(attentionWindowSelector);
            if (list.size() != 0) {
                okAttentionWindow.click();
                waitLoading(window);
                break;
            }
        }
    }

    private String contextMenuSelector = "ContextMenu";
    private String menuItemSelector = "MenuItem";

    public void clickItemContextMenu(RemoteWebElement window, int indexItem) {
        actions.contextClick(window).perform();
        driver
                .findElementByClassName(contextMenuSelector)
                .findElementsByClassName(menuItemSelector)
                .get(indexItem).click();

    }
}
