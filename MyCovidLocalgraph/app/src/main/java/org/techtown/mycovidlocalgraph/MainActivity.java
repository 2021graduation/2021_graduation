package org.techtown.mycovidlocalgraph;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class MainActivity extends AppCompatActivity {

    static RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        sendRequest();
    }

    public void sendRequest() {

        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=pPaSpIZ%2BXFweoQb0rmHH5gguuqHRO00DHw7CgOuW9wZ2c5HDm%2BwqWpv%2B29V9NIHAcggmnJz3ztzM8206Hkkw7A%3D%3D&startCreateDt=20210417&endCreateDt=20210418";

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
        Covid covidList = gson.fromJson(xmlToJson.toJson().toString(), Covid.class);
        Item item;

        ArrayList arrayList = new ArrayList();
        ArrayList arrayList1 = new ArrayList();

        for(int i=0; i< covidList.response.body.items.item.size();i++){

            item = covidList.response.body.items.item.get(i);


                    arrayList.add(item.getIncDec());



        }
        Log.d("tag", String.valueOf(arrayList1));
        int num0 = Integer.parseInt((String) arrayList.get(0));
        int num1 = Integer.parseInt((String) arrayList.get(1));
        int num2 = Integer.parseInt((String) arrayList.get(2));
        int num3 = Integer.parseInt((String) arrayList.get(3));
        int num4 = Integer.parseInt((String) arrayList.get(4));
        int num5 = Integer.parseInt((String) arrayList.get(5));
        int num6 = Integer.parseInt((String) arrayList.get(6));
        int num7 = Integer.parseInt((String) arrayList.get(7));
        int num8 = Integer.parseInt((String) arrayList.get(8));
        int num9 = Integer.parseInt((String) arrayList.get(9));
        int num10 = Integer.parseInt((String) arrayList.get(10));
        int num11 = Integer.parseInt((String) arrayList.get(11));
        int num12 = Integer.parseInt((String) arrayList.get(12));
        int num13 = Integer.parseInt((String) arrayList.get(13));
        int num14 = Integer.parseInt((String) arrayList.get(14));
        int num15 = Integer.parseInt((String) arrayList.get(15));
        int num16 = Integer.parseInt((String) arrayList.get(16));
        int num17 = Integer.parseInt((String) arrayList.get(17));
        // 합계 int num18 = Integer.parseInt((String) arrayList.get(3));

        PieChart pieChart = findViewById(R.id.pie);

        // 중앙에 원 생기게 하는 거
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setHoleRadius(30f);
        pieChart.setTransparentCircleRadius(25f);

        /*중앙 제목 설정*/
        pieChart.setDrawCenterText(true);
        pieChart.setCenterText("일일\n확진자");
        pieChart.setCenterTextColor(Color.BLACK);
        pieChart.setCenterTextSizePixels(50f);
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD);

        pieChart.setRotationAngle(45);
        pieChart.setRotationEnabled(true);
        pieChart.setUsePercentValues(true); // 비율변수로 사용할지
        pieChart.getDescription().setEnabled(false);

        /*라벨 설정*/
        pieChart.setDrawEntryLabels(true);

        pieChart.setEntryLabelColor(Color.BLACK);
        pieChart.setEntryLabelTextSize(12f);
        pieChart.setEntryLabelTypeface(Typeface.DEFAULT_BOLD);
        pieChart.setExtraOffsets(5,5,5,50); // 차트 위치
        pieChart.setDragDecelerationFrictionCoef(0);


        Legend l = pieChart.getLegend();
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(10f);
        l.setYOffset(10);
        l.setXOffset(10f);
        l.setTextSize(12f);
        l.setEnabled(true);
        l.setWordWrapEnabled(true);




        ArrayList<PieEntry> yValues = new ArrayList<PieEntry>();
        yValues.add(new PieEntry(num0,"검역"));
        //yValues.add(new PieEntry(num1,"제주"));
        yValues.add(new PieEntry(num6+num7+num12+num10,"충청권"));
        yValues.add(new PieEntry(num2+num11+num16,"경남권"));
        yValues.add(new PieEntry(num1+num4+num5+num13,"호남권"));
        //yValues.add(new PieEntry(num5,"전북"));
        //yValues.add(new PieEntry(num2,"경남"));
        //yValues.add(new PieEntry(num7,"충북"));
        yValues.add(new PieEntry(num8,"강원권"));
        yValues.add(new PieEntry(num9+num14,"수도권"));
        //yValues.add(new PieEntry(num10,"세종"));
        //yValues.add(new PieEntry(num11,"울산"));
        //yValues.add(new PieEntry(num12,"대전"));
        //yValues.add(new PieEntry(num13,"광주"));
        //yValues.add(new PieEntry(num14,"인천"));
        yValues.add(new PieEntry(num15+num3,"경북권"));
        //yValues.add(new PieEntry(num16,"부산"));
        yValues.add(new PieEntry(num17,"서울"));



        PieDataSet dataSet = new PieDataSet(yValues,"(%)");

        dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
        dataSet.setDrawValues(true); // 그래프에 값을 표시 할지
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTypeface(Typeface.DEFAULT);


        dataSet.setValueLineWidth(2f);
        dataSet.setValueLinePart1Length(1f);
        dataSet.setValueLinePart2Length(0.5f);
        dataSet.setValueLinePart1OffsetPercentage(20f);
        dataSet.setValueLineColor(Color.parseColor("#a1a1a1"));
        dataSet.setXValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.INSIDE_SLICE);
        dataSet.setUsingSliceColorAsValueLineColor(false);
        dataSet.setSliceSpace(1f);
        dataSet.setSelectionShift(10f);



        dataSet.setUsingSliceColorAsValueLineColor(true);


        PieData data = new PieData(dataSet);

        pieChart.setData(data);



    }
}