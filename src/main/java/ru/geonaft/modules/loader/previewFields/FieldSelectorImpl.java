package ru.geonaft.modules.loader.previewFields;

public class FieldSelectorImpl implements SelectorsBuilder{
    FieldSelector selector = new FieldSelector();

    @Override
    public SelectorsBuilder setWellFieldId(String wellFieldId) {
        selector.wellFieldId = wellFieldId;
        return this;
    }

    @Override
    public SelectorsBuilder setLogFieldId(String logFieldId) {
        selector.logFieldId = logFieldId;
        return this;
    }

    @Override
    public SelectorsBuilder setCurveFieldId(String curveFieldId) {
        selector.curveFieldId = curveFieldId;
        return this;
    }

    @Override
    public SelectorsBuilder setImageFieldId(String imageFieldId) {
        selector.imageFieldId = imageFieldId;
        return this;
    }

    @Override
    public SelectorsBuilder setSurfaceFieldId(String surfaceFieldId) {
        selector.surfaceFieldId = surfaceFieldId;
        return this;
    }

    @Override
    public FieldSelector build() {
        return selector;
    }
}
