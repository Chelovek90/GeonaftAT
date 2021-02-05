package ru.geonaft;


public enum NameEntityToProject {
    wellInProject(null),
    logInProject(null),
    trajectoryInProject(null),
    surfaceInProject(null),
    imageInProject(null),
    polygonInProject(null),
    pictureInProject(null),
    paletteInProject(null),
            ;

    public String name;
    NameEntityToProject(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
