package com.example.todolist;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.util.Log;
import android.view.View;
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
	private List<Todo> taskList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimary));
		taskNameEditText = findViewById(R.id.taskNameEditText);
		taskDescriptionEditText = findViewById(R.id.taskDescriptionEditText);
		addTaskButton = findViewById(R.id.addTaskButton);
		viewTasksButton = findViewById(R.id.viewTasksButton);
		tasksRecyclerView = findViewById(R.id.tasksRecyclerView);





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
				Toast.makeText(this, "Name: "+name+"\nDescription: "+description+"\nTime: "+time+"\n Status: "+status, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
			}
		});

		viewTasksButton.setOnClickListener(v -> {
			taskList.clear();
			taskList.addAll(dbHelper.getAllTasks());
			taskAdapter = new TodoAdapter(this, taskList, dbHelper);
			tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
			tasksRecyclerView.setAdapter(taskAdapter);
			taskAdapter.notifyDataSetChanged();
		});


    }
}
