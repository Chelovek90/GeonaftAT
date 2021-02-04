package ru.geonaft.view.treeProject.selectors;

public enum RootFolderSelector {

    WELLS("Geosteering.UI.Controls.DataTreeView.DataTree.WellsTreeViewItem"),
    CONTACTS("Geosteering.UI.Controls.DataTreeView.DataTree.FluidContactsTreeViewItem"),
    ZONES("Geosteering.UI.Controls.DataTreeView.DataTree.ZonesTreeViewItem"),
    STRATIGRAPHY("Geosteering.UI.Controls.DataTreeView.DataTree.StratigraphyTreeViewItem"),
    SURFACES("Geosteering.UI.Controls.DataTreeView.DataTree.SurfacesTreeViewItem"),
    POLYGONS("Geosteering.UI.Controls.DataTreeView.DataTree.PolygonsTreeViewItem"),
    PICTURES("Geosteering.UI.Controls.DataTreeView.DataTree.PicturesTreeViewItem"),
    ;

    public final String folderSelector;

    RootFolderSelector(String folderSelector) {
        this.folderSelector = folderSelector;
    }

}
