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
                .chooseWell()
                .chooseLog()
                .clickApplyButton();
        workflow
                .checkStepConfigurationForActivity();
        treeProject
                .checkCreateSubfolderModule();
        workSpace
                .checkCreateTrackWithCurve(2,1);
        return this;
    }

    public PorePressure OverburdenPressureCalculation() {

        return this;
    }

    public PorePressure Test() {
        workflow
                .chooseWellAndLogClickApply();
        return this;
    }
}
