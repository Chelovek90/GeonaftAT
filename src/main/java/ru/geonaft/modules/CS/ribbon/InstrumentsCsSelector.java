package ru.geonaft.modules.CS.ribbon;

public enum InstrumentsCsSelector {
    DipsProcessing(4,1,"RibbonToggleButton"),
    GeomodelTable(4,2,"RibbonButton"),
    AddNewDip(5,1,"RibbonToggleButton"),

    SetHomeView(1,0,"RibbonButton"),
    GoHomeView(1,1,"RibbonButton"),
    ;

    public final String buttonClassName;
    public final int indexGroup;
    public final int indexButton;

    InstrumentsCsSelector(int indexGroup, int indexButton, String moduleSelector) {
        this.buttonClassName = moduleSelector;
        this.indexGroup = indexGroup;
        this.indexButton = indexButton;
    }
}
