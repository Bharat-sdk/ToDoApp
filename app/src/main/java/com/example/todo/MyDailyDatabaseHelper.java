package com.example.todo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDailyDatabaseHelper  extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "DailyTodolist.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "my_daily_todolist";
    private static final String COLUNM_ITEM_ID = "item_id";
    private static final String COLUNM_ITEM_NAME = "todo_item";
    private static final String COLUNM_QUANTITY_OF_PURCHASE = "todo_qt_of_purchase";
    private static final String COLUNM_QUANTITY_OF_MONTHLY_PURCHASE = "todo_qt_monthly_purchase";

    private static MyDailyDatabaseHelper instance ;

    public MyDailyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "("
                + COLUNM_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUNM_ITEM_NAME + " TEXT, "
                + COLUNM_QUANTITY_OF_PURCHASE + " INTEGER, "
                + COLUNM_QUANTITY_OF_MONTHLY_PURCHASE + " INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    void addItem(String name, int qty_of_purchase )
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUNM_ITEM_NAME,name);
        cv.put(COLUNM_QUANTITY_OF_PURCHASE,qty_of_purchase);

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
    public boolean updateDailyData (String todo_qt_of_purchase,String item_name)
    {
        instance = new MyDailyDatabaseHelper(context);
        SQLiteDatabase db = instance.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUNM_QUANTITY_OF_PURCHASE,todo_qt_of_purchase);
        db.update(TABLE_NAME,cv,"todo_item = ? ",new String[] {item_name});
        return true;
    }
}


