package ru.geonaft.modules.pp;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.RemoteWebElement;
import ru.geonaft.Base;
import ru.geonaft.modules.loader.OpenModule;
import ru.geonaft.modules.pp.ribbonPP.RibbonPP;
import ru.geonaft.modules.pp.treeProjectPP.TreeProjectPP;
import ru.geonaft.modules.pp.workFlowPP.WorkflowPP;
import ru.geonaft.modules.pp.workSpacePP.WorkSpacePP;
import ru.geonaft.view.ribbon.buttonsSelector.ModuleSelector;

import static ru.geonaft.view.treeProject.selectors.SubFolderSelector.PRESSURE;

public class PorePressure extends Base implements OpenModule {

    public RibbonPP ribbon;
    public TreeProjectPP treeProject;
    public WorkSpacePP workSpace;
    public WorkflowPP workflow;

    public PorePressure(WindowsDriver<RemoteWebElement> driver) {
        super(driver);
        ribbon = new RibbonPP(driver);
        treeProject = new TreeProjectPP(driver);
        workSpace = new WorkSpacePP(driver);
    }

    @Override
    public PorePressure openModule() {
        ribbon
                .openModule(ModuleSelector.PRESSURE);
        workSpace
                .compareCountHeaders()
                .getWorkSpace();
        workflow = new WorkflowPP(driver);
        return this;
    }

    public PorePressure chooseWellAndLogClickApply() {
        workflow
                .checkStepsPanelMenuRUS()
                .checkOptionViewStepConfigurationRUS()
                .chooseWell()
                .chooseLog()
                .clickApplyButton();
        ribbon
                .checkActivityRibbonButton();
        workflow
                .checkStepConfigurationForActivity();
        treeProject
                .checkCreateFolder(PRESSURE)
                .checkCreateSubfolderModule(1);
        workSpace
                .checkCreateTrackWithCurve(1,1);
        return this;
    }

    public PorePressure chooseWellLogAndZoneClickApply() {
        workflow
                .checkStepsPanelMenuRUS()
                .checkOptionViewStepConfigurationRUS()
                .chooseWell()
                .chooseLog()
                .chooseZone()
                .clickApplyButton();
        ribbon
                .checkActivityRibbonButton();
        workflow
                .checkStepConfigurationForActivity();
        treeProject
                .checkCreateFolder(PRESSURE)
                .checkCreateSubfolderModule(1);
        workSpace
                .checkCreateTrackWithCurve(1,1)
                .checkCrateTrackZone(1);
        return this;
    }

    /*Overburden Pressure*/

    public PorePressure overburdenPressureCalculation() {
        workflow
                .clickOverburdenPressure()
                .checkOptionViewStepOverburdenPressureRUS()
                .chooseDensityCurve()
                .clickApplyButton()
                .waitLoading();
        workSpace
                .checkCreateTrackWithCurve(3, 5);
        treeProject
                .checkCalculatedCurveInFolder(3);
        return this;
    }

    /*D-Exponent*/

    public PorePressure clickDExponent() {
        workflow
                .clickDExponent();
        return this;
    }

    public PorePressure dExponentCalculation() {
        workflow
                .clickDExponent()
                .checkOptionViewStepDExponentWithoutZoneRUS()
                .chooseCurvesParameterDExponent()
                .clickApplyButton()
                .waitLoading();
        workSpace
                .checkCreateTrackWithCurve(2, 2);
        treeProject
                .checkCalculatedCurveInFolder(1);
        return this;
    }

    public PorePressure dExponentCheckRangeConstants() {
        workflow
                .clickDExponent()
                .checkEnableApplyButton();
        workflow
                .checkOptionViewStepDExponentWithoutZoneRUS()
                .checkEnterInvalidValuesConstants();
        return this;
    }

    public PorePressure dExponentCalculationCalculatedMethod(WorkflowPP.calculateMethodsDExponent method) {
        workflow
                .chooseCalculationMethod(method)
                .chooseCurvesParameterDExponent()
                .clickApplyButton();
        workSpace
                .checkCreateTrackWithCurve(2,2);
        treeProject
                .checkCalculatedCurveInFolder(1);
        return this;
    }

    public PorePressure Test() {

        return this;
    }
}
