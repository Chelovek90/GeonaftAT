package ru.geonaft.view.treeProject.selectors;

public enum SubFolderSelector {

    WELL(""),
    CONTACT("Geosteering.UI.Controls.DataTreeView.DataTree.FluidContactTreeViewItem"),
    ZONE_GROUP("Geosteering.UI.Controls.DataTreeView.DataTree.ZonesGroupTreeViewItem"),
    ZONE("Geosteering.UI.Controls.DataTreeView.DataTree.ZoneTreeViewItem"),
    MARKER("Geosteering.UI.Controls.DataTreeView.DataTree.MarkerTreeViewItem"),
    SURFACE("Geosteering.UI.Controls.DataTreeView.DataTree.SurfaceTreeViewItem"),
    ;

    public final String folderSelector;

    SubFolderSelector(String folderSelector) {
        this.folderSelector = folderSelector;
    }
}
