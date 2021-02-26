package ru.geonaft.view.ribbon.buttonsSelector;

public enum TabSelector {
    PROJECT("ProjectTab"),
    PROJECT_TOOLS("ProjectToolsTab"),
    OPTION("OptionsTab"),
    CS("CsMainTab"),
    CS_SCALING("CsScalingTab"),
    ;

    public final String tabSelector;

    TabSelector(String tabSelector) {

        this.tabSelector = tabSelector;
    }
}
