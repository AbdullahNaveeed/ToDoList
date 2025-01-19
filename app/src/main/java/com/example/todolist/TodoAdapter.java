package com.example.todolist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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
        holder.editTaskButton.setOnClickListener(v -> {

            showEditTaskDialog(task, holder.getAdapterPosition());
        });
        holder.deleteTaskButton.setOnClickListener(v -> {
            todoDBHelper.deleteTask(task.getId());
            taskList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, taskList.size());
            Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
    private void showEditTaskDialog(Todo task, int position) {
        // Create a dialog to edit task details
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.edit_task_dialog, null);
        builder.setView(dialogView);

        EditText editTaskName = dialogView.findViewById(R.id.editTaskName);
        EditText editTaskDescription = dialogView.findViewById(R.id.editTaskDescription);
        Button saveButton = dialogView.findViewById(R.id.saveButton);

        // Set current values
        editTaskName.setText(task.getName());
        editTaskDescription.setText(task.getDescription());

        android.app.AlertDialog dialog = builder.create();
        saveButton.setOnClickListener(v -> {
            String updatedName = editTaskName.getText().toString();
            String updatedDescription = editTaskDescription.getText().toString();

            if (!updatedName.isEmpty() && !updatedDescription.isEmpty()) {
                task.setName(updatedName);
                task.setDescription(updatedDescription);
                todoDBHelper.updateTask(task.getId(), updatedName, updatedDescription, task.getTime(), task.getStatus());
                taskList.set(position, task);
                notifyItemChanged(position);

                dialog.dismiss();
                Toast.makeText(context, "Task Updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Enter all details", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, taskDescription, taskTime, taskStatus;
        CheckBox taskCheckBox;
        ImageButton editTaskButton,deleteTaskButton;

        public ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.taskNameTextView);
            taskDescription = itemView.findViewById(R.id.taskDescriptionTextView);
            taskTime = itemView.findViewById(R.id.taskTimeTextView);
            taskStatus = itemView.findViewById(R.id.taskStatusTextView);
            taskCheckBox = itemView.findViewById(R.id.taskCheckBox);
            editTaskButton = itemView.findViewById(R.id.editTaskButton);
            deleteTaskButton = itemView.findViewById(R.id.deleteTaskButton);
        }
    }

}
