package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private  Context context;
    private static final String DATABASE_NAME = "Todolist.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_todolist";
    private static final String COLUNM_ID = "_id";
    private static final String COLUNM_TITLE = "todo_title";
    private static final String COLUNM_DESCRIPTION = "todo_description";
    private static final String COLUNM_TIME = "todo_time";

    private static MyDatabaseHelper instance ;

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + COLUNM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNM_TITLE + " TEXT, "
                + COLUNM_DESCRIPTION + " TEXT, "
                + COLUNM_TIME + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
    onCreate(db);
    }

    void addWork(String title, String description, String time )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUNM_TITLE,title);
        cv.put(COLUNM_DESCRIPTION,description);
        cv.put(COLUNM_TIME,time);
        db.insert(TABLE_NAME,null,cv);


    }
    Cursor readData ()
    {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null)
        {
            cursor = db.rawQuery(query,null);
        }
        return cursor;
    }
//String row_id,
    public boolean updateData (String row_id,String todo_title,String todo_desc,String todo_date)
    {
        instance = new MyDatabaseHelper(context);
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUNM_TITLE,todo_title);
        cv.put(COLUNM_DESCRIPTION,todo_desc);
        cv.put(COLUNM_TIME,todo_date);
        db.update(TABLE_NAME,cv,"_id = ? ",new String[] {row_id});

        return true;
    }
}
