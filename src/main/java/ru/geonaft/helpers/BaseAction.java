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
import ru.geonaft.Base;
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

    public BaseAction copyInBuffer(String s) {
        StringSelection stringSelection = new StringSelection(s);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        return this;
    }

    public BaseAction pastFromBuffer() {
        actions
                .sendKeys(Keys.chord(Keys.LEFT_CONTROL, "v"))
                .build()
                .perform();
        return this;
    }

    public BaseAction ctr_A() {
        actions
                .sendKeys(Keys.chord(Keys.LEFT_CONTROL, "a"))
                .build()
                .perform();
        return this;
    }

    public BaseAction enterClick() {
        actions
                .sendKeys(Keys.chord(Keys.ENTER))
                .build()
                .perform();
        return this;
    }

    private String nameField = "TextBlock";
    private String attributeName = "Name";

    public String getFileName(RemoteWebElement element) {
        String fileName;
        fileName = element
                .findElementByClassName(nameField)
                .getAttribute(attributeName);
        return fileName;
    }

    public BaseAction enterValues(RemoteWebElement element, String values) {
        element.click();
        copyInBuffer(values);
        pastFromBuffer();
        enterClick();
        return this;
    }

    @WindowsFindBy(accessibility = "PART_ToggleButton")
    private RemoteWebElement btn;
    @Attachment(value = "Test screenshot", type = "image/png")
    public byte[] takeScreenshotToAttachOnAllureReport(RemoteWebElement element, String fileName, Appointment appointment) {
        moveTo(btn);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    public BaseAction takeScreenshot(RemoteWebElement element, String fileName, Appointment appointment) {
        moveTo(btn);
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        File screenshot = element.getScreenshotAs(OutputType.FILE);
        switch (appointment) {
            case PRIMARY:
                try {
                    FileUtils.copyFile(screenshot, new File(ScreenshotPaths.primaryDir + fileName + ".png"));
                } catch (IOException e) {
                    System.out.println("Primary screenshot is not created");
                }

                break;
            case SECONDARY:
                try {
                    FileUtils.copyFile(screenshot, new File(ScreenshotPaths.secondaryDir + fileName + ".png"));
                } catch (IOException e) {
                    System.out.println("Secondary screenshot is not created");
                }
        }
        return this;
    }

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

    @Attachment(value = "Test gif", type = "image/gif")
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
            bytes = Files.readAllBytes(Paths.get(ScreenshotPaths.resultGifsDir + fileName + ".gif"));
        } catch (Exception e) {
            System.out.println("Gif picture file is not created");
        }
        return bytes;
    }

    private String clickablePoint = "TextBlock";

    public BaseAction doubleClick(RemoteWebElement element) {
        RemoteWebElement elementButton =
                (RemoteWebElement) element
                .findElementByClassName(clickablePoint);
        actions
                .doubleClick(elementButton)
                .perform();
        return this;
    }

    public BaseAction rightClick(RemoteWebElement element) {
        RemoteWebElement elementButton =
                (RemoteWebElement) element
                        .findElementByClassName(clickablePoint);
        actions
                .contextClick(elementButton)
                .perform();
        return this;
    }

    private String checkBox = "CheckBox";

    public BaseAction clickCheckBox(RemoteWebElement element) {
        RemoteWebElement checkBox =
                (RemoteWebElement) element
                .findElementByClassName(this.checkBox);
        checkBox.click();
        return this;
    }

    public BaseAction moveTo(RemoteWebElement element) {
        actions
                .moveToElement(element)
                .build()
                .perform();
        return this;
    }

    public BaseAction horizontalScroll(RemoteWebElement mainView, RemoteWebElement element) {

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
        return this;
    }

    @FindBy(name = "Открытие")
    private RemoteWebElement openingWindowSelector;
    private String pathFieldSelector = "Предыдущие расположения";
    private String nameFieldSelector = "Имя файла:";
    private String openButtonSelector = "Открыть";
    private String CancelButtonSelector = "Отмена";


    public BaseAction multiFileLoad(String path) {
        copyInBuffer(path);
        RemoteWebElement window = openingWindowSelector;
        window.findElementByName(pathFieldSelector).click();
        pastFromBuffer();
        enterClick();
        window.findElementByClassName("UIItemsView").click();
        ctr_A();
        window.findElementByName(openButtonSelector).click();
        return this;
    }


    public BaseAction loadFile(String path, String fileName) {
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
        return this;
    }

    private List<WebElement> indicatorLoad;
    private String loadIndicatorSelector = "WaitIndicator";

    public BaseAction waitLoading(RemoteWebElement window) {
        boolean load = true;
        while (load) {
            List<WebElement> indicator = window.findElementsByClassName(loadIndicatorSelector);
            if (indicator.size() == 0) {
                load = false;
            }
        }
        return this;
    }

    @FindBy(className = "Window")
    protected RemoteWebElement windowsElement;
    private String attentionWindowSelector = "Предупреждение";
    @WindowsFindBy(accessibility = "OKButton")
    private RemoteWebElement okAttentionWindow;

    public BaseAction clickOkInAttention(RemoteWebElement window) {
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
        return this;
    }

    private String contextMenuSelector = "ContextMenu";
    private String menuItemSelector = "MenuItem";

    public BaseAction clickItemContextMenu(RemoteWebElement window, int indexItem) {
        actions.contextClick(window).perform();
        driver
                .findElementByClassName(contextMenuSelector)
                .findElementsByClassName(menuItemSelector)
                .get(indexItem).click();
        return this;
    }

}
