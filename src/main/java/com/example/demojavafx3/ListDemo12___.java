package com.example.demojavafx3;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ListDemo12___ extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Create the ListView and add items
        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(
                "Monday", "Tuesday", "Wednesday",
                "Thursday", "Friday", "Saturday", "Sunday"
        );

        // Set orientation and preferred size
        listView.setOrientation(Orientation.HORIZONTAL);
        listView.setPrefSize(400, 60);

        // Enable multiple selection
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Label to show selected items
        Label selectionLabel = new Label("Select days...");

        // Add listener for selection changes
        listView.getSelectionModel().getSelectedItems().addListener((javafx.collections.ListChangeListener<String>) change -> {
            ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
            ObservableList<Integer> selectedIndices = listView.getSelectionModel().getSelectedIndices();

            StringBuilder sb = new StringBuilder("Selected items: ");
            for (int i = 0; i < selectedItems.size(); i++) {
                sb.append(selectedItems.get(i));
                if (i < selectedItems.size() - 1) sb.append(", ");
            }
            selectionLabel.setText(sb.toString());
        });

        // Layout
        VBox root = new VBox(10, listView, selectionLabel);
        Scene scene = new Scene(root, 500, 150);

        // Stage setup
        primaryStage.setTitle("Horizontal ListView with Multi-Selection");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
