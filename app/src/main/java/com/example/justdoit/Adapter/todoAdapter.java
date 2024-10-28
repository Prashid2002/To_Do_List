package com.example.justdoit.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justdoit.AddNewTask;
import com.example.justdoit.MainActivity;
import com.example.justdoit.Model.TODO;
import com.example.justdoit.R;
import com.example.justdoit.Utils.DatabaseHelper;

import java.util.List;

public class todoAdapter extends RecyclerView.Adapter<todoAdapter.MyViewHolder> {

    private List<TODO> mList;
    private MainActivity mainActivity;
    private DatabaseHelper myDB;

    public todoAdapter(DatabaseHelper myDB, MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        this.myDB = myDB;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tasklayout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TODO item = mList.get(position);
        holder.checkBox.setText(item.getTask());
        holder.checkBox.setChecked(toBoolean(item.getStatus()));
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    myDB.updateStatus(item.getId(), 1);

                } else {
                    myDB.updateStatus(item.getId(), 0);
                }
            }
        });

    }



    public boolean toBoolean(int num) {
        return num != 0;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public Context getContext()
    {
        return mainActivity;
    }

    public void setTasks(List<TODO> mList)
    {
        this.mList = mList;
        notifyDataSetChanged();
    }


    public void deleteTask(int position)
    {
        TODO item = mList.get(position);
        myDB.deleteTask(item.getId());
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void editTask(int position)
    {
        TODO item = mList.get(position);
        Bundle bundle = new Bundle();
        bundle.putInt("ID", item.getId());
        bundle.putString("TASK", item.getTask());

        AddNewTask task = new AddNewTask();
        task.setArguments(bundle);
        task.show(mainActivity.getSupportFragmentManager(), task.getTag());
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkBox;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
