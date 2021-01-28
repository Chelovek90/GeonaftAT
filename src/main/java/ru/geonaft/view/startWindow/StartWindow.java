package ru.geonaft.view.startWindow;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import ru.geonaft.BaseAction;

import java.util.List;

public class StartWindow extends BaseAction {

    protected List<WebElement> buttonsStartWindowGF;
    private String button = "Button";
    @FindBy(className = "StartPageView")
    private RemoteWebElement startWindowGF;
    public StartWindow(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        this.buttonsStartWindowGF = startWindowGF.findElementsByClassName(button);
    }

    public StartWindow clickOpenButton() {
        RemoteWebElement openButton = (RemoteWebElement) buttonsStartWindowGF.get(0);
        openButton.click();
        return this;
    }

    public StartWindow clickNewButton() {
        RemoteWebElement newButton = (RemoteWebElement) buttonsStartWindowGF.get(1);
        newButton.click();
        return this;
    }

    private String notificationWindowSelector = "Предупреждение";
    @WindowsFindBy(accessibility = "Yes")
    private RemoteWebElement yesNotificationWindow;
    public void openProject(String path, String fileName) {
        clickOpenButton();
        loadFile(path, fileName);
        List<WebElement> notification = windowsElement.findElementsByName(notificationWindowSelector);
        if (notification.size() != 0){
            yesNotificationWindow.click();
            waitLoading();
        }
    }
}
