package org.techtown.graduation_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class CovidActivity extends AppCompatActivity {

    TextView decide; // 확진자 TextView
    TextView accExam; // 누적검사 TextView
    TextView clearCnt; // 완치 TextView
    TextView deathCnt; // 사망 TextView
    TextView stateDt; // 기준 일 TextView
    TextView stateTime; // 기준 시간 TextView
    TextView Daily_decide; // 일일 확진자 TextView
    TextView Daily_accExam; // 일일 검사자 TextView
    TextView Daily_clear; // 일일 확진자 TextView
    TextView Daily_death; // 일일 사망자 TextView
    TextView local_cnt;  // 국내 일일 확진자 TextView
    TextView over_cnt; // 해외 유입 일일 확진자 TextView

    Button hospital; // 병원 검색 버튼
    Button infection; // 코로나19 시도 발생 현황 검색 버튼
    Button mask; // 마스크 검색 버튼
    Button pharmacy; // 약국 검색 버튼
    Button MyLocation; // 내 위치 조회 버튼
    Button dbSelect;
    Button disaster;

    DatabaseHelper mDatabaseHelper = new DatabaseHelper(this);
    GeoDatabaseHelper geoDatabaseHelper = new GeoDatabaseHelper(this);

    double tmp_latitude;
    double tmp_longitude;
    LatLng tmp_LatLng;
    String tablename;

    double db_latitude;
    double db_longitude;
    LatLng db_LatLng;


    private static final int REQUEST_CODE_LOCATION_PERMISSIONS = 1;
    static RequestQueue requestQueue; // 요청 큐
    // 공공데이터 포털의 servicekey
    String key = "pPaSpIZ%2BXFweoQb0rmHH5gguuqHRO00DHw7CgOuW9wZ2c5HDm%2BwqWpv%2B29V9NIHAcggmnJz3ztzM8206Hkkw7A%3D%3D";

    @Override
    protected    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);

        decide = (TextView) findViewById(R.id.decide);
        accExam = (TextView) findViewById(R.id.accExam);
        clearCnt = (TextView) findViewById(R.id.clearCnt);
        deathCnt = (TextView) findViewById(R.id.deathCnt);
        Daily_decide = (TextView) findViewById(R.id.Daily_decide);
        Daily_accExam = (TextView) findViewById(R.id.Daily_accExam);
        Daily_clear = (TextView) findViewById(R.id.Daily_clear);
        Daily_death = (TextView) findViewById(R.id.Daily_death);
        local_cnt = (TextView) findViewById(R.id.local_cnt);
        over_cnt = (TextView) findViewById(R.id.over_cnt);
        stateDt = (TextView) findViewById(R.id.stateDt);
        stateTime = (TextView) findViewById(R.id.stateTime);


        infection = findViewById(R.id.infection);
        mask = findViewById(R.id.mask);
        MyLocation = findViewById(R.id.MyLocation);
        disaster = findViewById(R.id.table_button);


        ///////////////////
        dbSelect = findViewById(R.id.dbSelect);
        dbSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor geodata = geoDatabaseHelper.getGeoDB();
                while(geodata.moveToNext()){
                    Log.d("GeoDB에서 가져온 정보: ", geodata.getString(0) + " | " +geodata.getString(1) + " | " + geodata.getString(2) + " | " + geodata.getString(3) + " | " + geodata.getString(4) + " | " + geodata.getString(5));
                }
