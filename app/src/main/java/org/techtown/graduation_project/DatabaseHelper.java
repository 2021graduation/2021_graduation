package org.techtown.graduation_project;

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
    1번쿼리
    CREATE TABLE IF NOT EXISTS TABLE_NAME(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    latitude decimal(18,10) NOT NULL,
    longitude decimal(18,10) NOT NULL,
    PRIMARY KEY(id));

    2번쿼리
    INSERT INTO TABLE_NAME (name) VALUES(item);
    */
    public void addData(LatLng Current){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS '" + TABLE_NAME + "'(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, latitude decimal(18,10) NOT NULL, longitude decimal(18,10) NOT NULL)");
        db.execSQL("INSERT INTO '" + TABLE_NAME + "'(latitude, longitude) VALUES ("+Current.latitude+","+Current.longitude+")");
    }

    public Cursor getTableName(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT name FROM sqlite_master WHERE type='table';";
        //String query = "SELECT * FROM '" + TABLE_NAME + "';";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getLocation(String table){
        SQLiteDatabase db =this.getReadableDatabase();
        String query = "SELECT * FROM '" + table + "';";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor LastRow(String table){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM '" + table + "' ORDER BY id DESC LIMIT 1";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean dbCheck(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT name FROM sqlite_master WHERE type='table' AND name ='" + table +"';";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        if(data.getCount()>0){
            return true;
        }else{
            return false;
        }
    }

    /*
    DELETE FROM TABLE_NAME
    WHERE ID = "id" AND NAME = "name"

    -->
    DELETE FROM TABLE_NAME
    WHERE name = row_data
     */
    public void deleteName(String table){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE '" + table + "';";
        //String query = "DELETE FROM '" + TABLE_NAME + "' WHERE name = '" + row_data +"';";
        Log.d(TAG, "deleteName: query: " + query);
        db.execSQL(query);
    }
}
