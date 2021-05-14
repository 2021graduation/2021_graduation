package org.techtown.graduation_project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    static RequestQueue requestQueue;
    GeoDatabaseHelper geoDatabaseHelper = new GeoDatabaseHelper(this);
    SigunguDatabaseHelper sigunguDatabaseHelper = new SigunguDatabaseHelper(this);
    public DisasterRowData row;
    Button UpdateGeoDB;

    AppCompatDialog progressDialog;

    public Date getDate() {
        long time = System.currentTimeMillis();
        SimpleDateFormat dayTime = new SimpleDateFormat("yyyy년MM월dd일");

        String now = dayTime.format(new Date(time));
        try {
            Date today = dayTime.parse(now);
            return today;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

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
                ad.setIcon(R.drawable.warning);
                ad.setTitle(row_data);
                ad.setMessage("삭제하시겠습니까?");

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


        UpdateGeoDB = findViewById(R.id.UpdateGeoDB);
        UpdateGeoDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                geoDatabaseHelper.dropTable();
                requestQueue = Volley.newRequestQueue(getApplicationContext());
                progressON(SQLiteActivity.this, "환경에따라 수 분에서\n몇십 초가 걸릴 수 있습니다.");
                sendRequest();
            }
        });

        populateListView();
    }


    // db 가져오는 부분
    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the View");

        Date today = getDate();
        Date tableDate = null;
        Cursor tCursor = mDatabaseHelper.getTableName();
        while (tCursor.moveToNext()) {
            tablename = tCursor.getString(0);
            Log.d("테이블이름: ", tablename);
            if(tablename.equals("android_metadata")){
                continue;
            }else if(tablename.equals("sqlite_sequence")){
                continue;
            }else{
                // tablename(사용자 동선이 기록된 날짜), 오늘 날짜를 비교해서 2주 이상의 데이터면 삭제하는 코드
                SimpleDateFormat format = new SimpleDateFormat("yyyy년MM월dd일");
                try {
                    tableDate = format.parse(tablename);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long calDate = today.getTime() - tableDate.getTime();   // 오늘날짜 - 기록날짜.
                long calDateDays = calDate / ( 24*60*60*1000);
                calDateDays = Math.abs(calDateDays);
                Log.d("오늘과 테이블 날짜를 비교함: ", String.valueOf(calDateDays));
                if(calDateDays > 14){
                    mDatabaseHelper.deleteName(tablename);
                    TableAdapter.notifyDataSetChanged();
                    //TableAdapter.Clearmlist();
                    continue;
                    //populateListView();
                }


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
                if(tmp_startDay == null && tmp_endDay == null){ // 둘다 없으면
                    disaster_count = CompareWithGeoDB(data, tablename, disaster_count);
                }
                else if(tmp_endDay == null){ // GeoDB에 endDay 가 없으면
                    Log.d("GeoDB에서 가져온 정보: ", data.getString(0) + " | " + data.getString(1) + " | " + data.getString(2) + " | " + data.getString(3) + " | " + data.getString(4) + " | " + data.getString(5));
                    Date startDate = simpleDateFormat.parse(tmp_startDay);
                    Date UserDB_Date = simpleDateFormat.parse(tablename);
                    if(UserDB_Date.after(startDate) || UserDB_Date.compareTo(startDate) == 0){
                        // 유저 디비에 저장된 날짜가 재난문자의 시작 날짜보다 뒤에 있거나 같으면
                        disaster_count = CompareWithGeoDB(data, tablename, disaster_count);
                    }
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
        db_cursor.close();

        return disaster_count;
    }

    public void sendRequest() {
        String url = "https://apixml-5d25d-default-rtdb.firebaseio.com/Msg.json";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        processResponse(response);

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT);
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void processResponse(String response) {
        List<List<Address>> list = null;
        Geocoder geocoder = new Geocoder(this);

        Gson gson = new Gson();
        DisasterMsg disasterList = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterList.DisasterMsg.row.size(); i++){
            row = disasterList.DisasterMsg.row.get(i);
            String str = row.getMsg();
            // 요청하는 재난문자를 분류하기 위해 시군구 데이터베이스를 만들었음.
            // 시군구 데이터베이스에는 사용자가 방문했던 장소들의 시군구가 기록되어있음.
            Cursor sigunguCursor = sigunguDatabaseHelper.getSigungu();
            while(sigunguCursor.moveToNext()) {
                // ex) if(부산광역시 전체 || 부산광역시 사하구)
                if (row.getLocation_name().equals(sigunguCursor.getString(0) + " 전체") ||
                        row.getLocation_name().equals(sigunguCursor.getString(0) + " "
                                + sigunguCursor.getString(1))) {
                    if (geoDatabaseHelper.GeoDB_Check(row.getMsg()) == false){
                        Pattern pattern = Pattern.compile("[(](.*?)[)]");
                        Matcher matcher = pattern.matcher(str);

                        while (matcher.find()) {  // 일치하는 게 있다면
                            //재난문자 ( ) 안 내용들 모두 들어옴

                            if (matcher.group(1).length() > 2) {
                                //요일제외 2글자 이상인 재난문자 선별
                                Pattern pattern2 = Pattern.compile(".*?(길\\b|동\\b|대로\\b|로\\b).*");
                                Matcher matcher2 = pattern2.matcher(matcher.group(1));
                                while (matcher2.find()) {
                                    // ~길, ~동, ~대로 ~로 글자 전후로 가져옴
                                    String filter = matcher2.group();

                                    int target_index;
                                    if (filter.contains("소독완료")) {
                                        target_index = filter.indexOf("소독완료");
                                        filter = filter.replace(filter.substring(target_index), "");
                                    } else if (filter.contains("방역완료")) {
                                        target_index = filter.indexOf("방역완료");
                                        filter = filter.replace(filter.substring(target_index), "");
                                    }
                                    filter = filter.replaceAll(".*감염경로.*", "");
                                    getDisasterAddress(filter, str);
                                }
                            }
                            if (matcher.group(1) == null)
                                break;
                        }
                    }
                }
            }
            sigunguCursor.close();
        }
        TableAdapter.Clearmlist();
        populateListView();
        TableAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
        progressOFF();
    }

    public void getDisasterAddress(String Sigungu, String Msg) {

        //지오코더 주소를 GPS로 변환
        Geocoder geocoder = new Geocoder(this);

        List<Address> addresses;

        try {
            addresses = geocoder.getFromLocationName(
                    Sigungu,
                    1);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return;
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return;
        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return;

        } else {

            LatLng covidlatlng = new LatLng(addresses.get(0).getLatitude(), addresses.get(0).getLongitude());
            Msg = Msg.replaceAll("\'", "");
            Log.d(TAG, Msg);
            geoDatabaseHelper.addData(Sigungu, covidlatlng, Msg);
            try {
                MsgDrawDate(Msg);
            } catch (ParseException e) {
                e.printStackTrace();
            }

//            if(geoDatabaseHelper.GeoDB_Check(Msg)){
//                geoDatabaseHelper.addData(Sigungu, covidlatlng, Msg);
//                Log.d(TAG, String.valueOf(addresses.get(0)));
//            }
            return;
        }
    }

    public void MsgDrawDate(String Msg) throws ParseException {
        Pattern datepattern = Pattern.compile("[0-9]+\\.[0-9]{1,2}|[0-9]\\월 +[0-9]{1,2}\\일|[0-9]\\월+[0-9]{1,2}\\일|[0-9]/[0-9]{1,2}");
        Matcher datematcher = datepattern.matcher(Msg);
        int flag = 0;
        String startDay = null;
        String endDay = null;
        while (datematcher.find()){
            if (flag == 0) {  // 날짜가 하나도 저장되어 있지 않으면
                String datefilter = datematcher.group();
                datefilter = datefilter.replaceAll("\\월|\\일|\\/", ".");
                long time = System.currentTimeMillis();
                SimpleDateFormat dayTime = new SimpleDateFormat("yyyy");
                String now = dayTime.format(new Date(time));

                SimpleDateFormat beforedayTime = new SimpleDateFormat("MM.dd");
                SimpleDateFormat afterdayTime = new SimpleDateFormat(now + "년MM월dd일");
                java.util.Date tempDate = null;
                try {
                    tempDate = beforedayTime.parse(datefilter);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                startDay = afterdayTime.format(tempDate);
                //Log.d("시작날짜: ", startDay);
                geoDatabaseHelper.addstartDay(startDay, Msg);
            }
            else if (flag >= 1) {  // 이미 1개가 저장되어 있다면
                String datefilter = datematcher.group();
                datefilter = datefilter.replaceAll("\\월|\\일|\\/", ".");
                long time = System.currentTimeMillis();
                SimpleDateFormat dayTime = new SimpleDateFormat("yyyy");
                String now = dayTime.format(new Date(time));

                SimpleDateFormat beforedayTime = new SimpleDateFormat("MM.dd");
                SimpleDateFormat afterdayTime = new SimpleDateFormat(now + "년MM월dd일");
                java.util.Date tempDate = null;
                try {
                    tempDate = beforedayTime.parse(datefilter);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                endDay = afterdayTime.format(tempDate);
                //Log.d("끝 날짜: ", endDay);
                // DB endDay 저장코드
                geoDatabaseHelper.addendDay(endDay, Msg);
            }
            flag += 1;
        }
        if(flag > 1){   // 최소 날짜가 2개 이상 기록됐다면
            SimpleDateFormat format = new SimpleDateFormat("yyyy년MM월dd일");
            // startDay, endDay 두 날짜를 parse()를 통해 Date형으로 변환.
            Date FirstDate = format.parse(startDay);
            Date SecondDate = format.parse(endDay);

            // Date로 변환된 두 날짜를 계산
            long calDate = FirstDate.getTime() - SecondDate.getTime();

            // 24*60*60*1000 = day
            long calDateDays = calDate / ( 24*60*60*1000);

            calDateDays = Math.abs(calDateDays);

            geoDatabaseHelper.addterm(String.valueOf(calDateDays), Msg);

        }
    }

    public void progressON(Activity activity, String message) {

        if (activity == null || activity.isFinishing()) {
            return;
        }


        if (progressDialog != null && progressDialog.isShowing()) {
            progressSET(message);
        } else {
            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.progress_loading);
            progressDialog.show();

        }


        final ImageView img_loading_frame = (ImageView) progressDialog.findViewById(R.id.iv_frame_loading);
        final AnimationDrawable frameAnimation = (AnimationDrawable) img_loading_frame.getBackground();
        img_loading_frame.post(new Runnable() {
            @Override
            public void run() {
                frameAnimation.start();
            }
        });

        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }


    }

    public void progressSET(String message) {

        if (progressDialog == null || !progressDialog.isShowing()) {
            return;
        }


        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.tv_progress_message);
        if (!TextUtils.isEmpty(message)) {
            tv_progress_message.setText(message);
        }

    }

    public void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
