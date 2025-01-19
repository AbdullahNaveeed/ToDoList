package com.example.todolist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{

    private Context context;
    private TodoDBHelper todoDBHelper;
    private List<Todo> taskList;
    private LayoutInflater mInflater;

    public TodoAdapter(Context context, List<Todo> taskList,TodoDBHelper dbHelper) {
        this.context = context;
        this.taskList = taskList;
        this.todoDBHelper = dbHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.todo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Todo task = taskList.get(position);
        holder.taskName.setText(task.getName());
        holder.taskDescription.setText(task.getDescription());
        holder.taskTime.setText(task.getTime());
        holder.taskStatus.setText(task.getStatus());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, taskDescription, taskTime, taskStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskNameTextView);
            taskDescription = itemView.findViewById(R.id.taskDescriptionTextView);
            taskTime = itemView.findViewById(R.id.taskTimeTextView);
            taskStatus = itemView.findViewById(R.id.taskStatusTextView);
        }
    }

}
