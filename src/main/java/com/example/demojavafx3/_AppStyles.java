package com.example.demojavafx3;

import javafx.scene.Scene;

public class _AppStyles {

    // Color palette
    public static final String PRIMARY_COLOR = "#3498db";
    public static final String SECONDARY_COLOR = "#2ecc71";
    public static final String ACCENT_COLOR = "#9b59b6";
    public static final String BACKGROUND_COLOR = "#f8f9fa";
    public static final String CARD_COLOR = "#ffffff";
    public static final String TEXT_COLOR = "#2c3e50";
    public static final String TEXT_SECONDARY_COLOR = "#7f8c8d";

    // Apply styles to the entire application
    public static void applyStyles(Scene scene) {
        scene.getStylesheets().add(_AppStyles.class.getResource("_styles.css").toExternalForm());
    }
}