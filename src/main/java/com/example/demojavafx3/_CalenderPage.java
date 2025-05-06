package com.example.demojavafx3;

import com.calendarfx.view.MonthView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class _CalenderPage {

    private MainApplication mainApp;
    private BorderPane view;

    public _CalenderPage(MainApplication mainApp) {
        this.mainApp = mainApp;
        createView();
    }

    private void createView() {
        view = new BorderPane();

        // Month selection combo box
        ComboBox<String> monthComboBox = new ComboBox<>();
        monthComboBox.setEditable(true);
        monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        monthComboBox.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM")));

        // year selection text field
        TextField yearField = new TextField(String.valueOf(LocalDate.now().getYear()));
        yearField.setPrefWidth(80); // Make it smaller than default
        yearField.setPromptText("YYYY");

        HBox titleBox = new HBox(10, monthComboBox, yearField);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        // Top right buttons
        Button viewTasksBtn = new Button("🔍 View All Tasks");
        viewTasksBtn.setOnAction(e -> mainApp.showTaskListView());

        Button addTaskBtn = new Button("➕   Add a Task   ");
        addTaskBtn.setOnAction(e -> mainApp.showAddTaskView());

        VBox rightButtons = new VBox(10, viewTasksBtn, addTaskBtn);
        rightButtons.setAlignment(Pos.CENTER_RIGHT);

        // Header layout
        BorderPane header = new BorderPane();
        header.setLeft(titleBox);
        header.setRight(rightButtons);
        header.setPadding(new Insets(10, 20, 10, 20));

        // Current month and year label
        Label monthYearLabel = new Label();
        monthYearLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        updateMonthYearLabel(monthYearLabel);

        // Create the CalendarFX month view
        MonthView monthView = new MonthView();
        monthView.setShowToday(true);
        monthView.setShowWeekNumbers(false);

        // Layout setup
        VBox topSection = new VBox(10, header);
        view.setTop(topSection);

        VBox centerContent = new VBox(10, monthYearLabel, monthView);
        centerContent.setAlignment(Pos.TOP_CENTER);
        view.setCenter(centerContent);

        monthComboBox.setOnAction(event -> {
            String selectedMonth = monthComboBox.getValue();
            int year = Integer.parseInt(yearField.getText());

            LocalDate selectedDate = LocalDate.parse(
                    String.format("%d-%02d-01",
                            year,
                            Month.valueOf(selectedMonth.toUpperCase()).getValue()
                    ));

            monthView.setDate(selectedDate);
            monthYearLabel.setText(selectedDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        });

        // Update the Timeline to preserve manual year selection
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            if (monthComboBox.getValue().equals(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM")))
                    && yearField.getText().equals(String.valueOf(LocalDate.now().getYear()))) {
                updateMonthYearLabel(monthYearLabel);
                monthView.setDate(LocalDate.now());
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Listener to validate and handle year input
        yearField.textProperty().addListener((observable, oldValue, newValue) -> {
            // Only allow digits
            if (!newValue.matches("\\d*")) {
                yearField.setText(newValue.replaceAll("[^\\d]", ""));
                return;
            }

            // Limit to 4 digits
            if (newValue.length() > 4) {
                yearField.setText(oldValue);
                return;
            }

            // Update calendar when valid year is entered
            if (newValue.length() == 4) {
                try {
                    int year = Integer.parseInt(newValue);
                    String selectedMonth = monthComboBox.getValue();

                    // Create date with selected month and year
                    LocalDate selectedDate = LocalDate.parse(
                            String.format("%d-%02d-01",
                                    year,
                                    Month.valueOf(selectedMonth.toUpperCase()).getValue()
                            ));

                    // Update the month view
                    monthView.setDate(selectedDate);

                    // Update the label
                    monthYearLabel.setText(selectedDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
                } catch (Exception e) {
                    yearField.setText(oldValue);
                }
            }
        });

        BorderPane.setMargin(topSection, new Insets(10));
        BorderPane.setMargin(centerContent, new Insets(10));
    }

    private void updateMonthYearLabel(Label label) {
        LocalDate now = LocalDate.now();
        String formattedDate = now.format(DateTimeFormatter.ofPattern("MMMM yyyy"));
        label.setText(formattedDate);
    }

    public BorderPane getView() {
        return view;
    }
}