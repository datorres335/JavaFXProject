package com.example.demojavafx3;

import java.util.ArrayList;
import java.util.List;

public class _TaskStorage {
    public static List<String[]> tasks = new ArrayList<>();

    public static void addTask(String name, String date, String desc, String loc) {
        tasks.add(new String[]{date, name + " - " + desc + " @ " + loc});
    }

    public static List<String[]> getTasks() {
        return tasks;
    }
}
