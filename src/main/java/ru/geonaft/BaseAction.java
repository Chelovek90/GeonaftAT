package ru.geonaft;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import ru.geonaft.helpers.GifSequenceWriter;
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
import java.util.List;

public abstract class BaseAction extends Base{

    public BaseAction(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
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

    protected void makeElementScreenshot(RemoteWebElement element, appointment appointment) {
        String fileName = getFileName(element);
        File screen = element.getScreenshotAs(OutputType.FILE);
        if (appointment == Base.appointment.ACTUAL) {
            try {
                FileUtils.copyFile(screen, new File(BasePaths.actualDir + fileName + ".png"));
            } catch (IOException e) {
                System.out.println("ScreenShot is not created");
            }
        } else if (appointment == Base.appointment.EXPECTED){
            try {
                FileUtils.copyFile(screen, new File(BasePaths.expectedDir + fileName + ".png"));
            } catch (IOException e) {
            }
        }
    }

    protected void takeDiffImage(RemoteWebElement element) {
        String fileName = getFileName(element);
        try {
            Screenshot actualScreenshot = new Screenshot(ImageIO.read(new File(BasePaths.actualDir + fileName + ".png")));
            Screenshot expectedScreenshot = new Screenshot(ImageIO.read(new File(BasePaths.expectedDir + fileName + ".png")));
            ImageDiff diff = new ImageDiffer().makeDiff(expectedScreenshot, actualScreenshot);
            File diffFile = new File(BasePaths.diffDir + fileName + ".png");
            ImageIO.write(diff.getMarkedImage(), "png", diffFile);
            int diffPoint = diff.getDiffSize();
            Assertions.assertNotEquals(diffPoint, 0);
        } catch (IOException e) {
            System.out.println("File is not created");
        }
    }

    protected void createGiffFile(String fileName) {
        try {
            BufferedImage first = ImageIO.read(new File(BasePaths.actualDir + fileName + ".png"));
            ImageOutputStream output = new FileImageOutputStream(new File(BasePaths.resultGifsDir + fileName + ".gif"));
            GifSequenceWriter writer = new GifSequenceWriter(output, first.getType(), 250, true);
            writer.writeToSequence(first);
            File[] images = new File[]{
                    new File(BasePaths.actualDir + fileName + ".png"),
                    new File(BasePaths.expectedDir + fileName + ".png"),
                    new File(BasePaths.diffDir + fileName + ".png"),
            };
            for (File image : images) {
                BufferedImage next = ImageIO.read(image);
                writer.writeToSequence(next);
            }
            writer.close();
            output.close();
        }catch (Exception e){}
    }

    protected String clickablePoint = "TextBlock";
    protected void doubleClick(RemoteWebElement element) {
        RemoteWebElement elementButton = (RemoteWebElement) element.findElementByClassName(clickablePoint);
        actions.doubleClick(elementButton).perform();
    }

    protected void rightClick(RemoteWebElement element) {
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

    protected void horizontalScroll(RemoteWebElement mainView, RemoteWebElement element) {

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
    protected void loadFile(String path, String fileName) {
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
        waitLoading();
    }

    @WindowsFindBy(accessibility = "IndicatorText")
    private java.util.List<WebElement> indicatorLoad;
    protected void waitLoading() {
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
