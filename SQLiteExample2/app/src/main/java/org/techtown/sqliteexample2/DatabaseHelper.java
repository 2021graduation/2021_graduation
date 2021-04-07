package org.techtown.sqliteexample2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = getDate();

    private static final String COL1 = "id";
    private static final String COL2 = "name";

    public static final String getDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy년MM월dd일");
        String now = dayTime.format(new Date(time));
        return now;
    }


    public DatabaseHelper(Context context){
        super(context, TABLE_NAME, null, 1);
    }

    /*
    CREATE TABLE TABLE_NAME(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    latitude decimal(18,10) NOT NULL,
    longitude decimal(18,10) NOT NULL,
    PRIMARY KEY(id));
    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "데이터베이스"+TABLE_NAME+"생성됨");
        db.execSQL("CREATE TABLE '"+ TABLE_NAME + "'(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, latitude decimal(18,10) NOT NULL, longitude decimal(18,10) NOT NULL)");
   }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeTable = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(upgradeTable);
        onCreate(db);
    }


    /*
    INSERT INTO TABLE_NAME (name) VALUES(item);
     */
    public void addData(LatLng Current){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO '" + TABLE_NAME + "'(latitude, longitude) VALUES ("+Current.latitude+","+Current.longitude+")");
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL2, item);
//
//        Log.d(TAG, "addData:Adding " + item + " to " + TABLE_NAME);
//        long result = db.insert(TABLE_NAME, null, contentValues);
//
//        if(result == -1){
//            return false;
//        }else{
//            return true;
//        }
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "SELECT name FROM sqlite_master WHERE type='table';";
        String query = "SELECT * FROM '" + TABLE_NAME + "';";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        /*
        SELECT ID
        FROM TABLE_NAME
        WHERE NAME = "name";
         */
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    /*
    DELETE FROM TABLE_NAME
    WHERE ID = "id" AND NAME = "name"

    -->
    DELETE FROM TABLE_NAME
    WHERE name = row_data
     */
    public void deleteName(String row_data){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM'" + TABLE_NAME + "'WHERE name = '" + row_data +"';";
        Log.d(TAG, "deleteName: query: " + query);
        db.execSQL(query);
    }
}
