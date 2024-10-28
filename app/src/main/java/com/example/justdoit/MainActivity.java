package com.example.justdoit;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.justdoit.Adapter.todoAdapter;
import com.example.justdoit.Model.TODO;
import com.example.justdoit.Utils.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements onDialogCloseListener {

    private RecyclerView todoRecycler;
    private FloatingActionButton fab;
    private DatabaseHelper db;
    private List<TODO> mList;
    private todoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        todoRecycler = findViewById(R.id.recyclerview);
        fab = findViewById(R.id.addTask);
        db = new DatabaseHelper(MainActivity.this);
        mList = new ArrayList<>();
        adapter = new todoAdapter(db, MainActivity.this);

        todoRecycler.setHasFixedSize(true);
        todoRecycler.setLayoutManager(new LinearLayoutManager(this));
        todoRecycler.setAdapter(adapter);


        mList = db.getAllTasks();
        Collections.reverse(mList);
        adapter.setTasks(mList);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(todoRecycler);
    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        mList = db.getAllTasks();
        Collections.reverse(mList);
        adapter.setTasks(mList);
        adapter.notifyDataSetChanged();
    }
}