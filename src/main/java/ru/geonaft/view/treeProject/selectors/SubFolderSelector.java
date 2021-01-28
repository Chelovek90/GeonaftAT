package ru.geonaft.view.treeProject.selectors;

public enum SubFolderSelector {

    WELL("Geosteering.UI.Controls.DataTreeView.DataTree.WellTreeViewItem"),
    TRAJECTORY("Geosteering.UI.Controls.DataTreeView.DataTree.TrajectoryTreeViewItem"),
    LOGS("Geosteering.UI.Controls.DataTreeView.DataTree.LogsTreeViewItem"),
    LOG("Geosteering.UI.Controls.DataTreeView.DataTree.LogTreeViewItem"),
    TIME_LOGS("Geosteering.UI.Controls.DataTreeView.DataTree.TimeLogsTreeViewItem"),
    IMAGE("Geosteering.UI.Controls.DataTreeView.DataTree.ImagesTreeViewItem"),
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