//                Cursor data = geoDatabaseHelper.getGeoDB();
//                while(data.moveToNext()){
//                    Location user_location = new Location(LocationManager.GPS_PROVIDER);
//                    tmp_latitude = data.getDouble(1);
//                    tmp_longitude = data.getDouble(2);
//                    user_location.setLatitude(tmp_latitude);
//                    user_location.setLongitude(tmp_longitude);
//                    tmp_LatLng = new LatLng(tmp_latitude, tmp_longitude);
//                    Log.d("GeoDB에서 가져온 LatLng", String.valueOf(tmp_LatLng));
//
//                    Cursor tCursor = mDatabaseHelper.getTableName();
//                    while (tCursor.moveToNext()) {
//                        tablename = tCursor.getString(0);
//                        if(tablename.equals("android_metadata")){
//                            continue;
//                        }else if(tablename.equals("sqlite_sequence")){
//                            continue;
//                        }else{
//                            Cursor db_cursor = mDatabaseHelper.getLatLng(tablename);
//                            while(db_cursor.moveToNext()){
//                                Location db_location = new Location(LocationManager.GPS_PROVIDER);
//                                db_latitude = db_cursor.getDouble(0);
//                                db_longitude = db_cursor.getDouble(1);
//                                db_location.setLatitude(db_latitude);
//                                db_location.setLongitude(db_longitude);
//                                if(db_location.distanceTo(user_location) < 100){
//                                    Log.d("경보: ", tablename + "   " + data.getString(3));
//                                }
//                            }
//                        }
//                    }
//
//                }
            }
        });
        ///////////////////

        infection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infection_intent = new Intent(getApplicationContext(), Infection.class);
                startActivity(infection_intent);
            }
        });

        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mask_intent = new Intent(getApplicationContext(), Mask.class);
                startActivity(mask_intent);
            }
        });

        MyLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SQLiteActivity.class);
                startActivity(intent);
            }
        });

        disaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Disaster.class);
                startActivity(intent);
            }
        });


        if(ContextCompat.checkSelfPermission(   // 위치 접근 권한 확인
                getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(
                    CovidActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_CODE_LOCATION_PERMISSIONS
            );
        }else{
            startLocationService();
        }
        int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION);
        if(permissionCheck2 == PackageManager.PERMISSION_DENIED){ //백그라운드 위치 권한 확인

            //위치 권한 요청
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_LOCATION_PERMISSIONS);
        }

        // RequestQueue 객체 생성하기
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        covid19();
        covidChartRequest();

    }

