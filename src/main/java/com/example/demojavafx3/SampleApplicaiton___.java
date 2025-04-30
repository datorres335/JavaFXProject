package com.example.demojavafx3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

public class SampleApplicaiton___ extends Application {
    private Stage primaryStage;
    private final List<Task> taskList = new ArrayList<>();
    private final ListView<Task> taskListView = new ListView<>();
    private final int WIDTH = 800, HEIGHT = 500;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        this.primaryStage = stage;
        showMainMenu();
    }

    // ---------- MAIN MENU WITH FILTERS ----------
    private void showMainMenu() {
        BorderPane root = new BorderPane();

        // LEFT: Category Buttons
        VBox sidebar = new VBox(10);
        sidebar.setPadding(new Insets(10));
        sidebar.setPrefWidth(150);
        Button todayBtn = new Button("Today");
        Button upcomingBtn = new Button("Upcoming");
        Button importantBtn = new Button("Important");
        Button createBtn = new Button("Create Task");

        todayBtn.setMaxWidth(Double.MAX_VALUE);
        upcomingBtn.setMaxWidth(Double.MAX_VALUE);
        importantBtn.setMaxWidth(Double.MAX_VALUE);
        createBtn.setMaxWidth(Double.MAX_VALUE);

        todayBtn.setOnAction(e -> showTasksByCategory("Today"));
        upcomingBtn.setOnAction(e -> showTasksByCategory("Upcoming"));
        importantBtn.setOnAction(e -> showTasksByCategory("Important"));
        createBtn.setOnAction(e -> openTaskEditor(null));

        sidebar.getChildren().addAll(todayBtn, upcomingBtn, importantBtn, createBtn);
        root.setLeft(sidebar);

        // RIGHT: Task List View
        taskListView.setOnMouseClicked(e -> {
            Task selected = taskListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                openTaskEditor(selected);
            }
        });

        root.setCenter(taskListView);

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.setTitle("Scheduler App");
        primaryStage.show();

        showTasksByCategory("Today"); // default
    }

    // ---------- FILTERED TASK VIEW ----------
    private void showTasksByCategory(String category) {
        List<Task> filtered;
        LocalDate today = LocalDate.now();

        switch (category) {
            case "Today":
                filtered = taskList.stream()
                        .filter(t -> t.date.equals(today))
                        .collect(Collectors.toList());
                break;
            case "Upcoming":
                filtered = taskList.stream()
                        .filter(t -> t.date.isAfter(today))
                        .collect(Collectors.toList());
                break;
            case "Important":
                filtered = taskList.stream()
                        .filter(t -> isImportantColor(t.color))
                        .collect(Collectors.toList());
                break;
            default:
                filtered = new ArrayList<>(taskList);
        }

        taskListView.getItems().setAll(filtered);
    }

    private boolean isImportantColor(Color color) {
        return color.equals(Color.RED) || color.equals(Color.ORANGE) || color.equals(Color.GOLD);
    }

    // ---------- TASK EDITOR WINDOW ----------
    private void openTaskEditor(Task existing) {
        Stage window = new Stage();
        window.setTitle(existing == null ? "Create Task" : "Edit Task");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER_LEFT);

        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextArea descField = new TextArea();
        descField.setPromptText("Description");
        descField.setPrefRowCount(3);

        DatePicker datePicker = new DatePicker();

        ColorPicker colorPicker = new ColorPicker(Color.LIGHTGRAY);

        if (existing != null) {
            titleField.setText(existing.title);
            descField.setText(existing.description);
            datePicker.setValue(existing.date);
            colorPicker.setValue(existing.color);
        }

        Button saveBtn = new Button("Save");
        Button deleteBtn = new Button("Delete");
        Button cancelBtn = new Button("Cancel");
        HBox buttons = new HBox(10, saveBtn, deleteBtn, cancelBtn);

        Text statusText = new Text();

        saveBtn.setOnAction(e -> {
            String title = titleField.getText().trim();
            String desc = descField.getText().trim();
            LocalDate date = datePicker.getValue();
            Color color = colorPicker.getValue();

            if (title.isEmpty() || date == null) {
                statusText.setText("Title and date are required.");
                return;
            }

            if (existing != null) {
                existing.title = title;
                existing.description = desc;
                existing.date = date;
                existing.color = color;
            } else {
                taskList.add(new Task(title, desc, date, color));
            }

            showTasksByCategory("Today"); // refresh
            window.close();
        });

        deleteBtn.setOnAction(e -> {
            if (existing != null) {
                taskList.remove(existing);
                showTasksByCategory("Today");
            }
            window.close();
        });

        cancelBtn.setOnAction(e -> window.close());

        root.getChildren().addAll(
                new Label("Title:"), titleField,
                new Label("Description:"), descField,
                new Label("Due Date:"), datePicker,
                new Label("Color (importance):"), colorPicker,
                buttons,
                statusText
        );

        window.setScene(new Scene(root, 350, 400));
        window.show();
    }

    // ---------- TASK CLASS ----------
    private static class Task {
        String title;
        String description;
        LocalDate date;
        Color color;

        Task(String title, String description, LocalDate date, Color color) {
            this.title = title;
            this.description = description;
            this.date = date;
            this.color = color;
        }

        @Override
        public String toString() {
            return title + " - " + date.toString();
        }
    }
}