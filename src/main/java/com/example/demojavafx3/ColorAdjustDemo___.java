package com.example.demojavafx3;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;

/**
 *  An Image Demo with labeled sliders
 */

public class ColorAdjustDemo___ extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        final double MIN = -1.0, MAX = 1.0, INITIAL = 0.0;

        ColorAdjust colorAdjust = new ColorAdjust();

        Image image = new Image(getClass().getResource("/com/example/demojavafx3/flower.jpg").toExternalForm());//Image
        // ("file:flower.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(400);
        imageView.setPreserveRatio(true);
        imageView.setEffect(colorAdjust);

        // === Hue ===
        Label hueLabel = new Label("Hue");
        Slider hueSlider = new Slider(MIN, MAX, INITIAL);
        hueSlider.setShowTickMarks(true);
        hueSlider.setShowTickLabels(true);
        hueSlider.setMajorTickUnit(0.25f);
        hueSlider.setBlockIncrement(0.1f);
        hueSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                colorAdjust.setHue(newVal.doubleValue()));
        VBox hueBox = new VBox(5, hueLabel, hueSlider);

        // === Saturation ===
        Label satLabel = new Label("Saturation");
        Slider satSlider = new Slider(MIN, MAX, INITIAL);
        satSlider.setShowTickMarks(true);
        satSlider.setShowTickLabels(true);
        satSlider.setMajorTickUnit(0.25f);
        satSlider.setBlockIncrement(0.1f);
        satSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                colorAdjust.setSaturation(newVal.doubleValue()));
        VBox satBox = new VBox(5, satLabel, satSlider);

        // === Brightness ===
        Label brightLabel = new Label("Brightness");
        Slider brightSlider = new Slider(MIN, MAX, INITIAL);
        brightSlider.setShowTickMarks(true);
        brightSlider.setShowTickLabels(true);
        brightSlider.setMajorTickUnit(0.25f);
        brightSlider.setBlockIncrement(0.1f);
        brightSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                colorAdjust.setBrightness(newVal.doubleValue()));
        VBox brightBox = new VBox(5, brightLabel, brightSlider);

        // === Contrast ===
        Label contrastLabel = new Label("Contrast");
        Slider contrastSlider = new Slider(MIN, MAX, INITIAL);
        contrastSlider.setShowTickMarks(true);
        contrastSlider.setShowTickLabels(true);
        contrastSlider.setMajorTickUnit(0.25f);
        contrastSlider.setBlockIncrement(0.1f);
        contrastSlider.valueProperty().addListener((obs, oldVal, newVal) ->
                colorAdjust.setContrast(newVal.doubleValue()));
        VBox contrastBox = new VBox(5, contrastLabel, contrastSlider);

        // Layout for image
        HBox hbox = new HBox(imageView);
        hbox.setAlignment(Pos.CENTER);

        // Layout for sliders with labels
        VBox vbox = new VBox(10, hueBox, satBox, brightBox, contrastBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        // Combine everything
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(hbox);
        borderPane.setBottom(vbox);

        Scene scene = new Scene(borderPane, 600, 600); // Width x Height
        primaryStage.setScene(scene);
        primaryStage.setTitle("Flower");
        primaryStage.show();
    }
}
