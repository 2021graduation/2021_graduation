package org.techtown.graduation_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SQLiteActivity extends AppCompatActivity {
    private static final String TAG = "SQLiteActivity";

    private ArrayList<Table> Table;
    private TableAdapter TableAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    String message;

    String tablename;
    String latlng_count;
    String disaster_count;


    DatabaseHelper mDatabaseHelper;
    SwipeRefreshLayout refreshLayout;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_tablename);

        /////////////////// 새로고침하는 부분 ///////////////////
        refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TableAdapter.Clearmlist();
                populateListView();
                TableAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
        //////////////////////////////////////////////////////
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Table = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(this);


        TableAdapter = new TableAdapter(Table);
        recyclerView.setAdapter(TableAdapter);

        TableAdapter.setOnItemClickListener(new TableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, TextView tablename) {
                Log.d(TAG, "클릭됨: " + tablename.getText().toString());
                String date = tablename.getText().toString();
                Intent intent = new Intent(SQLiteActivity.this, MapActivity.class);
                intent.putExtra("table_name",date);
                startActivity(intent);
            }
        });

        TableAdapter.setOnItemLongClickListener(new TableAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos, TextView tablename) {
                String row_data = tablename.getText().toString();
                ////////////// 삭제 기능 ///////////////////
                Log.d(TAG, "현재시각: " + mDatabaseHelper.getDate());
                Log.d(TAG, "onItemClick: You Clicked on " + tablename.getText());
                AlertDialog.Builder ad = new AlertDialog.Builder(SQLiteActivity.this);
                ad.setIcon(R.mipmap.ic_launcher_round);
                ad.setTitle("제목");
                ad.setMessage("삭제하시겠습니까?");

                final EditText et = new EditText(SQLiteActivity.this);
                ad.setView(et);

                ad.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabaseHelper.deleteName(row_data);
                        dialog.dismiss();
                        TableAdapter.notifyDataSetChanged();
                        TableAdapter.Clearmlist();
                        populateListView();
                    }
                });

                ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                ad.show();
                ///////////////////////////////////////////////////////
            }
        });

        populateListView();
    }


    // db 가져오는 부분
    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the View");

        Cursor tCursor = mDatabaseHelper.getTableName();
        while (tCursor.moveToNext()) {
            tablename = tCursor.getString(0);


            if(tablename.equals("android_metadata")){
                continue;
            }else if(tablename.equals("sqlite_sequence")){
                continue;
            }else{
                latlng_count = String.valueOf(mDatabaseHelper.dbCheck(tablename));
                disaster_count = String.valueOf(get_disaster_count(tablename));
                Table data1 = new Table(tablename, latlng_count, disaster_count);
                Table.add(data1);
            }
        }
        tCursor.close();
    }

    public int get_disaster_count(String tablename) {
        GeoDatabaseHelper geoDatabaseHelper = new GeoDatabaseHelper(this);
        Cursor data = geoDatabaseHelper.getGeoDB();

        String tmp_startDay;        // GeoDB에서 가져온 시작 날짜를 저장할 변수
        String tmp_endDay;          // GeoDB에서 가져온 끝 날짜를 저장할 변수

        int disaster_count = 0;

        while(data.moveToNext()) {

            tmp_startDay = data.getString(0);
            tmp_endDay = data.getString(1);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년MM월dd일");
            try {
                if(tmp_endDay == null){ // GeoDB에 endDay 가 없으면
                    Date startDate = simpleDateFormat.parse(tmp_startDay);
                    Date UserDB_Date = simpleDateFormat.parse(tablename);
                    if(UserDB_Date.after(startDate) || UserDB_Date.compareTo(startDate) == 0){
                        // 유저 디비에 저장된 날짜가 재난문자의 시작 날짜보다 뒤에 있거나 같으면
                        disaster_count = CompareWithGeoDB(data, tablename, disaster_count);
                    }
                }
                else if(tmp_startDay == null && tmp_endDay == null){ // 둘다 없으면
                    disaster_count = CompareWithGeoDB(data, tablename, disaster_count);
                }
                else if(tmp_startDay != null && tmp_endDay != null){   // GeoDB에 endDay 가 존재한다면
                    Date startDate = simpleDateFormat.parse(tmp_startDay);
                    Date endDate = simpleDateFormat.parse(tmp_endDay);
                    Date UserDB_Date = simpleDateFormat.parse(tablename);

                    if(UserDB_Date.after(startDate) && UserDB_Date.before(endDate)
                            || UserDB_Date.compareTo(startDate) == 0 || UserDB_Date.compareTo(endDate) == 0){
                        disaster_count = CompareWithGeoDB(data, tablename, disaster_count);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        data.close();

        return disaster_count;
    }


    private int CompareWithGeoDB(Cursor data, String tablename, int disaster_count){

        /*
        사용자 위치와 GeoDB 위치를 비교하는 코드
        Cursor data <= GeoDB Cursor
        tablename <= mDatabaseHelper tablename

        MapActivity 에 있는 것과는 다른 점이
        int 형의 데이터를 반환함
         */

        double tmp_latitude;        // GeoDB에서 가져온 위도를 저장할 변수
        double tmp_longitude;       // GeoDB에서 가져온 경도를 저장할 변수
        LatLng tmp_LatLng;          // 위도, 경도를 합친 좌표
        double db_latitude;         // 사용자 DB에서 조회한 위도
        double db_longitude;        // 사용자 DB에서 조회한 경도

        Location GeoDB_location = new Location(LocationManager.GPS_PROVIDER);
        tmp_latitude = data.getDouble(3);
        tmp_longitude = data.getDouble(4);
        /*
        거리 비교를 위해 location 변수에 GeoDB에서 가져온 위도 경도 세팅
         */
        GeoDB_location.setLatitude(tmp_latitude);
        GeoDB_location.setLongitude(tmp_longitude);
        tmp_LatLng = new LatLng(tmp_latitude, tmp_longitude);
        Log.d("GeoDB에서 가져온 LatLng", String.valueOf(tmp_LatLng));

        Cursor db_cursor = mDatabaseHelper.getLatLng(tablename);
        while (db_cursor.moveToNext()) {
            Location db_location = new Location(LocationManager.GPS_PROVIDER);
            db_latitude = db_cursor.getDouble(0);
            db_longitude = db_cursor.getDouble(1);
            db_location.setLatitude(db_latitude);
            db_location.setLongitude(db_longitude);
            if (db_location.distanceTo(GeoDB_location) < 1000) {
                disaster_count++;
            }
        }

        return disaster_count;
    }


    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
