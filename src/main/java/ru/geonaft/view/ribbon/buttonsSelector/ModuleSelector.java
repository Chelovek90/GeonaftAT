package ru.geonaft.view.ribbon.buttonsSelector;

public enum ModuleSelector {
    LOADER(0,"Geosteering.Geonaft.Module.Dataloader.Modularity.DataLoaderToolbarInfo"),
    CS(1, "Geosteering.Geonaft.Module.CrossSection.CrossSectionToolbarInfo"),
    SYNTHETIC(1, "Geosteering.Geonaft.Module.Synthetics.SyntheticsToolbarInfo"),
    CP(1, "Geosteering.Geonaft.Module.CorrelationPanel.Modularity.CorrelationPanelToolbarInfo"),
    QIDIP(1, "Geosteering.Geonaft.Module.QIDip.Modularity.QiDipToolbarInfo"),
    AUTOLOADER(1, "Geosteering.Geonaft.Module.RealTime.RealTimeToolbarInfo"),
    PRESSURE(1, "Geosteering.Geonaft.Module.Pressures.Modularity.PressuresToolbarInfo"),
    ;

    public final String moduleSelector;
    public final int indexGroup;

    ModuleSelector(int indexGroup, String moduleSelector) {
        this.moduleSelector = moduleSelector;
        this.indexGroup = indexGroup;
    }

}
