package com.example.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public  class TodoDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "TodoList.db";
    private static final int DATABASE_VERSION = 1;
    public TodoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE tasks (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "description TEXT," +
                "time TEXT," +
                "status TEXT)");
    }
    public boolean addTask(String name, String description, String time, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("time", time);
        values.put("status", status);
        long result = db.insert("tasks", null, values);
        db.close();
        return result != -1;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks;");
        onCreate(db);
    }
    public List<Todo> getAllTasks() {
        List<Todo> todoItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String time = cursor.getString(3);
                String status = cursor.getString(4);

                Todo todoItem = new Todo(id,name,description,time,status);
                todoItem.setId(id);
                todoItem.setName(name);
                todoItem.setDescription(description);
                todoItem.setTime(time);
                todoItem.setStatus(status);

                todoItems.add(todoItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return todoItems;

    }
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tasks", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }
    public void updateTask(int id, String name, String description, String time, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("description", description);
        values.put("time", time);
        values.put("status", status);
        db.update("tasks", values, "id=?", new String[]{String.valueOf(id)});
    }

    public List<Todo>  getCompletedTasks() {
        List<Todo> todoItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks WHERE status='Completed'", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String time = cursor.getString(3);
                String status = cursor.getString(4);

                Todo todoItem = new Todo(id,name,description,time,status);
                todoItem.setId(id);
                todoItem.setName(name);
                todoItem.setDescription(description);
                todoItem.setTime(time);
                todoItem.setStatus(status);

                todoItems.add(todoItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return todoItems;

    }
    public List<Todo>  getPendingTasks() {
        List<Todo> todoItems = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tasks WHERE status='Pending'", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String time = cursor.getString(3);
                String status = cursor.getString(4);

                Todo todoItem = new Todo(id,name,description,time,status);
                todoItem.setId(id);
                todoItem.setName(name);
                todoItem.setDescription(description);
                todoItem.setTime(time);
                todoItem.setStatus(status);

                todoItems.add(todoItem);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return todoItems;

    }


}
