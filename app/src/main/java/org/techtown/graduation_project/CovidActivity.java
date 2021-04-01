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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class CovidActivity extends AppCompatActivity {

    TextView decide;
    TextView accExam;
    TextView clearCnt;
    TextView deathCnt;
    TextView stateDt;
    TextView stateTime;
    TextView Daily_decide;
    TextView Daily_accExam;
    TextView Daily_clear;
    TextView Daily_death;

    Button hospital;
    Button infection;
    Button mask;
    Button pharmacy;

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        sendRequest();
    }
    public void sendRequest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance(); // 오늘날짜
        String today = sdf.format(calendar.getTime());
        String day = sdf2.format(calendar.getTime());
        stateDt.setText(day);
        calendar.add(Calendar.DATE, -1);  // 오늘 날짜에서 하루를 뺌.
        String yesterday = sdf.format(calendar.getTime());

        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey=pPaSpIZ%2BXFweoQb0rmHH5gguuqHRO00DHw7CgOuW9wZ2c5HDm%2BwqWpv%2B29V9NIHAcggmnJz3ztzM8206Hkkw7A%3D%3D&startCreateDt="+yesterday+"&endCreateDt="+today;

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
        XmlToJson xmlToJson = new XmlToJson.Builder(response).build();
        Gson gson = new Gson();
        CovidLocal covidList = gson.fromJson(xmlToJson.toJson().toString(), CovidLocal.class);
        Item today = covidList.response.body.items.item.get(0);
        Item yesterday = covidList.response.body.items.item.get(1);

        println(today.decideCnt, decide);
        println(today.accExamCnt,accExam);
        println(today.clearCnt, clearCnt);
        println(today.deathCnt, deathCnt);
        println(today.stateTime,stateTime);

        int blue = ContextCompat.getColor(getApplicationContext(), R.color.blue);
        int red = ContextCompat.getColor(getApplicationContext(), R.color.red);

        int dec_inter = Integer.parseInt(today.decideCnt)-Integer.parseInt(yesterday.decideCnt);
        int acc_inter = Integer.parseInt(today.accExamCnt)-Integer.parseInt(yesterday.accExamCnt);
        int clear_inter = Integer.parseInt(today.clearCnt) - Integer.parseInt(yesterday.clearCnt);
        int death_inter = Integer.parseInt(today.deathCnt) - Integer.parseInt(yesterday.deathCnt);

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
            println(acc_inter, Daily_accExam);
            Daily_accExam.setCompoundDrawablesWithIntrinsicBounds(R.drawable.down,0,0,0);
        }
        else if(acc_inter > 0) {
            Daily_accExam.setTextColor(red);
            println(acc_inter, Daily_accExam);
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