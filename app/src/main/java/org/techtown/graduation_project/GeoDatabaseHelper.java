package org.techtown.graduation_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GeoDatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static String TABLE_NAME = getDate();
    String date;

    public static String getDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy년MM월dd일");
        String now = dayTime.format(new Date(time));
        return now;
    }

    public GeoDatabaseHelper(Context context){
        super(context, "GeoDB", null, 1);
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
        Log.d(TAG, "GeoDB 생성됨");
        db.execSQL("CREATE TABLE IF NOT EXISTS GeoDB(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, address VARCHAR(50) NOT NULL,latitude decimal(18,10) NOT NULL, longitude decimal(18,10) NOT NULL, dMsg TEXT NOT NULL)");
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
    public void addData(String address, LatLng Current, String dMsg){
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("CREATE TABLE IF NOT EXISTS '" + date + "'(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, latitude decimal(18,10) NOT NULL, longitude decimal(18,10) NOT NULL)");
        db.execSQL("INSERT INTO GeoDB(address, latitude, longitude, dMsg) VALUES ('"+address+"',"+Current.latitude+","+Current.longitude+",'"+dMsg+"')");
    }

    public Cursor getTableName(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT name FROM sqlite_master WHERE type = 'table';";
        //String query = "SELECT * FROM '" + TABLE_NAME + "';";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        return data;
    }

    public Cursor getLocation(String table){
        SQLiteDatabase db =this.getReadableDatabase();
        String query = "SELECT * FROM '" + table + "';";
        db.execSQL("CREATE TABLE IF NOT EXISTS '" + TABLE_NAME + "'(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, latitude decimal(18,10) NOT NULL, longitude decimal(18,10) NOT NULL)");
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getCursor(String table){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM '" + table +"';";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public boolean dbCheck(){
        int count = 0;
        SQLiteDatabase db = this.getWritableDatabase();
        date = getDate();
        db.execSQL("CREATE TABLE IF NOT EXISTS '" + date + "'(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, latitude decimal(18,10) NOT NULL, longitude decimal(18,10) NOT NULL)");
        Cursor data = getLocation(date);
        while (data.moveToNext()) {
            count++;
        }
        if(count>0){
            Log.d(TAG, "data.getCount: " + count);
            return true;
        }else{
            Log.d(TAG, "false: data.getCount: " + count);
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