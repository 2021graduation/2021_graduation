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
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
    TextView ingCnt; // 격리중 환자 TextView
    TextView clearCnt; // 완치 TextView
    TextView deathCnt; // 사망 TextView
    TextView createDt; // 기준시간 TextView
    TextView Daily_decide; // 일일 확진자 TextView
    TextView Daily_ingCnt; // 일일 격리중 환자 TextView
    TextView Daily_clear; // 일일 완치자 TextView
    TextView Daily_death; // 일일 사망자 TextView
    TextView local_cnt;  // 국내 일일 확진자 TextView
    TextView over_cnt; // 해외 유입 일일 확진자 TextView

    Button infection; // 코로나19 시도 발생 현황 검색 버튼
    Button mask; // 마스크 검색 버튼
    Button MyLocation; // 내 위치 조회 버튼
    Button disaster;

    private static final int REQUEST_CODE_LOCATION_PERMISSIONS = 1;
    static RequestQueue requestQueue; // 요청 큐

    @Override
    protected    void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);

        decide = (TextView) findViewById(R.id.decide);
        ingCnt = (TextView) findViewById(R.id.ingCnt);
        clearCnt = (TextView) findViewById(R.id.clearCnt);
        deathCnt = (TextView) findViewById(R.id.deathCnt);
        Daily_decide = (TextView) findViewById(R.id.Daily_decide);
        Daily_ingCnt = (TextView) findViewById(R.id.Daily_ingCnt);
        Daily_clear = (TextView) findViewById(R.id.Daily_clear);
        Daily_death = (TextView) findViewById(R.id.Daily_death);
        local_cnt = (TextView) findViewById(R.id.local_cnt);
        over_cnt = (TextView) findViewById(R.id.over_cnt);
        createDt = (TextView) findViewById(R.id.createDt);



        infection = findViewById(R.id.infection);
        mask = findViewById(R.id.mask);
        MyLocation = findViewById(R.id.MyLocation);
        disaster = findViewById(R.id.table_button);


