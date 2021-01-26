package ru.geonaft.view.ribbone.modulesSelector;

public enum TabSelector {
    PROJECT("ProjectTab"),
    PROJECT_TOOLS("ProjectToolsTab"),
    OPTION("OptionsTab");

    public final String tabSelector;

    TabSelector(String tabSelector) {

        this.tabSelector = tabSelector;
    }
}
