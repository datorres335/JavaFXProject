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
        view.getStyleClass().add("main-view");

        // Create header
        BorderPane header = createHeader();

        // Create calendar content
        BorderPane calendarContent = createCalendarContent();

        view.setTop(header);
        view.setCenter(calendarContent);
    }

    private BorderPane createHeader() {
        BorderPane header = new BorderPane();
        header.getStyleClass().add("header");

        // Title
        Label title = new Label("Calendar");
        title.getStyleClass().add("title-label");

        // Navigation buttons
        Button tasksBtn = new Button("View Tasks");
        tasksBtn.getStyleClass().addAll("button", "view-button");
        tasksBtn.setGraphic(createIcon("🔍"));
        tasksBtn.setOnAction(e -> mainApp.showTaskListView());

        Button addTaskBtn = new Button("Add Task");
        addTaskBtn.getStyleClass().addAll("button", "add-button");
        addTaskBtn.setGraphic(createIcon("➕"));
        addTaskBtn.setOnAction(e -> mainApp.showAddTaskView(null));

        HBox actionButtons = new HBox(15, tasksBtn, addTaskBtn);
        actionButtons.setAlignment(Pos.CENTER_RIGHT);

        header.setLeft(title);
        header.setRight(actionButtons);

        return header;
    }

    private BorderPane createCalendarContent() {
        // Use BorderPane instead of VBox for better layout control
        BorderPane content = new BorderPane();
        content.getStyleClass().add("calendar-content");
        content.setPadding(new Insets(20));

        // Top section with month/year selector and label
        VBox topSection = new VBox(10);
        topSection.setAlignment(Pos.CENTER);

        // Month and year selector
        HBox dateSelector = new HBox(15);
        dateSelector.getStyleClass().add("date-selector");
        dateSelector.setAlignment(Pos.CENTER);

        // Month selection combo box
        ComboBox<String> monthComboBox = new ComboBox<>();
        monthComboBox.setEditable(true);
        monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        monthComboBox.setValue(LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM")));
        monthComboBox.getStyleClass().add("month-selector");

        // Year selection text field
        TextField yearField = new TextField(String.valueOf(LocalDate.now().getYear()));
        yearField.setPrefWidth(100);
        yearField.setPromptText("YYYY");
        yearField.getStyleClass().add("year-selector");

        dateSelector.getChildren().addAll(monthComboBox, yearField);

        // Current month and year label
        Label monthYearLabel = new Label();
        monthYearLabel.getStyleClass().add("month-year-label");
        updateMonthYearLabel(monthYearLabel);

        topSection.getChildren().addAll(dateSelector, monthYearLabel);

        // Calendar view - this will expand to fill available space
        MonthView monthView = new MonthView();
        monthView.setShowToday(true);
        monthView.setShowWeekNumbers(false);
        monthView.getStyleClass().add("calendar-view");

        // Make the month view expand to fill available space
        monthView.setPrefHeight(Double.MAX_VALUE);
        monthView.setMaxHeight(Double.MAX_VALUE);

        // Add custom styling to the calendar
        monthView.setStyle(
                "-fx-background-color: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 5, 0, 0, 2);"
        );

        // Load custom CSS file for calendar styling
        String customCssPath = getClass().getResource("_calendar-styles.css").toExternalForm();
        monthView.getStylesheets().add(customCssPath);

        // Set up the BorderPane layout
        content.setTop(topSection);
        content.setCenter(monthView);

        // Add margin to the top section
        BorderPane.setMargin(topSection, new Insets(0, 0, 10, 0));

        // Set up month and year change handlers
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

        return content;
    }

    private Label createIcon(String text) {
        Label icon = new Label(text);
        icon.setStyle("-fx-font-size: 16px;");
        return icon;
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