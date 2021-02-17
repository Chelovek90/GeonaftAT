package ru.geonaft.view.startWindow;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import ru.geonaft.Base;
import ru.geonaft.helpers.BaseAction;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class StartWindow extends Base {

    protected List<WebElement> buttonsStartWindowGF;
    private String button = "Button";
    @FindBy(className = "StartPageView")
    private RemoteWebElement startWindowGF;
    public StartWindow(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        this.buttonsStartWindowGF = startWindowGF.findElementsByClassName(button);
        this.baseAction = new BaseAction(driver);
    }

    public StartWindow clickOpenButton() {
        RemoteWebElement openButton = (RemoteWebElement) buttonsStartWindowGF.get(0);
        openButton.click();
//        baseAction.click(openButton);
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
        baseAction.loadFile(path, fileName);
        List<WebElement> notification = windowsElement.findElementsByName(notificationWindowSelector);
        if (notification.size() != 0){
            yesNotificationWindow.click();
            baseAction.waitLoading(geonaftWindow);
        }
        String windowName = geonaftWindow.getText();
        assertThat("Window name does not match the project name", windowName, containsString(fileName));
        System.out.println("Project name - " + windowName);
    }
}
