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
        return result != -1; // Return true if insert is successful
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Todos;");
        onCreate(db);
    }
    public Cursor getAllTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tasks", null);
    }
    public void deleteTask(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("tasks", "id=?", new String[]{String.valueOf(id)});
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
    public Cursor getCompletedTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tasks WHERE status='Completed'", null);
    }
    public Cursor getPendingTasks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM tasks WHERE status='Pending'", null);
    }
//    public List<Todo> getAllTodoItems() {
//        List<Todo> todoItems = new ArrayList<>();
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM Todos", null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                long id = cursor.getLong(cursor.getInt(0));
//                String name = cursor.getString(cursor.getInt(1));
//                int description = cursor.getInt(cursor.getInt(2));
//                int time = cursor.getInt(cursor.getInt(3));
//                int status = cursor.getInt(cursor.getInt(4));
//                boolean completed = completedInt == 1;
//
//                Todo todoItem = new Todo(task, completed);
//                todoItem.setId(id);
//                todoItem.setCompleted(completed);
//
//                todoItems.add(todoItem);
//            } while (cursor.moveToNext());
//        }
//
//        cursor.close();
//        db.close();
//        return todoItems;
//    }
//
//    public long insertTodoItem(String name, String description, String time, String status) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("name", name);
//        values.put("description", description);
//        values.put("time", time);
//        values.put("status", status);
//        long itemId = db.insert("Todos", null, values);
//        db.close();
//        return itemId;
//    }
//
//
//
//    public int updateTodoItemStatus(long itemId, boolean completed) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("completed", completed ? 1 : 0);
//        int rowsAffected = db.update("Todos", values, "_id=?", new String[]{String.valueOf(itemId)});
//        db.close();
//        return rowsAffected;
//    }
//
//    public void deleteTodoItem(long todoId) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.delete("Todos", "_id" +" = ?", new String[]{String.valueOf(todoId)});
//        db.close();
//    }
}
