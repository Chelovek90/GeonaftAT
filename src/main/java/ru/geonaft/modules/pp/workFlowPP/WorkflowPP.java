package ru.geonaft.modules.pp.workFlowPP;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.view.workSpaces.workFlow.BaseWorkFlow;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.geonaft.NameEntityToProject.*;

public class WorkflowPP extends BaseWorkFlow {

    public WorkflowPP(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
    }

    private String buttonMenuSelector = "NavBarItemControl";

    public WorkflowPP checkStepsPanelMenuRUS() {
        List<String> expectedSteps = Arrays.asList(
                "D-экспонента",
                "Выделение глин",
                "Градиент давления разрыва",
                "Литостатическое давление",
                "Настройка данных",
                "Поровое давление",
                "Сглаживание кривых",
                "Тренд нормального уплотнения");
        List<String> actualSteps = panelMenu
                .findElementsByClassName(buttonMenuSelector)
                .stream()
                .map(element -> element.getText())
                .sorted()
                .collect(Collectors.toList());

        assertThat("Buttons steps workflow does not equal expected",
                actualSteps,
                equalTo(expectedSteps));
        return this;
    }

    private String nameFieldSelector = "TextBlock";
    private String comboBoxSelector = "ComboBox";

    public WorkflowPP checkOptionViewStepConfigurationDataRUS() {
        List<String> expectedNameField = Arrays.asList(
                "1. Скважина",
                "2. Каротаж",
                "3. Зона",
                "Применить");
        List<String> actualNameFieldList = optionView
                .findElementsByClassName(nameFieldSelector)
                .stream()
                .map(element -> element.getText())
                .sorted()
                .collect(Collectors.toList());

        assertThat("Name fields does not equal expected name",
                actualNameFieldList,
                equalTo(expectedNameField));

        assertThat("Apply button is enabled",
                optionView
                        .findElementByClassName(applyButtonSelector)
                        .getAttribute(enableButtonAttribute),
                equalTo("False"));
        return this;
    }

    @WindowsFindBy(accessibility = "cbWellCollection")
    RemoteWebElement wellComboBox;

    public WorkflowPP chooseWell() {
        wellComboBox.click();
        actions.moveToElement(wellComboBox, 20, 35)
                .click()
                .build()
                .perform();

//        chooseWellForModule();

        assertThat("ComboBox 'Log' is not enable",
                optionView
                        .findElementsByClassName(comboBoxSelector)
                        .get(1)
                        .getAttribute(enableButtonAttribute),
                equalTo("True")
        );

        return this;
    }

    public WorkflowPP chooseLog() {
        optionView
                .findElementsByClassName(comboBoxSelector)
                .get(1)
                .click();

        actions.moveToElement(optionView
                .findElementsByClassName(comboBoxSelector)
                .get(1), 20, 35)
                .click()
                .build()
                .perform();

//        chooseLogForModule();

        assertThat("'Apply' button is not enable",
                optionView
                        .findElementByClassName(applyButtonSelector)
                        .getAttribute(enableButtonAttribute),
                equalTo("True"));

        return this;
    }

    public WorkflowPP checkStepConfigurationForActivity() {
        panelMenu
                .findElementsByClassName(buttonMenuSelector)
                .stream()
                .forEach(step -> {
                    assertThat("Step " + step.getText() + " is not enabled",
                            step.getAttribute(enableButtonAttribute),
                            equalTo("True"));
                });
        return this;
    }

    public WorkflowPP clickOverburdenPressure() {
        panelMenu
                .findElementsByClassName(buttonMenuSelector)
                .get(1)
                .click();
        setOptionViewForStepWorkflow();
        return this;
    }

    public WorkflowPP checkOptionViewStepOverburdenPressureRUS() {
        List<String> expectedNameField = Arrays.asList(
                "",
                " ",
                "1. Результаты расчета",
                "2. Параметры скважины",
                "3. Параметры экстраполяции",
                "4. Входные данные:",
                "TVD",
                "Высота столба воздуха:",
                "Глубина моря:",
                "Градиент литостатического давления:",
                "Конечная TVD :",
                "Литостатическое давление:",
                "Начальная TVD :",
                "Плотность",
                "Плотность воды:",
                "Плотность:",
                "Применить",
                "Экстраполированная плотность:",
                "г/см³",
                "м",
                "м",
                "м",
                "м");
        List<String> actualNameFieldList = optionView
                .findElementsByClassName(nameFieldSelector)
                .stream()
                .map(element -> element.getText())
                .sorted()
                .collect(Collectors.toList());

        assertThat("Name fields does not equal expected name",
                actualNameFieldList,
                equalTo(expectedNameField));
        return this;
    }

    @WindowsFindBy(accessibility = "DensityCurvesCollection")
    private RemoteWebElement densityCurveComboBox;
    public WorkflowPP chooseDensityCurve() {
        densityCurveComboBox.click();
        actions.moveToElement(densityCurveComboBox, 20, 35)
                .click()
                .build()
                .perform();
        return this;
    }

}
