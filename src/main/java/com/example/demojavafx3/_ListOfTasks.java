package com.example.demojavafx3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class _ListOfTasks {

    private MainApplication mainApp;
    private BorderPane view;
    private static VBox taskList;
    private static _ListOfTasks instance;

    public _ListOfTasks(MainApplication mainApp) {
        this.mainApp = mainApp;
        instance = this;
        createView();
    }

    private void createView() {
        view = new BorderPane();
        view.getStyleClass().add("main-view");

        // Create header with modern design
        BorderPane header = createHeader();

        // Create task list container
        taskList = new VBox(10);
        taskList.getStyleClass().add("task-list");
        taskList.setPadding(new Insets(20));

        // Create scroll pane for task list
        ScrollPane scrollPane = new ScrollPane(taskList);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("task-scroll-pane");

        // Add empty state message
        Label emptyStateLabel = new Label("No tasks yet. Click the '+' button to add your first task!");
        emptyStateLabel.setTextAlignment(TextAlignment.CENTER);
        emptyStateLabel.getStyleClass().add("empty-state-label");
        emptyStateLabel.setVisible(false);

        // Create a stack pane to show either the task list or empty state
        StackPane centerContent = new StackPane(scrollPane, emptyStateLabel);

        view.setTop(header);
        view.setCenter(centerContent);

        // Load tasks
        refreshTaskList();

        // Show empty state if no tasks
        if (_TaskStorage.getTasks().isEmpty()) {
            emptyStateLabel.setVisible(true);
        }
    }

    private BorderPane createHeader() {
        BorderPane header = new BorderPane();
        header.getStyleClass().add("header");

        // Title with icon
        Label title = new Label("My Tasks");
        title.getStyleClass().add("title-label");

        // Action buttons
        Button calendarViewBtn = new Button("Calendar View");
        calendarViewBtn.getStyleClass().addAll("button", "view-button");
        calendarViewBtn.setGraphic(createIcon("📅"));
        calendarViewBtn.setOnAction(e -> mainApp.showCalendarView());

        Button addTaskBtn = new Button("Add Task");
        addTaskBtn.getStyleClass().addAll("button", "add-button");
        addTaskBtn.setGraphic(createIcon("➕"));
        addTaskBtn.setOnAction(e -> mainApp.showAddTaskView(null));

        HBox actionButtons = new HBox(15, calendarViewBtn, addTaskBtn);
        actionButtons.setAlignment(Pos.CENTER_RIGHT);

        header.setLeft(title);
        header.setRight(actionButtons);

        return header;
    }

    private Label createIcon(String text) {
        Label icon = new Label(text);
        icon.setStyle("-fx-font-size: 16px;");
        return icon;
    }

    public BorderPane getView() {
        return view;
    }

    public void refreshTaskList() {
        taskList.getChildren().clear();
        List<String[]> tasks = _TaskStorage.getTasks();

        // Show or hide empty state message
        Node emptyStateLabel = ((StackPane) view.getCenter()).getChildren().get(1);
        emptyStateLabel.setVisible(tasks.isEmpty());

        for (int i = 0; i < tasks.size(); i++) {
            String[] task = tasks.get(i);
            final int taskIndex = i;

            // Create a card for each task
            VBox taskCard = new VBox(8);
            taskCard.getStyleClass().add("task-card");

            // Format date
            String dateStr = task[0];
            try {
                LocalDate date = LocalDate.parse(dateStr);
                dateStr = date.format(DateTimeFormatter.ofPattern("MMM d, yyyy"));
            } catch (Exception e) {
                // Use the original string if parsing fails
            }

            // Task date
            Label dateLabel = new Label(dateStr);
            dateLabel.getStyleClass().add("task-date");

            // Task name
            Label nameLabel = new Label(task[1]);
            nameLabel.getStyleClass().add("task-name");

            // Task description (if available)
            VBox contentBox = new VBox(5);
            if (task[2] != null && !task[2].isEmpty()) {
                Label descLabel = new Label(task[2]);
                descLabel.getStyleClass().add("task-description");
                descLabel.setWrapText(true);
                contentBox.getChildren().add(descLabel);
            }

            // Task location (if available)
            if (task[3] != null && !task[3].isEmpty()) {
                Label locLabel = new Label(task[3]);
                locLabel.getStyleClass().add("task-location");
                locLabel.setGraphic(createIcon("📍"));
                contentBox.getChildren().add(locLabel);
            }

            // Check if task is completed
            boolean isCompleted = task.length > 4 && Boolean.parseBoolean(task[4]);
            if (isCompleted) {
                nameLabel.getStyleClass().add("task-completed");
                Label completedLabel = new Label("Completed");
                completedLabel.getStyleClass().add("task-completed-label");
                contentBox.getChildren().add(completedLabel);
            }

            // Add all elements to the card
            taskCard.getChildren().addAll(dateLabel, nameLabel, contentBox);

            // Make the card clickable
            taskCard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                mainApp.showAddTaskView(task);
            });

            taskList.getChildren().add(taskCard);
        }
    }

    // Static method for updating from other classes
    public static void updateTaskList() {
        if (taskList != null) {
            if (instance != null) {
                instance.refreshTaskList();
            } else {
                // Fallback if no instance is available
                taskList.getChildren().clear();
                List<String[]> tasks = _TaskStorage.getTasks();

                for (String[] task : tasks) {
                    HBox row = new HBox(10);
                    row.setAlignment(Pos.CENTER_LEFT);
                    row.setPadding(new Insets(5));
                    row.getStyleClass().add("task-row");

                    Label date = new Label(task[0]);
                    Label name = new Label(task[1]);

                    row.getChildren().addAll(date, name);
                    taskList.getChildren().add(row);
                }
            }
        }
    }
}