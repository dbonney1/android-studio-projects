package com.example.hw02;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.Objects;

public class Task implements Serializable {
    String name;
    String date;
    String priority;

    public Task(String name, String date, String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    public Task() {

    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(name, task.name) &&
                Objects.equals(date, task.date) &&
                Objects.equals(priority, task.priority);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(name, date, priority);
    }
}