//        ///////////////////
//        dbSelect = findViewById(R.id.dbSelect);
//        dbSelect.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Cursor geodata = geoDatabaseHelper.getGeoDB();
//                while(geodata.moveToNext()){
//                    Log.d("GeoDB에서 가져온 정보: ", geodata.getString(0) + " | " +geodata.getString(1) + " | " + geodata.getString(2) + " | " + geodata.getString(3) + " | " + geodata.getString(4) + " | " + geodata.getString(5));
//                }
//            }
//        });
//        ///////////////////

        infection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infection_intent = new Intent(getApplicationContext(), InfectionActivity.class);
                startActivity(infection_intent);
            }
        });

        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mask_intent = new Intent(getApplicationContext(), MaskActivity.class);
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
                Intent intent = new Intent(getApplicationContext(), DisasterActivity.class);
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

        covidChartRequest();

    }

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
            Toast.makeText(this,"위치 정보 수집이 시작되었습니다.", Toast.LENGTH_LONG).show();
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


    public void covidChartRequest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 년, 월, 일 데이터 포맷형식 설정
        Calendar calendar = Calendar.getInstance(); // 오늘날짜
        String today = sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, -8);  // 오늘 날짜에서 8일치를 뺌
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

        DecimalFormat df = new DecimalFormat("#,###"); // 표현 패턴 설정

        /*
        파싱할 데이터
        1. 일일 확진자 (incDec) -> 전일 대비 증감
        2. 국내 발생(localOccCnt), 해외 유입(overFlowCnt)
        3. 누적 확진자 (defCnt)
        4. 격리중환자(확진환자) (isolingCnt) -> 전일 대비 증감 (item0.isolingCnt) - (item1.isolingCnt)
        5. 완치(isolClearCnt) -> 전일 대비 증감 (item0.isolClearCnt) - (item1.isolClearCnt)
        6. 사망자(deathCnt) -> 전일 대비 증감 (item0.deathCnt) - (item1.deathCnt)
        7. 기준시간(createDt)
         */


        /*금일 코로나19현황 데이터*/
        int localcnt = Integer.parseInt(item0.localOccCnt); // 국내 일일 확진자 형 변환
        int overcnt = Integer.parseInt(item0.overFlowCnt); // 해외 유입 일일 확진자 형 변화
        int incDec_inter = Integer.parseInt(item0.incDec); // 총 일일 확진자 형 변환
        int defcnt = Integer.parseInt(item0.defCnt); // 누적 확진자 형 변환
        int ingcnt = Integer.parseInt(item0.isolIngCnt); // 격리중 환자 형 변환
        int clearcnt = Integer.parseInt(item0.isolClearCnt); // 완치 환자 형 변환
        int deatcnt = Integer.parseInt(item0.deathCnt); // 사망 환자 형 변환
        String create = item0.createDt.substring(0, 10).replace("-","."); // 발생 날짜

        /*전일 코로나19현황 데이터*/
        int yesterday_ingcnt = Integer.parseInt(item1.isolIngCnt);
        int yesterday_clearcnt = Integer.parseInt(item1.isolClearCnt);
        int yesterday_deathcnt = Integer.parseInt(item1.deathCnt);

        /*전일 대비 증감 코로나19현황 데이터*/
        int daily_ingcnt = ingcnt - yesterday_ingcnt;
        int daily_clearcnt = clearcnt - yesterday_clearcnt;
        int daily_deathcnt = deatcnt - yesterday_deathcnt;

        /*금일 코로나19현황 데이터 텍스트뷰 추가*/
        println(df.format(defcnt), decide);  // 누적 확진자
        println(df.format(localcnt), local_cnt); // 국내 일일
        println(df.format(overcnt), over_cnt); // 해외 유입 일일
        println(df.format(ingcnt), ingCnt); // 격리중 환자
        println(df.format(clearcnt), clearCnt); // 완치 환자
        println(df.format(deatcnt), deathCnt); // 사망 환자
        println(create, createDt); // 발생 시간

        int blue = ContextCompat.getColor(getApplicationContext(), R.color.blue); // 감소하였을 경우
        int red = ContextCompat.getColor(getApplicationContext(), R.color.red);   // 증가하였을 경우

        /*전일 대비 코로나19현황 데이터*/
        if(incDec_inter < 0){ // 총 일일 확진자가 0보다 작을 경우 파란색설정
            Daily_decide.setTextColor(blue);
            println(df.format(incDec_inter), Daily_decide);
        }
        else if(incDec_inter > 0){ // 총 일일 확진자가 0보다 클 경우 빨간색설정
            Daily_decide.setTextColor(red);
            println(df.format(incDec_inter), Daily_decide);
        }
        if(daily_ingcnt < 0) // 격리 중 환자의 수가 0 보다 작을 경우 파란색설정
        {
            Daily_ingCnt.setTextColor(blue);
            println(daily_ingcnt, Daily_ingCnt);
            //Daily_ingCnt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.down,0,0,0);
        }
        else if(daily_ingcnt > 0) // 격리 중 환자의 수가 0보다 클 경우 빨간색 설정
        {
            Daily_ingCnt.setTextColor(red);
            println(daily_ingcnt, Daily_ingCnt);
            //Daily_ingCnt.setCompoundDrawablesWithIntrinsicBounds(R.drawable.up,0,0,0);
        }
        if(daily_clearcnt < 0){
            Daily_clear.setTextColor(blue);
            println(daily_clearcnt, Daily_clear);
            //Daily_clear.setCompoundDrawablesWithIntrinsicBounds(R.drawable.down,0,0,0);
        }
        else if(daily_clearcnt > 0) {
            Daily_clear.setTextColor(red);
            println(daily_clearcnt, Daily_clear);
            //Daily_clear.setCompoundDrawablesWithIntrinsicBounds(R.drawable.up,0,0,0);
        }
        if(daily_deathcnt < 0){
            Daily_death.setTextColor(blue);
            println(daily_deathcnt, Daily_death);
            //Daily_death.setCompoundDrawablesWithIntrinsicBounds(R.drawable.down,0,0,0);
        }
        else if(daily_deathcnt > 0) {
            Daily_death.setTextColor(red);
            println(daily_deathcnt, Daily_death);
            //Daily_death.setCompoundDrawablesWithIntrinsicBounds(R.drawable.up,0,0,0);
        }
        /***************************************************/


        /************차트시작************/
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
        lineChart.setTouchEnabled(false);

        /*Description 설정*/
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
        xAxis.setAxisMinimum(-0.2f);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xAxisLabel.get((int)value);
            }

        });

        /*****************************************************/

        /*Y축 왼쪽 설정*/
        YAxis yAxisLeft = lineChart.getAxisLeft();
        yAxisLeft.setTextSize(12f);
        yAxisLeft.setAxisMinimum(0f);
        yAxisLeft.setAxisMaximum(1000f);
        //yAxisLeft.setLabelCount(11,true);

        /*Y축 오른쪽 설정*/
        YAxis yAxisRight = lineChart.getAxisRight();
        yAxisRight.setDrawLabels(false);
        yAxisRight.setDrawAxisLine(false);
        yAxisRight.setDrawGridLines(false);

        /*범례설정*/
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        lineChart.setData(lineData);

        /*차트 끝*/
    }

    public void println(Object data, TextView textView) {
        textView.setText(data.toString());
    }

}