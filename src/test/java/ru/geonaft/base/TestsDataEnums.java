package ru.geonaft.base;

public enum  TestsDataEnums {
    surfaceForTest("D:\\Data for testing\\Поверхности\\IRAP", null),
    logForTest("D:\\Data for testing\\Каротажи", "12_actual_DATA_GEONAFT.las"),
    imageForTest("D:\\Data for testing\\Имиджы", "Имидж.las"),
    trajectoryForTest("D:\\Data for testing\\Траектории", "Имидж.las"),
    polygonForTest("D:\\Data for testing\\Полигоны", "Полигон"),
    pictureForTest("D:\\ScreenShot\\форматы", null),
    ;

    public final String path;
    public final String name;

    TestsDataEnums(String path, String name) {
        this.path = path;
        this.name = name;
    }
}
