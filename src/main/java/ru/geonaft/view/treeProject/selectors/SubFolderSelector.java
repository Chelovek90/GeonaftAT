package ru.geonaft.view.treeProject.selectors;

public enum SubFolderSelector {

    WELL("Geosteering.UI.Controls.DataTreeView.DataTree.WellTreeViewItem"),

    TRAJECTORY("Geosteering.UI.Controls.DataTreeView.DataTree.TrajectoryTreeViewItem"),

    TARGETS("Geosteering.UI.Controls.DataTreeView.DataTree.TargetsTreeViewItem"),

    GEO_JOURNAL("Geosteering.UI.Controls.DataTreeView.DataTree.DecisionsTreeViewItem"),

    PLAN_TRAJECTORIES("Geosteering.UI.Controls.DataTreeView.DataTree.TrajectoriesTreeViewItem"),

    LOGS("Geosteering.UI.Controls.DataTreeView.DataTree.LogsTreeViewItem"),
    LOG("Geosteering.UI.Controls.DataTreeView.DataTree.LogTreeViewItem"),
    CURVE("Geosteering.UI.Controls.DataTreeView.DataTree.CurveTreeViewItem"),

    TIME_LOGS("Geosteering.UI.Controls.DataTreeView.DataTree.TimeLogsTreeViewItem"),

    IMAGES("Geosteering.UI.Controls.DataTreeView.DataTree.ImagesTreeViewItem"),
    IMAGE("Geosteering.UI.Controls.DataTreeView.DataTree.ImageTreeViewItem"),
    DIP_PICKING("Geosteering.UI.Controls.DataTreeView.DataTree.DipPickingTreeViewItem"),
    DIP_SERIES("Geosteering.UI.Controls.DataTreeView.DataTree.DipSeriesTreeViewItem"),

    CONTACT("Geosteering.UI.Controls.DataTreeView.DataTree.FluidContactTreeViewItem"),

    ZONE_GROUP("Geosteering.UI.Controls.DataTreeView.DataTree.ZonesGroupTreeViewItem"),
    ZONE("Geosteering.UI.Controls.DataTreeView.DataTree.ZoneTreeViewItem"),

//    MARKERS("Geosteering.UI.Controls.DataTreeView.DataTree.WellStratigraphyTreeViewItem"), /*3.8*/
    MARKERS("Geosteering.UI.Controls.DataTreeView.DataTree.MarkersTreeViewItem"), /*3.7*/
    MARKER("Geosteering.UI.Controls.DataTreeView.DataTree.MarkerTreeViewItem"),

    SURFACE("Geosteering.UI.Controls.DataTreeView.DataTree.SurfaceTreeViewItem"),

    POLYGON("Geosteering.UI.Controls.DataTreeView.DataTree.PolygonTreeViewItem"),

    PICTURE("Geosteering.UI.Controls.DataTreeView.DataTree.PictureTreeViewItem"),

    PALETTE("Geosteering.UI.Controls.DataTreeView.DataTree.PalettesTreeViewItem"),

    MODULE("Geosteering.UI.Controls.DataTreeView.DataTree.ModuleTreeViewItem"),

    PATTERN("Geosteering.UI.Controls.DataTreeView.DataTree.PalettesTreeViewItem"),
    ;

    public final String folderSelector;

    SubFolderSelector(String folderSelector) {
        this.folderSelector = folderSelector;
    }
}
