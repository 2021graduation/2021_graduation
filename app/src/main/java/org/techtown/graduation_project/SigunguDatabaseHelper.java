package org.techtown.graduation_project;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SigunguDatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "SigunguDatabaseHelper";

    public SigunguDatabaseHelper(Context context){
        super(context, "Sigungu", null, 1);
    }

    /*
    CREATE TABLE IF NOT EXISTS Address(
    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, sigungu VARCHAR(30) NOT NULL)
    */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Sigungu 데이터베이스 생성됨");
        db.execSQL("CREATE TABLE IF NOT EXISTS Address(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, si VARCHAR(30) NOT NULL, gungu VARCHAR(30) NOT NULL)");
        db.execSQL("CREATE TABLE IF NOT EXISTS disasterMsg(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, dMsg TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String upgradeTable = "DROP TABLE IF EXISTS Address;";
        db.execSQL(upgradeTable);
        onCreate(db);
    }

    /*
    db.execSQL("INSERT INTO Address VALUES'" + Sigungu +"';");
    */
    public void addSigungu(String si, String gungu){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("CREATE TABLE IF NOT EXISTS Address(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, si VARCHAR(30) NOT NULL, gungu VARCHAR(30) NOT NULL)");
        db.execSQL("INSERT INTO Address(si, gungu) VALUES ('" + si + "','" + gungu +"');");
    }

    /*
        SELECT sigungu_parse FROM Address;
    */
    public Cursor getSigungu(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT si, gungu FROM Address;";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE Address";
        db.execSQL(query);
        onCreate(db);
    }


    /*
        SELECT
    */

    public boolean Sigungu_Check(String si, String gungu){
        SQLiteDatabase db = this.getReadableDatabase();

        //String query = "SELECT * FROM Address WHERE sigungu_parse = '" + sigungu +"';";
        String query = "SELECT si, gungu FROM Address";
        Cursor data = db.rawQuery(query, null);
        while(data.moveToNext()){
            if(data.getString(0).equals(si)){
                if(data.getString(1).equals(gungu)){
                    Log.d(TAG, "중복 데이터가 있어서 저장하지 않습니다.");
                    return true;
                }
            }
        }
        return false;
//        try{
//            String query = "SELECT * FROM Address WHERE sigungu_parse = '" + sigungu +"';";
//            Cursor data = db.rawQuery(query, null);
//            Log.d(TAG, data.getString(0));
//            return true;
//        }catch (RuntimeException e){
//            Log.d(TAG, "데이터 없음: " + sigungu);
//            return false;
//        }
//
//
//        if(data == null){
//            Log.d(TAG, "데이터 없음");
//            return false;
//        }else{
//            Log.d(TAG, "데이터 존재함 @!##!@");
//            return true;
//        }
    }

    public boolean SigungudMsg_Check(String Msg){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT dMsg FROM disasterMsg";
        Cursor data = db.rawQuery(query, null);
        Msg = Msg.replaceAll("\'", "");
        while (data.moveToNext()) {
            if (data.getString(0).equals(Msg)) {
                Log.d(TAG, data.getString(0) +","+Msg);
                return true;
            }
        }
        //data.close();
        return false;
    }

    public void SigunguAddMsg(String Msg){
        SQLiteDatabase db = this.getWritableDatabase();
        Msg = Msg.replaceAll("\'", "");
        db.execSQL("INSERT INTO disasterMsg(dMsg) VALUES ('" + Msg + "');");
    }

    public void SigungudMsg_dropTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DROP TABLE disasterMsg";
        db.execSQL(query);
        onCreate(db);
    }
}
