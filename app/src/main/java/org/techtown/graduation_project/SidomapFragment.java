package org.techtown.graduation_project;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;


public class SidomapFragment extends Fragment {
    TextView seoul_data,gyeongi_data, incheon_data, gangwon_data, daegu_data, daejeon_data, gyeongnam_data, gyeongbuk_data, chungnam_data,chungbuk_data, sejong_data, ulsan_data, busan_data, jeonnam_data, jeonbuk_data, gwangju_data, jeju_data, geomyeog_data, total_data;

    static RequestQueue requestQueue;
    View view;

    public SidomapFragment() {
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sidomap, container, false);

        seoul_data = view.findViewById(R.id.seoul_data);gyeongi_data = view.findViewById(R.id.gyeongi_data);incheon_data = view.findViewById(R.id.incheon_data);gangwon_data = view.findViewById(R.id.gangwon_data);daegu_data = view.findViewById(R.id.daegu_data);daejeon_data = view.findViewById(R.id.daejeon_data);gyeongnam_data = view.findViewById(R.id.gyeongnam_data);gyeongbuk_data = view.findViewById(R.id.gyeonbuk_data);chungnam_data = view.findViewById(R.id.chungnam_data);chungbuk_data = view.findViewById(R.id.chungbuk_data);sejong_data = view.findViewById(R.id.sejong_data);ulsan_data = view.findViewById(R.id.ulsan_data);busan_data = view.findViewById(R.id.busan_data);jeonnam_data = view.findViewById(R.id.jeonnam_data);jeonbuk_data = view.findViewById(R.id.jeonbuk_data);gwangju_data = view.findViewById(R.id.gwangju_data);jeju_data = view.findViewById(R.id.jeju_data);geomyeog_data = view.findViewById(R.id.geomyeog_data);total_data = view.findViewById(R.id.total_data);


        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        }

        sendRequest();


        return view;
    }
    public void sendRequest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar calendar = Calendar.getInstance();
        String today =sdf.format(calendar.getTime());
        calendar.add(Calendar.DATE, -2);
        String yesterday =sdf.format(calendar.getTime());

        String url = "http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=pPaSpIZ%2BXFweoQb0rmHH5gguuqHRO00DHw7CgOuW9wZ2c5HDm%2BwqWpv%2B29V9NIHAcggmnJz3ztzM8206Hkkw7A%3D%3D"+"&startCreateDt="+yesterday+"&endCreateDt="+today;

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
                        Toast.makeText(getActivity().getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT);
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
        CovidSido covidSido = gson.fromJson(xmlToJson.toJson().toString(), CovidSido.class);
        CovidSidoItem item;

        DecimalFormat df = new DecimalFormat("#,###"); // 표현 패턴 설정

        ArrayList incList = new ArrayList();
        int inc_data[] = new int [covidSido.response.body.items.item.size()];
        for(int i=0; i< covidSido.response.body.items.item.size();i++){
            item = covidSido.response.body.items.item.get(i);
            incList.add(item.getIncDec());
            inc_data[i] =  Integer.parseInt((String) incList.get(i));
            println(df.format(inc_data[0]),geomyeog_data);
            println(df.format(inc_data[1]),jeju_data);
            println(df.format(inc_data[2]),gyeongnam_data);
            println(df.format(inc_data[3]),gyeongbuk_data);
            println(df.format(inc_data[4]),jeonnam_data);
            println(df.format(inc_data[5]),jeonbuk_data);
            println(df.format(inc_data[6]),chungnam_data);
            println(df.format(inc_data[7]),chungbuk_data);
            println(df.format(inc_data[8]),gangwon_data);
            println(df.format(inc_data[9]),gyeongi_data);
            println(df.format(inc_data[10]),sejong_data);
            println(df.format(inc_data[11]),ulsan_data);
            println(df.format(inc_data[12]),daejeon_data);
            println(df.format(inc_data[13]),gwangju_data);
            println(df.format(inc_data[14]),incheon_data);
            println(df.format(inc_data[15]),daegu_data);
            println(df.format(inc_data[16]),busan_data);
            println(df.format(inc_data[17]),seoul_data);
            println(df.format(inc_data[18]),total_data);
        }





    }
    public void println(Object data, TextView textView) {
        textView.setText(data.toString());
    }

}