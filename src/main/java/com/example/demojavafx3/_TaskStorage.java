package com.example.demojavafx3;

import java.util.ArrayList;
import java.util.List;

public class _TaskStorage {
    // Static list to store tasks
    private static List<String[]> tasks = new ArrayList<>();

    // Add a new task
    public static void addTask(String name, String date, String desc, String loc) {
        // Format: [date, name, description, location, isComplete]
        tasks.add(new String[]{date, name, desc, loc, "false"});
    }

    // Get all tasks
    public static List<String[]> getTasks() {
        return tasks;
    }

    // Get a specific task by index
    public static String[] getTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            return tasks.get(index);
        }
        return null;
    }

    // Update an existing task
    public static void updateTask(int index, String name, String date, String desc, String loc, boolean isComplete) {
        if (index >= 0 && index < tasks.size()) {
            tasks.set(index, new String[]{date, name, desc, loc, String.valueOf(isComplete)});
        }
    }

    // Remove a task
    public static void removeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
        }
    }
}