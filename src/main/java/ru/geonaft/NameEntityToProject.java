package ru.geonaft;


import java.util.List;

public enum NameEntityToProject {
    wellInProject(null),
//    actualWellInProject(null),
    actualWellInProject("12_actual"),
    refWellInProject(null),
    logInProject(null),
    trajectoryInProject(null),
    surfaceInProject(null),
    imageInProject(null),
    polygonInProject(null),
    pictureInProject(null),
    paletteInProject(null),
    patternInProject(null),
            ;

    public String name;
    NameEntityToProject(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<String> fileNameList;
}


