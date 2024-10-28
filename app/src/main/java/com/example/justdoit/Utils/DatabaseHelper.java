package com.example.justdoit.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.justdoit.Model.TODO;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    private static final int version = 1;
    private static final String db_name = "TODO_DATABASE";
    private static final String table_name = "TODO_TABLE";
    private static final String col_1 = "ID";
    private static final String col_2 = "TASK";
    private static final String col_3 = "STATUS";

    public DatabaseHelper(@Nullable Context context) {
        super(context, db_name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + table_name + "(ID INTEGER PRIMARY KEY AUTOINCREMENT , TASK TEXT, STATUS INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);
    }

    public void insertTask(TODO model) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_2, model.getTask());
        values.put(col_3, 0);
        db.insert(table_name, null, values);

    }

    public void updateTask(int id, String task) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_2, task);
        db.update(table_name, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void updateStatus(int id, int status) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(col_3, status);
        db.update(table_name, values, "ID=?", new String[]{String.valueOf(id)});
    }

    public void deleteTask(int id) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.delete(table_name, "ID=?", new String[]{String.valueOf(id)});
    }

    public List<TODO> getAllTasks() {
        db = this.getWritableDatabase();
        Cursor cursor = null;

        List<TODO> modelList = new ArrayList<>();

        db.beginTransaction();
        try {
            cursor = db.query(table_name, null, null, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        TODO task = new TODO();
                        task.setId(cursor.getInt(cursor.getColumnIndex(col_1)));
                        task.setTask(cursor.getString(cursor.getColumnIndex(col_2)));
                        task.setStatus(cursor.getInt(cursor.getColumnIndex(col_3)));

                        modelList.add(task);
                    } while (cursor.moveToNext());
                }
            }
        } finally {
            db.endTransaction();
            cursor.close();
        }

        return modelList;
    }

}


