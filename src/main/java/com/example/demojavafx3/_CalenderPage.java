package com.example.demojavafx3;

import com.calendarfx.view.MonthView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class _CalenderPage {

    private MainApplication mainApp;
    private BorderPane view;
    private MonthView monthView;
    private StackPane calendarContainer;
    private Pane taskOverlay;

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
        monthView = new MonthView();
        monthView.setShowToday(true);
        monthView.setShowWeekNumbers(false);
        monthView.getStyleClass().add("calendar-view");

        // Make the month view expand to fill available space
        monthView.setPrefHeight(Double.MAX_VALUE);
        monthView.setMaxHeight(Double.MAX_VALUE);

        // Create a task overlay pane that will sit on top of the calendar
        taskOverlay = new Pane();
        taskOverlay.setMouseTransparent(true); // Allow clicks to pass through to the calendar

        // Create a stack pane to hold both the calendar and the task overlay
        calendarContainer = new StackPane(monthView, taskOverlay);

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
        content.setCenter(calendarContainer);

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

            // Refresh the calendar to show tasks for the new month
            refreshCalendar();
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

                    // Refresh the calendar to show tasks for the new month/year
                    refreshCalendar();
                } catch (Exception e) {
                    yearField.setText(oldValue);
                }
            }
        });

        // Add a listener to refresh tasks when the calendar is resized
        monthView.widthProperty().addListener((obs, oldVal, newVal) -> refreshCalendar());
        monthView.heightProperty().addListener((obs, oldVal, newVal) -> refreshCalendar());

        // Initial task display
        Platform.runLater(this::refreshCalendar);

        return content;
    }

    /**
     * Refresh the calendar to update task previews
     */
    public void refreshCalendar() {
        // Clear existing task labels
        taskOverlay.getChildren().clear();

        // Wait for the calendar to be fully laid out
        Platform.runLater(() -> {
            // Get the current month and year
            LocalDate currentDate = monthView.getDate();
            YearMonth yearMonth = YearMonth.of(currentDate.getYear(), currentDate.getMonth());

            // Get all tasks
            List<String[]> tasks = _TaskStorage.getTasks();

            // For each day in the month
            for (int day = 1; day <= yearMonth.lengthOfMonth(); day++) {
                LocalDate date = LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(), day);

                // Find tasks for this date
                int taskCount = 0;
                for (String[] task : tasks) {
                    try {
                        // Parse the task date (format: yyyy-MM-dd)
                        LocalDate taskDate = LocalDate.parse(task[0]);

                        // If the task date matches this day
                        if (taskDate.equals(date)) {
                            // Create and position a task label
                            addTaskLabelToOverlay(date, task, taskCount);
                            taskCount++;

                            // Limit to 3 tasks per day
                            if (taskCount >= 3) {
                                // Add a "+more" indicator
                                int moreCount = getTaskCountForDate(date) - 3;
                                if (moreCount > 0) {
                                    addMoreLabelToOverlay(date, moreCount);
                                }
                                break;
                            }
                        }
                    } catch (Exception e) {
                        // Skip tasks with invalid dates
                        continue;
                    }
                }
            }
        });
    }

    /**
     * Add a task label to the overlay at the correct position
     */
    private void addTaskLabelToOverlay(LocalDate date, String[] task, int position) {
        // Find the cell position for this date
        double[] cellBounds = findCellBounds(date);
        if (cellBounds == null) return;

        double cellX = cellBounds[0];
        double cellY = cellBounds[1];
        double cellWidth = cellBounds[2];
        double cellHeight = cellBounds[3];

        // Create the task label
        Label taskLabel = new Label(task[1]);
        taskLabel.getStyleClass().add("task-preview");

        // Add completed style if the task is completed
        boolean isCompleted = task.length > 4 && Boolean.parseBoolean(task[4]);
        if (isCompleted) {
            taskLabel.getStyleClass().add("task-completed");
        }

        // Truncate long task names
        if (task[1].length() > 15) {
            taskLabel.setText(task[1].substring(0, 12) + "...");
        }

        // Style the label
        taskLabel.setBackground(new Background(new BackgroundFill(
                isCompleted ? Color.LIGHTGRAY : Color.valueOf("#d4edda"),
                new CornerRadii(3),
                Insets.EMPTY
        )));

        taskLabel.setPadding(new Insets(2, 4, 2, 4));
        taskLabel.setBorder(new Border(new BorderStroke(
                isCompleted ? Color.GRAY : Color.valueOf("#28a745"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(3),
                new BorderWidths(1)
        )));

        // Position the label within the cell
        taskLabel.setLayoutX(cellX + 5);
        taskLabel.setLayoutY(cellY + 25 + (position * 20)); // 25px from top, 20px per task
        taskLabel.setPrefWidth(cellWidth - 40);
        taskLabel.setMaxWidth(cellWidth - 40);

        // Make the task clickable
        taskLabel.setOnMouseClicked(e -> {
            mainApp.showAddTaskView(task);
            e.consume();
        });

        // Add to overlay
        taskOverlay.getChildren().add(taskLabel);
    }

    /**
     * Add a "+more" label to the overlay
     */
    private void addMoreLabelToOverlay(LocalDate date, int moreCount) {
        // Find the cell position for this date
        double[] cellBounds = findCellBounds(date);
        if (cellBounds == null) return;

        double cellX = cellBounds[0];
        double cellY = cellBounds[1];
        double cellWidth = cellBounds[2];
        double cellHeight = cellBounds[3];

        // Create the "more" label
        Label moreLabel = new Label("+" + moreCount + " more");
        moreLabel.getStyleClass().add("task-more-label");

        // Style the label
        moreLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #7f8c8d; -fx-font-style: italic;");

        // Position the label
        moreLabel.setLayoutX(cellX + 5);
        moreLabel.setLayoutY(cellY + 40 + (3 * 20)); // Below the 3rd task

        // Add to overlay
        taskOverlay.getChildren().add(moreLabel);
    }

    /**
     * Find the bounds of a calendar cell for a specific date
     * Returns [x, y, width, height] or null if not found
     */
    private double[] findCellBounds(LocalDate date) {
        // This is a simplified approach - we'll calculate the position based on the calendar layout

        // Get the calendar dimensions
        double calendarWidth = monthView.getWidth();
        double calendarHeight = monthView.getHeight();

        // Get the current month view
        LocalDate firstOfMonth = monthView.getDate().withDayOfMonth(1);

        // Calculate the day of week for the first day of the month (0 = Sunday, 6 = Saturday)
        int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue() % 7;

        // Calculate the position of the date in the grid
        int dayOfMonth = date.getDayOfMonth();
        int position = firstDayOfWeek + dayOfMonth - 1;

        // Calculate row and column
        int row = position / 7;
        int col = position % 7;

        // Calculate the number of weeks in the view (usually 6)
        int numWeeks = 6;

        // Calculate cell dimensions
        double cellWidth = calendarWidth / 7;
        double cellHeight = calendarHeight / numWeeks;

        // Calculate cell position
        double cellX = col * cellWidth;
        double cellY = row * cellHeight;

        return new double[] { cellX, cellY, cellWidth, cellHeight };
    }

    /**
     * Count the number of tasks for a specific date
     */
    private int getTaskCountForDate(LocalDate date) {
        List<String[]> tasks = _TaskStorage.getTasks();
        int count = 0;

        for (String[] task : tasks) {
            try {
                LocalDate taskDate = LocalDate.parse(task[0]);
                if (taskDate.equals(date)) {
                    count++;
                }
            } catch (Exception e) {
                // Skip invalid dates
            }
        }

        return count;
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
        // Refresh the calendar when the view is shown
        Platform.runLater(this::refreshCalendar);
        return view;
    }
}