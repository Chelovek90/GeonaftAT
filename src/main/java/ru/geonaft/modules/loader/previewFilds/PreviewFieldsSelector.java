package ru.geonaft.modules.loader.previewFilds;

public enum PreviewFieldsSelector {

    WELL_ID("cbName"),

    ENTITY_ID("tboxMapTemplateViewModel"),
    CB_ENTITY_ID("cbMapTemplateViewModel"),

    LOG_SELECTOR("Geosteering.Geonaft.Module.Dataloader.Classes.DataTree.Items.LogTreeItem"),

    TRAJECTORY_ID("tboxTrajectoryViewModel"),

    CURVE_SELECTOR("Geosteering.Geonaft.Module.Dataloader.Classes.DataTree.Items.CurveTreeItem"),

    SECTOR_SELECTOR("Geosteering.Geonaft.Module.Dataloader.Classes.DataTree.Items.SectorTreeItem"),

    IMAGE_ID("cbTypeCurve"),

    SURFACE_ID("tboxSurfaceViewModel"),

    SURFACE_SELECTOR("Geosteering.Geonaft.Module.Dataloader.Classes.DataTree.Items.PolygonTreeItem"),

    ROW_SELECTOR("Geosteering.UI.Infrastructure.ViewModels.TreeViewItems.WarningTreeItem"),
    ;


    public final String selector;

    PreviewFieldsSelector(String selector) {
        this.selector = selector;
    }
}
