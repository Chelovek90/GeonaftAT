package ru.geonaft.modules.pp.workFlowPP;

import io.appium.java_client.pagefactory.WindowsFindBy;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.view.workSpaces.workFlow.BaseWorkFlow;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

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

        assertThat("Buttons steps workflow does not equal expected",
                getAllStepsNameWorkflow(),
                equalTo(expectedSteps));
        return this;
    }

    public WorkflowPP checkOptionViewStepConfigurationRUS() {
        List<String> expectedNameField = Arrays.asList(
                "1. Скважина",
                "2. Каротаж",
                "3. Зона",
                "Применить");

        assertThat("Name fields does not equal expected name",
                getAllTextFromOptionView(optionView),
                equalTo(expectedNameField));

        checkEnableApplyButton();
        return this;
    }

    @WindowsFindBy(accessibility = "cbWellCollection")
    RemoteWebElement wellComboBox;

    public WorkflowPP chooseWell() {
        primitiveChooseFromComboBox(
                (RemoteWebElement) optionView
                        .findElementsByClassName(comboBoxSelector)
                        .get(0));

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
        primitiveChooseFromComboBox(
                (RemoteWebElement) optionView
                        .findElementsByClassName(comboBoxSelector)
                        .get(1));

        assertThat("'Apply' button is not enable",
                optionView
                        .findElementByClassName(applyButtonSelector)
                        .getAttribute(enableButtonAttribute),
                equalTo("True"));

        return this;
    }

    public WorkflowPP chooseZone() {
        RemoteWebElement zone = (RemoteWebElement) optionView
                .findElementsByClassName(comboBoxSelector)
                .get(2);
        zone.click();
        actions.moveToElement(zone, 20, 55)
                .click()
                .build()
                .perform();
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

    /*Overburden Pressure*/

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
                "м");

        assertThat("Name fields does not equal expected name",
                getAllTextFromOptionView(optionView),
                equalTo(expectedNameField));
        return this;
    }

    @WindowsFindBy(accessibility = "DensityCurvesCollection")
    private RemoteWebElement densityCurveComboBox;

    public WorkflowPP chooseDensityCurve() {
        primitiveChooseFromComboBox(densityCurveComboBox);
        return this;
    }

    /*D-Exponent*/

    private String workflowDExponentSelector = "DExponentWorkflowView";

    public WorkflowPP setOptionViewDExponent() {
        this.optionView =
                mainViewID
                        .findElementByClassName(workflowSelector)
                        .findElement(By.className(workflowDExponentSelector));
        return this;
    }

    public WorkflowPP clickDExponent() {
        panelMenu
                .findElementsByClassName(buttonMenuSelector)
                .get(2)
                .click();
        setOptionViewDExponent();
        checkEnableApplyButton();
        return this;
    }

    public WorkflowPP checkOptionViewStepDExponentWithoutZoneRUS() {
        List<String> expectedNameField = Arrays.asList(
                "1. Результаты расчета",
                "2. Настройка интервалов расчета",
                "3. Входные данные",
                "D-экспонента",
                "Весь интервал",
                "Гидростатический градиент",
                "Диаметр долота",
                "Ед.изм.",
                "Значение",
                "Зона",
                "Метод",
                "Нагрузка на долото",
                "Параметр",
                "Применить",
                "Скорость проходки",
                "Частота вращения",
                "ЭЦП");

        assertThat("Name fields does not equal expected name",
                getAllTextFromOptionView(optionView),
                equalTo(expectedNameField));
        return this;
    }

    public WorkflowPP checkOptionViewStepDExponentWithZoneRUS() {
        List<String> expectedNameField = Arrays.asList(
                "1. Результаты расчета",
                "2. Настройка интервалов расчета",
                "3. Входные данные",
                "D-экспонента",
                "Весь интервал",
                "Гидростатический градиент",
                "Диаметр долота",
                "Ед.изм.",
                "Значение",
                "Зона",
                "Метод",
                "Нагрузка на долото",
                "Параметр",
                "Применить",
                "Скорость проходки",
                "Частота вращения",
                "ЭЦП");

        assertThat("Name fields does not equal expected name",
                getAllTextFromOptionView(optionView),
                equalTo(expectedNameField));
        return this;
    }

    public WorkflowPP checkInputDataBlockDExponentCalculateMethodRUS() {
        setOptionViewDExponent();
        List<String> expectedNameField = Arrays.asList(
                "Гидростатический градиент",
                "Диаметр долота",
                "Ед.изм.",
                "Значение",
                "Нагрузка на долото",
                "Параметр",
                "Скорость проходки",
                "Частота вращения",
                "ЭЦП");
        RemoteWebElement inputDataBlock = (RemoteWebElement) optionView
                .findElementByClassName(inputDataBlockSelector);
        assertThat("Name fields does not equal expected name",
                getAllTextFromOptionView(inputDataBlock),
                equalTo(expectedNameField));
        return this;
    }

    public WorkflowPP checkInputDataBlockDExponentNoneMethodRUS() {
        setOptionViewDExponent();
        List<String> expectedNameField = Arrays.asList(
                "Ед.изм.",
                "Значение",
                "Параметр");
        RemoteWebElement inputDataBlock = (RemoteWebElement) optionView
                .findElementByClassName(inputDataBlockSelector);
        assertThat("Name fields does not equal expected name",
                getAllTextFromOptionView(inputDataBlock),
                equalTo(expectedNameField));
        return this;
    }

    public WorkflowPP checkInputDataBlockDExponentCurveMethodRUS() {
        setOptionViewDExponent();
        List<String> expectedNameField = Arrays.asList(
                "Ед.изм.",
                "Значение",
                "Кривая D-экспоненты",
                "Параметр");
        RemoteWebElement inputDataBlock = (RemoteWebElement) optionView
                .findElementByClassName(inputDataBlockSelector);
        assertThat("Name fields does not equal expected name",
                getAllTextFromOptionView(inputDataBlock),
                equalTo(expectedNameField));
        return this;
    }

    private String curveParameterSelector = "Geosteering.Core.CalculatableModule.InputParameters.Parameters.CurveParameterViewModel";

    public WorkflowPP chooseCurvesParameterDExponent() {
        optionView
                .findElementsByName(curveParameterSelector)
                .stream()
                .forEach(row ->
                        primitiveChooseFromComboBox(row.findElement(By.className(comboBoxSelector))));
        checkEnableApplyButton();
        return this;
    }

    private String inputDataBlockSelector = "DataGrid";
    private String dataGridSelector = "DataGridRow";
    private String constantSelector = "Geosteering.Core.CalculatableModule.InputParameters.Parameters.ConstantWithDependencyParameterViewModel`1[Geosteering.Domain.Enums.TypeValue]";
    private String GGCoefficient = "Geosteering.Core.CalculatableModule.InputParameters.Parameters.ConstantWithUnitsParameterViewModel";

    public WorkflowPP checkEnterInvalidValuesConstants() {
        RemoteWebElement inputDataBlock = (RemoteWebElement) optionView
                .findElementByClassName(inputDataBlockSelector);

        inputDataBlock
                .findElementsByClassName(dataGridSelector)
                .stream()
                .filter(grid -> grid.getAttribute(nameAttribute).equals(GGCoefficient))
                .forEach(row -> {
                    String primaryValue = row
                            .findElement(By.className(textBoxSelector))
                            .getAttribute(valueAttribute);
                    baseAction.enterValues((RemoteWebElement) row, "3.01");
                    String actualValue = row
                            .findElement(By.className(textBoxSelector))
                            .getAttribute(valueAttribute);
                    assertThat("Value greater than 3 is entered",
                            primaryValue,
                            equalTo(actualValue));
                });

        inputDataBlock
                .findElementsByClassName(dataGridSelector)
                .stream()
                .filter(grid -> grid.getAttribute(nameAttribute).equals(constantSelector) | grid.getAttribute(nameAttribute).equals(GGCoefficient))
                .forEach(row -> {
                    String primaryValue = row
                            .findElement(By.className(textBoxSelector))
                            .getAttribute(valueAttribute);
                    baseAction.enterValues((RemoteWebElement) row, "-1");
                    String actualValue = row
                            .findElement(By.className(textBoxSelector))
                            .getAttribute(valueAttribute);
                    assertThat("Negative value is entered",
                            primaryValue,
                            equalTo(actualValue));
                });
        return this;
    }

    private String calculationIntervalBlockSelector = "ZoneTable";

    public enum calculateMethodsDExponent {
        none,
        calculate,
        curve;
    }

    public WorkflowPP chooseCalculationMethod(calculateMethodsDExponent method) {

        RemoteWebElement calculationIntervalBlock = (RemoteWebElement) optionView
                .findElementByClassName(calculationIntervalBlockSelector);

        RemoteWebElement methodComboBox = calculationIntervalBlock
                .findElementsByClassName(dataGridSelector)
                .get(0)
                .findElement(By.className(comboBoxSelector));

        methodComboBox.click();

        switch (method) {
            case none:
                actions.moveToElement(methodComboBox, 20, 35)
                        .click()
                        .build()
                        .perform();
                checkInputDataBlockDExponentNoneMethodRUS();
                break;
            case calculate:
                actions.moveToElement(methodComboBox, 20, 55)
                        .click()
                        .build()
                        .perform();
                checkInputDataBlockDExponentCalculateMethodRUS();
                break;
            case curve:
                actions.moveToElement(methodComboBox, 20, 75)
                        .click()
                        .build()
                        .perform();
                checkInputDataBlockDExponentCurveMethodRUS();
                break;
        }

        return this;
    }


}
