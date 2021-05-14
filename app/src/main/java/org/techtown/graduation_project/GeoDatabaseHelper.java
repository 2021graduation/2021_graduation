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

    private static final String TAG = "GeoDatabaseHelper";

    private static String TABLE_NAME = getDate();
    String date;

    public static String getDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy년MM월dd일");
        String now = dayTime.format(new Date(time));
        return now;
    }

    public GeoDatabaseHelper(Context context){
        super(context, "GeoDB", null, 2);
    }

    /*
    CREATE TABLE GeoDB(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    address VARCHAR(50) NOT NULL,
    latitude decimal(18,10) NOT NULL,
    longitude decimal(18,10) NOT NULL,
    dMsg TEXT NOT NULL,
    PRIMARY KEY(id));
    */

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "GeoDB 생성됨");
        db.execSQL("CREATE TABLE IF NOT EXISTS GeoDB(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                "startDay VARCHAR(30) , endDay VARCHAR(30) DEFAULT NULL, term VARCHAR(20)," +
                "address VARCHAR(50) NOT NULL, latitude decimal(18,10) NOT NULL, longitude decimal(18,10) NOT NULL, dMsg TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeTable = "DROP TABLE IF EXISTS GeoDB";
        db.execSQL(upgradeTable);
        onCreate(db);
    }


    /*
    2번쿼리
    INSERT INTO GeoDB(address, latitude, longitude, dMsg) VALUES (address,Current.latitude,Current.longitude,dMsg);
    */
    public void addData(String address, LatLng Current, String dMsg){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO GeoDB(address, latitude, longitude, dMsg) VALUES ('"+address+"',"+Current.latitude+","+Current.longitude+",'"+dMsg+"')");
        Log.d(TAG, "저장 작업이 끝났습니다: " + address);
    }

    public void addstartDay(String startDay, String Msg){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update GeoDB set startDay = '" + startDay + "'where dMsg = '" + Msg +"';");
    }

    public void addendDay(String endDay, String Msg){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update GeoDB set endDay = '" + endDay + "'where dMsg = '" + Msg +"';");
    }

    public void addterm(String term, String Msg) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("Update GeoDB set term = '" + term + "'where dMsg = '" + Msg +"';");
    }


    public boolean GeoDB_Check(String Msg) {
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT dMsg FROM GeoDB";
        Cursor data = db.rawQuery(query, null);
        while (data.moveToNext()) {
            if (data.getString(0).equals(Msg)) {
                return true;
            }
        }
        return false;
    }



    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE GeoDB";
        db.execSQL(query);
        onCreate(db);
    }

    public Cursor getTableName(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT name FROM sqlite_master WHERE type = 'table';";
        //String query = "SELECT * FROM '" + TABLE_NAME + "';";
        Cursor data = db.rawQuery(query, null);
        data.moveToFirst();
        return data;
    }

    public Cursor getGeoDB(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT startDay, endDay, address, latitude, longitude, dMsg FROM GeoDB";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getLatLng(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT latitude, longitude FROM GeoDB";
        Cursor data = db.rawQuery(query, null);
        return data;
    }



    public Cursor getCursor(String table){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM '" + table +"';";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

//    public boolean dbCheck(){
//        int count = 0;
//        SQLiteDatabase db = this.getWritableDatabase();
//        date = getDate();
//        db.execSQL("CREATE TABLE IF NOT EXISTS '" + date + "'(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, latitude decimal(18,10) NOT NULL, longitude decimal(18,10) NOT NULL)");
//        Cursor data = getLocation(date);
//        while (data.moveToNext()) {
//            count++;
//        }
//        if(count>0){
//            Log.d(TAG, "data.getCount: " + count);
//            return true;
//        }else{
//            Log.d(TAG, "false: data.getCount: " + count);
//            return false;
//        }
//    }

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
