package ru.geonaft.modules.loader.previewFields;

public interface SelectorsBuilder {
    SelectorsBuilder setWellFieldId(String wellFieldId);
    SelectorsBuilder setLogFieldId(String logFieldId);
    SelectorsBuilder setCurveFieldId(String curveFieldId);
    SelectorsBuilder setImageFieldId(String imageFieldId);
    SelectorsBuilder setSurfaceFieldId(String surfaceFieldId);
    FieldSelector build();
}
