package org.techtown.graduation_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
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
import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    Button hospital; // 병원 검색 버튼
    Button infection; // 코로나19 시도 발생 현황 검색 버튼
    Button mask; // 마스크 검색 버튼
    Button pharmacy; // 약국 검색 버튼
    Button MyLocation;

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
        stateDt = (TextView) findViewById(R.id.stateDt);
        stateTime = (TextView) findViewById(R.id.stateTime);
        Daily_decide = (TextView) findViewById(R.id.Daily_decide);
        Daily_accExam = (TextView) findViewById(R.id.Daily_accExam);
        Daily_clear = (TextView) findViewById(R.id.Daily_clear);
        Daily_death = (TextView) findViewById(R.id.Daily_death);

        hospital = findViewById(R.id.hospital);
        infection = findViewById(R.id.infection);
        mask = findViewById(R.id.mask);
        pharmacy = findViewById(R.id.pharmacy);
        MyLocation = findViewById(R.id.MyLocation);

        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Hospital.class);
                startActivity(intent);
            }
        });

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

        pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pharmacy_intent = new Intent(getApplicationContext(), Pharmacy.class);
                startActivity(pharmacy_intent);
            }
        });


        // RequestQueue 객체 생성하기
        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        sendRequest();

    }

    public void sendRequest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 년, 월, 일 데이터 포맷형식 설정
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance(); // 오늘날짜
        String today = sdf.format(calendar.getTime());
        String day = sdf2.format(calendar.getTime());
        stateDt.setText(day);
        calendar.add(Calendar.DATE, -1);  // 오늘 날짜에서 하루를 뺌.
        String yesterday = sdf.format(calendar.getTime());
        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey="
                +key+"&startCreateDt="+yesterday+"&endCreateDt="+today;

        // 요청을 보내기 위한 StringRequest객체 생성
        StringRequest request = new StringRequest(
                Request.Method.GET, // 첫번 째 파라미터 GET 메서드
                url, // 두 번째 파라미터 url 주소
                new com.android.volley.Response.Listener<String>() {
                    @Override  // 세 번째 파라미터 응답받을 리스너 객체
                    public void onResponse(String response) {
                        processResponse(response);
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

    public void processResponse(String response) {
        XmlToJson xmlToJson = new XmlToJson.Builder(response).build();
        Gson gson = new Gson();
        CovidLocal covidList = gson.fromJson(xmlToJson.toJson().toString(), CovidLocal.class);
        Item today = covidList.response.body.items.item.get(0);
        Item yesterday = covidList.response.body.items.item.get(1);

        DecimalFormat df = new DecimalFormat("#,###"); // 표현 패턴 설정

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
        println(today.stateTime,stateTime);


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
    public void println(Object data, TextView textView) {
        textView.setText(data.toString());
    }

}