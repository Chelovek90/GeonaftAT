package ru.geonaft.helpers;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.geonaft.ScreenshotPaths;
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
import java.nio.file.Paths;
import java.util.List;

import static ru.geonaft.Base.*;

public class BaseAction {

    WindowsDriver<RemoteWebElement> driver;

    public BaseAction(WindowsDriver<RemoteWebElement> driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    protected void copyInBuffer(String s) {
        StringSelection stringSelection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    protected void pastFromBuffer(){
        actions.sendKeys(Keys.chord(Keys.LEFT_CONTROL, "v")).build().perform();
    }

    protected void enterClick() {
        actions.sendKeys(Keys.chord(Keys.ENTER)).build().perform();
    }

    private String nameField = "TextBlock";
    private String attributeName = "Name";
    public String getFileName(RemoteWebElement element) {
        String fileName;
        fileName = element.findElementByClassName(nameField).getAttribute(attributeName);
        return fileName;
    }

    @Attachment(value = "Test screenshot", type = "image/png")
    public byte[] takeScreenshotToAttachOnAllureReport(RemoteWebElement element, String fileName, appointment appointment) {
        byte[] bytes = null;
        File screen = element.getScreenshotAs(OutputType.FILE);
        if (appointment == appointment.SECONDARY) {
            try {
                FileUtils.copyFile(screen, new File(ScreenshotPaths.secondaryDir + fileName + ".png"));
                bytes = Files.readAllBytes(Paths.get(String.valueOf(screen)));
            } catch (IOException e) {
                System.out.println("Secondary screenshot is not created");
            }
        } else if (appointment == appointment.PRIMARY){
            try {
                FileUtils.copyFile(screen, new File(ScreenshotPaths.primaryDir + fileName + ".png"));
                bytes = Files.readAllBytes(Paths.get(String.valueOf(screen)));
            } catch (IOException e) {
                System.out.println("Primary screenshot is not created");
            }
        }
        return bytes;
    }

    public void takeDiffImage(RemoteWebElement element) {
        String fileName = getFileName(element);
        try {
            Screenshot secondaryScreenshot = new Screenshot(ImageIO.read(new File(ScreenshotPaths.secondaryDir + fileName + ".png")));
            Screenshot primaryScreenshot = new Screenshot(ImageIO.read(new File(ScreenshotPaths.primaryDir + fileName + ".png")));
            ImageDiff diff = new ImageDiffer().makeDiff(primaryScreenshot, secondaryScreenshot);
            File diffFile = new File(ScreenshotPaths.diffDir + fileName + ".png");
            ImageIO.write(diff.getMarkedImage(), "png", diffFile);
            int diffPoint = diff.getDiffSize();
            Assertions.assertNotEquals(diffPoint, 0);
        } catch (IOException e) {
            System.out.println("File different is not created");
        }
    }

    public void createGiffFile(String fileName) {
        try {
            BufferedImage first = ImageIO.read(new File(ScreenshotPaths.secondaryDir + fileName + ".png"));
            ImageOutputStream output = new FileImageOutputStream(new File(ScreenshotPaths.resultGifsDir + fileName + ".gif"));
            GifSequenceWriter writer = new GifSequenceWriter(output, first.getType(), 250, true);
            writer.writeToSequence(first);
            File[] images = new File[]{
                    new File(ScreenshotPaths.secondaryDir + fileName + ".png"),
                    new File(ScreenshotPaths.primaryDir + fileName + ".png"),
                    new File(ScreenshotPaths.diffDir + fileName + ".png"),
            };
            for (File image : images) {
                BufferedImage next = ImageIO.read(image);
                writer.writeToSequence(next);
            }
            writer.close();
            output.close();
        }catch (Exception e){System.out.println("Gif file is not created");}
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

    protected void clickCheckBox(RemoteWebElement element) {
        RemoteWebElement checkBox = (RemoteWebElement) element.findElementByClassName(this.checkBox);
        checkBox.click();
    }

    public void moveTo(RemoteWebElement element) {
        actions.moveToElement(element).build().perform();
    }

    public void horizontalScroll(RemoteWebElement mainView, RemoteWebElement element) {

        int topMainView = mainView.getLocation().getY();
        int heightMainView = mainView.getSize().getHeight();
        int centerMainView = topMainView + heightMainView/2;
        int bottomMainView = topMainView + heightMainView - 40;

        int topElement = element.getLocation().getY();
        int heightElement = element.getSize().getHeight();
        int bottomElement = topElement + heightElement;

        if(topElement < topMainView) {
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
    public void loadFile(String path, String fileName) {
        copyInBuffer(path);
        RemoteWebElement window = openingWindowSelector;
        window.findElementByName(pathFieldSelector).click();
        pastFromBuffer();
        enterClick();
        copyInBuffer(fileName);
        java.util.List<WebElement> nameFiled = window.findElementsByName(nameFieldSelector);
        nameFiled.get(1).click();
        pastFromBuffer();
        window.findElementByName(openButtonSelector).click();
    }

    @WindowsFindBy(accessibility = "IndicatorText")
    private java.util.List<WebElement> indicatorLoad;
    public void waitLoading() {
        boolean load = true;
        while (load) {
            java.util.List<WebElement> indicator = indicatorLoad;
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
    public void clickOkInAttention() {
        for (int i = 0; i <=5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<WebElement> list = windowsElement.findElementsByName(attentionWindowSelector);
            if (list.size() != 0) {okAttentionWindow.click();
                waitLoading();
                break;
            }
        }
    }
}