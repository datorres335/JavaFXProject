package com.example.demojavafx3;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.demojavafx3._ListOfTasks.updateTaskList;

public class _AddATask {

    private MainApplication mainApp;
    private BorderPane view;

    // Form fields
    private TextField nameField;
    private DatePicker datePicker;
    private TextArea descArea;
    private TextField locField;
    private Button submitButton;
    private CheckBox completeCheck;

    // Editing state
    private boolean isEditing = false;
    private int editingTaskIndex = -1;

    public _AddATask(MainApplication mainApp) {
        this.mainApp = mainApp;
        createView();
    }

    private void createView() {
        view = new BorderPane();
        view.getStyleClass().add("main-view");

        // Create header
        BorderPane header = createHeader();

        // Create form
        VBox form = createForm();

        view.setTop(header);
        view.setCenter(form);
    }

    private BorderPane createHeader() {
        BorderPane header = new BorderPane();
        header.getStyleClass().add("header");

        // Title
        Label title = new Label("New Task");
        title.getStyleClass().add("title-label");
        title.setId("formTitle"); // For updating when editing

        // Navigation buttons
        Button backBtn = new Button("Back to Tasks");
        backBtn.getStyleClass().addAll("button", "view-button");
        backBtn.setGraphic(createIcon("🔙"));
        backBtn.setOnAction(e -> mainApp.showTaskListView());

        Button calendarBtn = new Button("Calendar");
        calendarBtn.getStyleClass().addAll("button", "view-button");
        calendarBtn.setGraphic(createIcon("📅"));
        calendarBtn.setOnAction(e -> mainApp.showCalendarView());

        // Task actions (only visible when editing)
        completeCheck = new CheckBox("Mark as Complete");
        completeCheck.getStyleClass().add("complete-check");

        Button deleteBtn = new Button("");
        deleteBtn.getStyleClass().addAll("button", "delete-button");
        deleteBtn.setGraphic(createIcon("🗑"));
        Tooltip deleteTooltip = new Tooltip("Delete task");
        Tooltip.install(deleteBtn, deleteTooltip);

        // Set up delete button action
        deleteBtn.setOnAction(e -> {
            if (isEditing && editingTaskIndex >= 0) {
                // Confirm deletion
                Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmAlert.setTitle("Confirm Delete");
                confirmAlert.setHeaderText("Delete Task");
                confirmAlert.setContentText("Are you sure you want to delete this task?");

                confirmAlert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        // Delete the task
                        _TaskStorage.removeTask(editingTaskIndex);
                        updateTaskList();
                        mainApp.showTaskListView();
                    }
                });
            }
        });

        HBox taskActions = new HBox(15, completeCheck, deleteBtn);
        taskActions.setAlignment(Pos.CENTER_RIGHT);
        taskActions.setVisible(false); // Only show when editing
        taskActions.setId("taskActions");

        VBox rightSide = new VBox(15);
        rightSide.getChildren().addAll(
                new HBox(15, backBtn, calendarBtn),
                taskActions
        );
        rightSide.setAlignment(Pos.TOP_RIGHT);

        header.setLeft(title);
        header.setRight(rightSide);

        return header;
    }

    private VBox createForm() {
        VBox form = new VBox(20);
        form.getStyleClass().add("form");
        form.setPadding(new Insets(30));

        // Task name field
        Label nameLabel = new Label("Task Name");
        nameLabel.getStyleClass().add("form-label");
        nameField = new TextField();
        nameField.setPromptText("Enter task name");
        nameField.getStyleClass().add("text-field");

        // Date field
        Label dateLabel = new Label("Date");
        dateLabel.getStyleClass().add("form-label");
        datePicker = new DatePicker();
        datePicker.setPromptText("Select date");
        datePicker.getStyleClass().add("date-picker");

        // Description field
        Label descLabel = new Label("Description");
        descLabel.getStyleClass().add("form-label");
        descArea = new TextArea();
        descArea.setPromptText("Enter task description");
        descArea.setPrefRowCount(4);
        descArea.getStyleClass().add("text-area");

        // Location field
        Label locLabel = new Label("Location");
        locLabel.getStyleClass().add("form-label");
        locField = new TextField();
        locField.setPromptText("Enter location (optional)");
        locField.getStyleClass().add("text-field");

        // Submit button
        submitButton = new Button("Create Task");
        submitButton.getStyleClass().addAll("button", "add-button");
        submitButton.setPrefWidth(150);

        // Button container
        HBox buttonContainer = new HBox(submitButton);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(20, 0, 0, 0));

        // Add all elements to form
        form.getChildren().addAll(
                createFormGroup(nameLabel, nameField),
                createFormGroup(dateLabel, datePicker),
                createFormGroup(descLabel, descArea),
                createFormGroup(locLabel, locField),
                buttonContainer
        );

        // Add button click handler
        submitButton.setOnAction(event -> {
            if (nameField.getText().isEmpty() || datePicker.getValue() == null) {
                showErrorAlert("Missing Required Fields",
                        "Please fill in all required fields (Task Name and Date)");
                return;
            }

            // Get form data
            String name = nameField.getText();
            String date = datePicker.getValue().toString();
            String desc = descArea.getText();
            String loc = locField.getText();
            boolean isComplete = completeCheck.isSelected();

            if (isEditing && editingTaskIndex >= 0) {
                // Update existing task
                _TaskStorage.updateTask(editingTaskIndex, name, date, desc, loc, isComplete);
                showSuccessAlert("Task Updated", "Task updated successfully!");
            } else {
                // Save new task
                _TaskStorage.addTask(name, date, desc, loc);
                showSuccessAlert("Task Created", "New task created successfully!");
            }

            updateTaskList(); // Update the task list in the main view

            // Clear form and reset state
            clearForm();

            // Switch back to the task list view
            mainApp.showTaskListView();
        });

        return form;
    }

    private VBox createFormGroup(Label label, Control field) {
        VBox group = new VBox(8);
        group.getChildren().addAll(label, field);
        return group;
    }

    private Label createIcon(String text) {
        Label icon = new Label(text);
        icon.setStyle("-fx-font-size: 16px;");
        return icon;
    }

    private void showErrorAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showSuccessAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Method to set up the form for editing an existing task
    public void setupForEditing(String[] taskToEdit) {
        clearForm(); // Clear any previous data

        if (taskToEdit == null) {
            // New task
            isEditing = false;
            editingTaskIndex = -1;
            submitButton.setText("Create Task");

            // Update UI elements
            Label titleLabel = (Label) view.lookup("#formTitle");
            if (titleLabel != null) {
                titleLabel.setText("New Task");
            }

            HBox taskActions = (HBox) view.lookup("#taskActions");
            if (taskActions != null) {
                taskActions.setVisible(false);
            }

            return;
        }

        // We're editing an existing task
        isEditing = true;

        // Find the task index in the storage
        List<String[]> tasks = _TaskStorage.getTasks();
        for (int i = 0; i < tasks.size(); i++) {
            String[] task = tasks.get(i);
            // Compare task data to find the matching task
            if (task[0].equals(taskToEdit[0]) && task[1].equals(taskToEdit[1])) {
                editingTaskIndex = i;
                break;
            }
        }

        if (editingTaskIndex >= 0) {
            // Update UI elements
            Label titleLabel = (Label) view.lookup("#formTitle");
            if (titleLabel != null) {
                titleLabel.setText("Edit Task");
            }

            HBox taskActions = (HBox) view.lookup("#taskActions");
            if (taskActions != null) {
                taskActions.setVisible(true);
            }

            // Fill the form with task data
            String[] task = _TaskStorage.getTask(editingTaskIndex);

            // Assuming task format: [date, name, description, location, isComplete]
            nameField.setText(task[1]); // Name

            // Parse the date string to LocalDate
            try {
                LocalDate date = LocalDate.parse(task[0], DateTimeFormatter.ISO_DATE);
                datePicker.setValue(date);
            } catch (Exception e) {
                // Handle date parsing error
                datePicker.setValue(LocalDate.now());
            }

            descArea.setText(task[2]); // Description
            locField.setText(task[3]); // Location

            // Set completion status if available
            if (task.length > 4) {
                completeCheck.setSelected(Boolean.parseBoolean(task[4]));
            } else {
                completeCheck.setSelected(false);
            }

            submitButton.setText("Update Task");
        }
    }

    private void clearForm() {
        nameField.clear();
        datePicker.setValue(null);
        descArea.clear();
        locField.clear();
        completeCheck.setSelected(false);
    }

    public BorderPane getView() {
        return view;
    }
}