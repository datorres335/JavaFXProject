package com.example.demojavafx3;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApplication extends Application {

    private Stage primaryStage;
    private BorderPane mainLayout;

    // Components
    private _ListOfTasks listOfTasksComponent;
    private _AddATask addATaskComponent;
    private _CalenderPage calendarPageComponent;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Task Manager");

        // Initialize the main layout
        mainLayout = new BorderPane();
        mainLayout.getStyleClass().add("main-container");

        Scene scene = new Scene(mainLayout, 900, 650);

        // Apply global styles
        _AppStyles.applyStyles(scene);

        // Initialize components
        listOfTasksComponent = new _ListOfTasks(this);
        addATaskComponent = new _AddATask(this);
        calendarPageComponent = new _CalenderPage(this);

        // Set the initial view
        showTaskListView();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Methods to switch between views
    public void showTaskListView() {
        mainLayout.setCenter(listOfTasksComponent.getView());
        primaryStage.setTitle("Task Manager - Tasks");
        // Refresh the task list to ensure it's up to date
        listOfTasksComponent.refreshTaskList();
    }

    public void showAddTaskView(String[] taskToEdit) {
        // Set up the add task view for editing if a task is provided
        addATaskComponent.setupForEditing(taskToEdit);
        mainLayout.setCenter(addATaskComponent.getView());
        primaryStage.setTitle(taskToEdit == null ? "Task Manager - New Task" : "Task Manager - Edit Task");
    }

    // Overload for when no task is provided (new task)
    public void showAddTaskView() {
        showAddTaskView(null);
    }

    public void showCalendarView() {
        mainLayout.setCenter(calendarPageComponent.getView());
        primaryStage.setTitle("Task Manager - Calendar");
    }

    public static void main(String[] args) {
        launch(args);
    }
}