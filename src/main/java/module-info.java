module javagraph {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    opens javagraph to javafx.fxml;

    exports javagraph;
    exports javagraph.nodeManager;
    exports javagraph.fileManager;
}
