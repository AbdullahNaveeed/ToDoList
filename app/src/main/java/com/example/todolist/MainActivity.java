package com.example.todolist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivity extends Activity {

	private EditText taskNameEditText, taskDescriptionEditText;
	private Button addTaskButton, viewTasksButton;
	private RecyclerView tasksRecyclerView;

	private TodoDBHelper dbHelper;
	private TodoAdapter taskAdapter;
	private ArrayList<Todo> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//		getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimary));

		taskNameEditText = findViewById(R.id.taskNameEditText);
		taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
		addTaskButton = findViewById(R.id.addTaskButton);
		viewTasksButton = findViewById(R.id.viewTasksButton);
		tasksRecyclerView = findViewById(R.id.tasksRecyclerView);

		dbHelper = new TodoDBHelper(this);
		taskList = new ArrayList<Todo>();



		addTaskButton.setOnClickListener(v -> {
			String name = taskNameEditText.getText().toString();
			String description = taskDescriptionEditText.getText().toString();
			String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
			String status = "Pending";

			if (!name.isEmpty() && !description.isEmpty()) {
				dbHelper.addTask(name, description, time, status);
				Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
				taskNameEditText.setText("");
				taskDescriptionEditText.setText("");
			} else {
				Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
			}
		});
		viewTasksButton.setOnClickListener(v -> {
			taskList.clear();
			Cursor cursor = dbHelper.getAllTasks();
			if (cursor.moveToFirst()) {
				do {
					taskList.add(new Todo(
							cursor.getInt(0),
							cursor.getString(1),
							cursor.getString(2),
							cursor.getString(3),
							cursor.getString(4)
					));
				} while (cursor.moveToNext());
			}
			cursor.close();

			taskAdapter = new TodoAdapter(this, taskList);
			tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
			tasksRecyclerView.setAdapter(taskAdapter);
		});

//		List<Todo> todos = todoDBHelper.getAllTasks();
//
//		recyclerView.setLayoutManager(new LinearLayoutManager(this));
//		todoAdapter =  new TodoAdapter(this, todos, todoDBHelper);
//		recyclerView.setAdapter(todoAdapter);

//		addButton.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View view) {
//					String text = addEditText.getText().toString();
//
//					if (!text.isEmpty()) {
//						long itemId = todoDBHelper.insertTodoItem(text, false);
//
//						if (itemId != -1) {
//							Todo newTodo = new Todo(text, false);
//							todoAdapter.addItem(newTodo);
//
//							int position = todoAdapter.getItemCount() - 1;
//							todoAdapter.notifyItemInserted(position);
//
//							Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_LONG).show();
//							addEditText.setText("");
//						} else {
//							Toast.makeText(getApplicationContext(), "Failed to add item", Toast.LENGTH_LONG).show();
//						}
//					} else {
//						Toast.makeText(getApplicationContext(), "Please enter something....", Toast.LENGTH_SHORT).show();
//					}
//				}
//			});
    }
}