//    public String getTableName(){
//        Cursor tCursor = mDatabaseHelper.getTableName();
//        while (tCursor.moveToNext()) {
//            tablename = tCursor.getString(0);
//            if(tablename.equals("android_metadata")){
//                continue;
//            }else if(tablename.equals("sqlite_sequence")){
//                continue;
//            }else{
//                return tablename;
//            }
//        }
//    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_LOCATION_PERMISSIONS && grantResults.length>0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                startLocationService();
            }{
                Toast.makeText(this, "권한이 승인됨.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isLocationServiceRunning(){
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if(activityManager != null){
            for(ActivityManager.RunningServiceInfo service : activityManager.getRunningServices(Integer.MAX_VALUE)){
                if(MyService.class.getName().equals(service.service.getClassName())){
                    if(service.foreground){
                        return true;
                    }
                }
            }
            return false;
        }
        return false;
    }

    private void startLocationService(){
        if(!isLocationServiceRunning()){
            Intent intent = new Intent(getApplicationContext(), MyService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                ContextCompat.startForegroundService(this, intent);
            }
            Toast.makeText(this,"Location service started", Toast.LENGTH_LONG).show();
        }
    }

    private void stopLocationService(){
        if(isLocationServiceRunning()){
            Intent intent = new Intent(getApplicationContext(), MyService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            stopService(intent);
            Toast.makeText(this, "Location service stopped", Toast.LENGTH_LONG).show();
        }
    }

    private void toast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void covid19() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 년, 월, 일 데이터 포맷형식 설정
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance(); // 오늘날짜
        String today = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, -8);  // 오늘 날짜에서 2일치를 뺌
        String dayinter = sdf.format(calendar.getTime());
        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey="
                +key+"&startCreateDt="+dayinter+"&endCreateDt="+today;



        // 요청을 보내기 위한 StringRequest객체 생성
        StringRequest request = new StringRequest(
                Request.Method.GET, // 첫번 째 파라미터 GET 메서드
                url, // 두 번째 파라미터 url 주소
                new com.android.volley.Response.Listener<String>() {
                    @Override  // 세 번째 파라미터 응답받을 리스너 객체
                    public void onResponse(String response) {
                        covidResponse(response);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override // 네 번째 파라미터 에러발생시 호출될 리스너 객체
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT);
                    }
                }
        ) {
            @Override // POST 방식사용시의 반환하는 HashMap 객체
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        // 요청을 보내기 위한 StringRequest객체 생성
        StringRequest request2 = new StringRequest(
                Request.Method.GET, // 첫번 째 파라미터 GET 메서드
                url, // 두 번째 파라미터 url 주소
                new com.android.volley.Response.Listener<String>() {
                    @Override  // 세 번째 파라미터 응답받을 리스너 객체
                    public void onResponse(String response) {
                        covidResponse(response);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override // 네 번째 파라미터 에러발생시 호출될 리스너 객체
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT);
                    }
                }
        ) {
            @Override // POST 방식사용시의 반환하는 HashMap 객체
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void covidResponse(String response) {
        XmlToJson xmlToJson = new XmlToJson.Builder(response).build();
        Gson gson = new Gson();
        CovidLocal covidList = gson.fromJson(xmlToJson.toJson().toString(), CovidLocal.class);
        Item today = covidList.response.body.items.item.get(0);
        Item yesterday = covidList.response.body.items.item.get(1);

        DecimalFormat df = new DecimalFormat("#,###"); // 표현 패턴 설정

        /*기준 시간 추가*/
        StringBuffer dt = new StringBuffer();
        dt.append(today.stateDt);
        dt.insert(4,".");
        dt.insert(7,".");
        println(dt, stateDt);
        println(today.stateTime, stateTime);

        /* -----금일 데이터 String -> int형 변환-------*/
        int decide_today = Integer.parseInt(today.decideCnt);
        int accExam_today = Integer.parseInt(today.accExamCnt);
        int clear_today = Integer.parseInt(today.clearCnt);
        int death_today = Integer.parseInt(today.deathCnt);

        /* -----전일 데이터 String -> int형 변환-------*/
        int decide_yesterday = Integer.parseInt(yesterday.decideCnt);
        int accExam_yesterday = Integer.parseInt(yesterday.accExamCnt);
        int clear_yesterday = Integer.parseInt(yesterday.clearCnt);
        int death_yesterday = Integer.parseInt(yesterday.deathCnt);

        /* ----- 전일 대비 증감 -------*/
        int dec_inter = decide_today - decide_yesterday;
        int acc_inter = accExam_today - accExam_yesterday;
        int clear_inter = clear_today - clear_yesterday;
        int death_inter = death_today - death_yesterday;

        /* ----- 금일 데이터 텍스트뷰에 추가 -------*/
        println(df.format(decide_today), decide);
        println(df.format(accExam_today),accExam);
        println(df.format(clear_today), clearCnt);
        println(df.format(death_today), deathCnt);


        int blue = ContextCompat.getColor(getApplicationContext(), R.color.blue); // 감소하였을 경우
        int red = ContextCompat.getColor(getApplicationContext(), R.color.red);   // 증가하였을 경우

        /* ----- 전일 대비 증감 데이터 텍스트뷰에 추가 -------*/

        if(dec_inter < 0){
            Daily_decide.setTextColor(blue);
            println(dec_inter, Daily_decide);
        }
        else if(dec_inter > 0) {
            Daily_decide.setTextColor(red);
            println(dec_inter, Daily_decide);
        }
        if(acc_inter < 0){
            Daily_accExam.setTextColor(blue);
            println(df.format(acc_inter), Daily_accExam);
            Daily_accExam.setCompoundDrawablesWithIntrinsicBounds(R.drawable.down,0,0,0);
        }
        else if(acc_inter > 0) {
            Daily_accExam.setTextColor(red);
            println(df.format(acc_inter), Daily_accExam);
            Daily_accExam.setCompoundDrawablesWithIntrinsicBounds(R.drawable.up,0,0,0);
        }
        if(clear_inter < 0){
            Daily_clear.setTextColor(blue);
            println(clear_inter, Daily_clear);
            Daily_accExam.setCompoundDrawablesWithIntrinsicBounds(R.drawable.down,0,0,0);
        }
        else if(clear_inter > 0) {
            Daily_clear.setTextColor(red);
            println(clear_inter, Daily_clear);
            Daily_clear.setCompoundDrawablesWithIntrinsicBounds(R.drawable.up,0,0,0);
        }
        if(death_inter < 0){
            Daily_death.setTextColor(blue);
            println(death_inter, Daily_death);
            Daily_death.setCompoundDrawablesWithIntrinsicBounds(R.drawable.down,0,0,0);
        }
        else if(death_inter > 0) {
            Daily_death.setTextColor(red);
            println(death_inter, Daily_death);
            Daily_death.setCompoundDrawablesWithIntrinsicBounds(R.drawable.up,0,0,0);
        }
    }
    public void covidChartRequest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 년, 월, 일 데이터 포맷형식 설정
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance(); // 오늘날짜
        String today = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, -8);  // 오늘 날짜에서 2일치를 뺌
        String yesterday = sdf.format(calendar.getTime());
        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=pPaSpIZ%2BXFweoQb0rmHH5gguuqHRO00DHw7CgOuW9wZ2c5HDm%2BwqWpv%2B29V9NIHAcggmnJz3ztzM8206Hkkw7A%3D%3D"+"&startCreateDt="+yesterday+"&endCreateDt="+today;

        // 요청을 보내기 위한 StringRequest객체 생성
        StringRequest request = new StringRequest(
                Request.Method.GET, // 첫번 째 파라미터 GET 메서드
                url, // 두 번째 파라미터 url 주소
                new com.android.volley.Response.Listener<String>() {
                    @Override  // 세 번째 파라미터 응답받을 리스너 객체
                    public void onResponse(String response) {
                        covidChartResponse(response);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override // 네 번째 파라미터 에러발생시 호출될 리스너 객체
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT);
                    }
                }
        ) {
            @Override // POST 방식사용시의 반환하는 HashMap 객체
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    public void covidChartResponse(String response) {
        XmlToJson xmlToJson = new XmlToJson.Builder(response).build();
        Gson gson = new Gson();
        CovidSido covidList = gson.fromJson(xmlToJson.toJson().toString(), CovidSido.class);
        CovidSidoItem item0 = covidList.response.body.items.item.get(18);  // 금일 합계 데이터
        CovidSidoItem item1 = covidList.response.body.items.item.get(37);  // 전일 합계 데이터
        CovidSidoItem item2 = covidList.response.body.items.item.get(56);  // 2일전
        CovidSidoItem item3 = covidList.response.body.items.item.get(75);  // 3일전
        CovidSidoItem item4 = covidList.response.body.items.item.get(94);  // 4일전
        CovidSidoItem item5 = covidList.response.body.items.item.get(113); // 5일전
        CovidSidoItem item6 = covidList.response.body.items.item.get(132); // 6일전

        /*국내 일일, 해외 유입 일일 확진자*/
        println(item0.localOccCnt, local_cnt);
        println(item0.overFlowCnt, over_cnt);

        /*일주일치 일일 확진자의 수를 구하기 위한 리스트*/
        ArrayList dailyList = new ArrayList();
        dailyList.add(item0.incDec);
        dailyList.add(item1.incDec);
        dailyList.add(item2.incDec);
        dailyList.add(item3.incDec);
        dailyList.add(item4.incDec);
        dailyList.add(item5.incDec);
        dailyList.add(item6.incDec);

        /*일주일치 발생 시간을 구하기 위한 리스트*/
        ArrayList creatdt = new ArrayList();
        creatdt.add(item0.createDt);
        creatdt.add(item1.createDt);
        creatdt.add(item2.createDt);
        creatdt.add(item3.createDt);
        creatdt.add(item4.createDt);
        creatdt.add(item5.createDt);
        creatdt.add(item6.createDt);

        String[] cre = new String[creatdt.size()]; // 발생 시간을 담는 배열 선언
        int[] inc = new int[dailyList.size()]; // 일일 확진자를 담는 배열 선언
        for(int i=0 ;i<dailyList.size();i++){
            inc[i] = Integer.parseInt((String) dailyList.get(i)); // 일일 확진자 데이터 배열에 추가
            cre[i] = ((String) creatdt.get(i)).substring(5,10).replace("-","."); // 발생 시간의 원하는 위치를 받기 위함
        }

        LineChart lineChart = findViewById(R.id.chart);
        lineChart.invalidate(); // 차트 초기화 작업
        lineChart.clear();
        lineChart.setTouchEnabled(false);

        /*설명문 없애기*/
        Description description = new Description();
        description.setText("");
        lineChart.setDescription(description);

        ArrayList incData = new ArrayList();
        incData.add(new BarEntry(0, inc[6]));
        incData.add(new BarEntry(1, inc[5]));
        incData.add(new BarEntry(2, inc[4]));
        incData.add(new BarEntry(3, inc[3]));
        incData.add(new BarEntry(4, inc[2]));
        incData.add(new BarEntry(5, inc[1]));
        incData.add(new BarEntry(6, inc[0]));



        LineDataSet dataSet = new LineDataSet(incData, "요일");
        dataSet.setColors(Color.RED); // 라인색 설정
        dataSet.setCircleColor(Color.RED); // 라인의 원 색상 설정
        dataSet.setCircleHoleColor(Color.RED); // 라인의 원 구멍 색상 설정

        LineData lineData = new LineData(dataSet);
        lineData.setValueTextSize(12); // line data의 text

        /*x축 라벨 설정을 위함*/
        ArrayList<String> xAxisLabel = new ArrayList<>();
        xAxisLabel.add(cre[6]);
        xAxisLabel.add(cre[5]);
        xAxisLabel.add(cre[4]);
        xAxisLabel.add(cre[3]);
        xAxisLabel.add(cre[2]);
        xAxisLabel.add(cre[1]);
        xAxisLabel.add(cre[0]);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);   // x축의 시작선 보이게
        xAxis.setDrawGridLines(false); // x축 선을 안보이게

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xAxisLabel.get((int)value);
            }

        });

        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setTextSize(12f);
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setAxisMaximum(1000f);
        //yAxisLeft.setLabelCount(11,true);

        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(true);
        yAxisRight.setDrawGridLines(false);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        lineChart.setData(lineData);

        /*차트 끝*/
    }

    public void println(Object data, TextView textView) {
        textView.setText(data.toString());
    }

}