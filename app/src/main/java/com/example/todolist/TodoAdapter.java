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
        this.mInflater = LayoutInflater.from(context);
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
        holder.taskCheckBox.setChecked("Completed".equals(task.getStatus()));
        holder.taskCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            String newStatus = isChecked ? "Completed" : "Pending";
            todoDBHelper.updateTask(task.getId(), task.getName(), task.getDescription(), task.getTime(), newStatus);
            task.setStatus(newStatus); // Update the status locally
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, taskDescription, taskTime, taskStatus;
        CheckBox taskCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskNameTextView);
            taskDescription = itemView.findViewById(R.id.taskDescriptionTextView);
            taskTime = itemView.findViewById(R.id.taskTimeTextView);
            taskStatus = itemView.findViewById(R.id.taskStatusTextView);
            taskCheckBox = itemView.findViewById(R.id.taskCheckBox);
        }
    }

}
