package ru.geonaft;


public enum NameEntityToProject {
    wellInProject(null),
    logInProject(null),
    surfaceInProject(null),
    imageInProject(null),
    polygonInProject(null),
    pictureInProject(null),
            ;

    public String name;
    NameEntityToProject(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
