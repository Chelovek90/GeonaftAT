package ru.geonaft.modules.loader.previewFilds;

public enum PreviewFieldsSelector {

    WELL_ID("cbName"),
    LOGS("tboxMapTemplateViewModel"),
    TIME_LOGS("tboxMapTemplateViewModel"),
    LOG_SELECTOR("Geosteering.Geonaft.Module.Dataloader.Classes.DataTree.Items.LogTreeItem"),
    CURVE_SELECTOR("Geosteering.Geonaft.Module.Dataloader.Classes.DataTree.Items.CurveTreeItem"),
    IMAGE_ID("cbTypeCurve"),
    SURFACE_ID("tboxSurfaceViewModel")
    ;


    public final String value;

    PreviewFieldsSelector(String value) {
        this.value = value;
    }
}
