package org.techtown.graduation_project;

import android.content.Intent;
import android.net.Uri;
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


public class SidotableFragment extends Fragment {
    /*sido TextView*/
    TextView seoul, gyeonggi, incheon, busan, daegu, ulsan, gyeongbuk, gyeongnam, daegeon, sejong, chungbuk, chungnam, gwangju, jeonbuk, jeonnam, jeju, gangwon, geomyeog;
    /*daily_def TextView*/
    TextView seoul_daily, gyeongi_daily, incheon_daily, busan_daily, daegu_daily, ulsan_daily, gyeongbuk_daily, gyeongnam_daily, daegeon_daily, sejong_daily, chungbuk_daily, chungnam_daily, gwangju_daily, jeonbuk_daily, jeonnam_daily, jeju_daily, gangwon_daily, geomyeog_daily;
    /*def TextView*/
    TextView seoul_def, gyeongi_def, incheon_def, busan_def, daegu_def, ulsan_def, gyeongbuk_def, gyeongnam_def, daegeon_def, sejong_def, chungbuk_def, chungnam_def, gwangju_def,jeonbuk_def, jeonnam_def, jeju_def, gangwon_def, geomyeog_def;
    /*death TextView*/
    TextView seoul_dea, gyeongi_dea, incheon_dea, busan_dea, daegu_dea, ulsan_dea, gyeongbuk_dea, gyeongnam_dea, daegeon_dea, sejong_dea, chungbuk_dea, chungnam_dea, gwangju_dea, jeonbuk_dea, jeonnam_dea, jeju_dea, gangwon_dea, geomyeog_dea;
    /*clear TextView*/
    TextView seoul_clear, gyeongi_clear, incheon_clear, busan_clear, daegu_clear,ulsan_clear, gyeongbuk_clear, gyeongnam_clear, daegeon_clear, sejong_clear, chungbuk_clear, chungnam_clear, gwangju_clear, jeonbuk_clear, jeonnam_clear, jeju_clear, gangwon_clear, geomyeog_clear;
    View view;
    static RequestQueue requestQueue;
    public SidotableFragment() {
        // Required empty public constructor
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sidotable, container, false);
        /*sido*/
        seoul = view.findViewById(R.id.seoul); gyeonggi = view.findViewById(R.id.gyeonggi); incheon = view.findViewById(R.id.incheon); busan = view.findViewById(R.id.busan); daegu = view.findViewById(R.id.daegeon); ulsan = view.findViewById(R.id.ulsan); gyeongbuk = view.findViewById(R.id.gyeongbuk); gyeongnam = view.findViewById(R.id.gyeongnam); daegeon = view.findViewById(R.id.daegeon); sejong = view.findViewById(R.id.sejong); chungbuk = view.findViewById(R.id.chungbuk); chungnam = view.findViewById(R.id.chungnam); gwangju = view.findViewById(R.id.gwangju); jeonbuk = view.findViewById(R.id.jeonbuk); jeonnam = view.findViewById(R.id.jeonnam); jeju = view.findViewById(R.id.jeju); gangwon = view.findViewById(R.id.gangwon); geomyeog = view.findViewById(R.id.geomyeog);
        /*daily_def id*/
        seoul_daily = view.findViewById(R.id.seoul_daily); gyeongi_daily = view.findViewById(R.id.gyeongi_daily); incheon_daily = view.findViewById(R.id.incheon_daily); busan_daily = view.findViewById(R.id.busan_daily); daegu_daily = view.findViewById(R.id.daegu_daily); ulsan_daily = view.findViewById(R.id.ulsan_daily); gyeongbuk_daily = view.findViewById(R.id.gyeongbuk_daily); gyeongnam_daily = view.findViewById(R.id.gyeongnam_daily); daegeon_daily = view.findViewById(R.id.daegeon_daily); sejong_daily = view.findViewById(R.id.sejong_daily); chungbuk_daily = view.findViewById(R.id.chungbuk_daily); chungnam_daily = view.findViewById(R.id.chungnam_daily); gwangju_daily = view.findViewById(R.id.gwangju_daily); jeonbuk_daily = view.findViewById(R.id.jeonbuk_daily); jeonnam_daily = view.findViewById(R.id.jeonnam_daily); jeju_daily = view.findViewById(R.id.jeju_daily); gangwon_daily = view.findViewById(R.id.gangwon_daily); geomyeog_daily = view.findViewById(R.id.geomyeog_daily);
        /*def id*/
        seoul_def = view.findViewById(R.id.seoul_def); gyeongi_def = view.findViewById(R.id.gyeongi_def); incheon_def = view.findViewById(R.id.incheon_def); busan_def = view. findViewById(R.id.busan_def); daegu_def = view.findViewById(R.id.daegu_def); ulsan_def = view.findViewById(R.id.ulsan_def); gyeongbuk_def = view.findViewById(R.id.gyeongbuk_def); gyeongnam_def = view.findViewById(R.id.gyeongnam_def); daegeon_def = view.findViewById(R.id.daegeon_def); sejong_def = view.findViewById(R.id.sejong_def); chungbuk_def = view.findViewById(R.id.chungbuk_def); chungnam_def = view.findViewById(R.id.chungnam_def); gwangju_def = view.findViewById(R.id.gwangju_def); jeonbuk_def = view.findViewById(R.id.jeonbuk_def); jeonnam_def = view.findViewById(R.id.jeonnam_def); jeju_def = view.findViewById(R.id.jeju_def); gangwon_def = view.findViewById(R.id.gangwon_def); geomyeog_def = view.findViewById(R.id.geomyeog_def);
        /*death id*/
        seoul_dea = view.findViewById(R.id.seoul_dea); gyeongi_dea = view.findViewById(R.id.gyeongi_dea); incheon_dea = view.findViewById(R.id.incheon_dea); busan_dea = view.findViewById(R.id.busan_dea); daegu_dea = view.findViewById(R.id.daegu_dea); ulsan_dea = view.findViewById(R.id.ulsan_dea); gyeongbuk_dea = view.findViewById(R.id.gyeongbuk_dea); gyeongnam_dea = view.findViewById(R.id.gyeongnam_dea); daegeon_dea = view.findViewById(R.id.daegeon_dea); sejong_dea = view.findViewById(R.id.sejong_dea); chungbuk_dea = view.findViewById(R.id.chungbuk_dea); chungnam_dea = view.findViewById(R.id.chungnam_dea); gwangju_dea = view.findViewById(R.id.gwangju_dea); jeonbuk_dea = view.findViewById(R.id.jeonbuk_dea); jeonnam_dea = view.findViewById(R.id.jeonnam_dea); jeju_dea = view.findViewById(R.id.jeju_dea); gangwon_dea = view.findViewById(R.id.gangwon_dea); geomyeog_dea = view.findViewById(R.id.geomyeog_dea);
        /*clear id*/
        seoul_clear = view.findViewById(R.id.seoul_clear);gyeongi_clear = view.findViewById(R.id.gyeongi_clear);incheon_clear = view.findViewById(R.id.incheon_clear);busan_clear = view.findViewById(R.id.busan_clear);ulsan_clear = view.findViewById(R.id.ulsan_clear);daegu_clear = view.findViewById(R.id.daegu_clear);gyeongbuk_clear = view.findViewById(R.id.gyeongbuk_clear);gyeongnam_clear = view.findViewById(R.id.gyeongnam_clear);daegeon_clear = view.findViewById(R.id.daegeon_clear);sejong_clear = view.findViewById(R.id.sejong_clear);chungbuk_clear = view.findViewById(R.id.chungbuk_clear);chungnam_clear = view.findViewById(R.id.chungnam_clear);gwangju_clear = view.findViewById(R.id.gwangju_clear);jeonbuk_clear = view.findViewById(R.id.jeonbuk_clear);jeonnam_clear = view.findViewById(R.id.jeonnam_clear);jeju_clear = view.findViewById(R.id.jeju_clear);gangwon_clear = view.findViewById(R.id.gangwon_clear);geomyeog_clear = view.findViewById(R.id.geomyeog_clear);

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
        CovidSido covidList = gson.fromJson(xmlToJson.toJson().toString(), CovidSido.class);
        CovidSidoItem item;
        DecimalFormat df = new DecimalFormat("#,###"); // 표현 패턴 설정

        ArrayList dailyList = new ArrayList(); // 일일 확진자
        ArrayList defList = new ArrayList(); // 확진자
        ArrayList deaList = new ArrayList(); // 사망자
        ArrayList clearList = new ArrayList(); // 사망자
        int inc_data[] = new int [covidList.response.body.items.item.size()];
        int def_data[] = new int[covidList.response.body.items.item.size()];
        int dea_data[] = new int[covidList.response.body.items.item.size()];
        int cle_data[] = new int[covidList.response.body.items.item.size()];
        for(int i=0; i< covidList.response.body.items.item.size();i++){
            item = covidList.response.body.items.item.get(i);
            dailyList.add(item.getIncDec());
            defList.add(item.getDefCnt());
            deaList.add(item.getDeathCnt());
            clearList.add(item.getIsolClearCnt());
            inc_data[i] =  Integer.parseInt((String) dailyList.get(i));
            def_data[i] = Integer.parseInt((String) defList.get(i));
            dea_data[i] = Integer.parseInt((String) deaList.get(i));
            cle_data[i] = Integer.parseInt((String) clearList.get(i));

            /*daily_def*/
            println(df.format(inc_data[0]),geomyeog_daily);
            println(df.format(inc_data[1]),jeju_daily);
            println(df.format(inc_data[2]),gyeongnam_daily);
            println(df.format(inc_data[3]),gyeongbuk_daily);
            println(df.format(inc_data[4]),jeonnam_daily);
            println(df.format(inc_data[5]),jeonbuk_daily);
            println(df.format(inc_data[6]),chungnam_daily);
            println(df.format(inc_data[7]),chungbuk_daily);
            println(df.format(inc_data[8]),gangwon_daily);
            println(df.format(inc_data[9]),gyeongi_daily);
            println(df.format(inc_data[10]),sejong_daily);
            println(df.format(inc_data[11]),ulsan_daily);
            println(df.format(inc_data[12]),daegeon_daily);
            println(df.format(inc_data[13]),gwangju_daily);
            println(df.format(inc_data[14]),incheon_daily);
            println(df.format(inc_data[15]),daegu_daily);
            println(df.format(inc_data[16]),busan_daily);
            println(df.format(inc_data[17]),seoul_daily);

            /*def*/
            println(df.format(def_data[0]),geomyeog_def);
            println(df.format(def_data[1]),jeju_def);
            println(df.format(def_data[2]),gyeongnam_def);
            println(df.format(def_data[3]),gyeongbuk_def);
            println(df.format(def_data[4]),jeonnam_def);
            println(df.format(def_data[5]),jeonbuk_def);
            println(df.format(def_data[6]),chungnam_def);
            println(df.format(def_data[7]),chungbuk_def);
            println(df.format(def_data[8]),gangwon_def);
            println(df.format(def_data[9]),gyeongi_def);
            println(df.format(def_data[10]),sejong_def);
            println(df.format(def_data[11]),ulsan_def);
            println(df.format(def_data[12]),daegeon_def);
            println(df.format(def_data[13]),gwangju_def);
            println(df.format(def_data[14]),incheon_def);
            println(df.format(def_data[15]),daegu_def);
            println(df.format(def_data[16]),busan_def);
            println(df.format(def_data[17]),seoul_def);

            /*death*/
            println(df.format(dea_data[0]),geomyeog_dea);
            println(df.format(dea_data[1]),jeju_dea);
            println(df.format(dea_data[2]),gyeongnam_dea);
            println(df.format(dea_data[3]),gyeongbuk_dea);
            println(df.format(dea_data[4]),jeonnam_dea);
            println(df.format(dea_data[5]),jeonbuk_dea);
            println(df.format(dea_data[6]),chungnam_dea);
            println(df.format(dea_data[7]),chungbuk_dea);
            println(df.format(dea_data[8]),gangwon_dea);
            println(df.format(dea_data[9]),gyeongi_dea);
            println(df.format(dea_data[10]),sejong_dea);
            println(df.format(dea_data[11]),ulsan_dea);
            println(df.format(dea_data[12]),daegeon_dea);
            println(df.format(dea_data[13]),gwangju_dea);
            println(df.format(dea_data[14]),incheon_dea);
            println(df.format(dea_data[15]),daegu_dea);
            println(df.format(dea_data[16]),busan_dea);
            println(df.format(dea_data[17]),seoul_dea);

            /*clear*/
            println(df.format(cle_data[0]),geomyeog_clear);
            println(df.format(cle_data[1]),jeju_clear);
            println(df.format(cle_data[2]),gyeongnam_clear);
            println(df.format(cle_data[3]),gyeongbuk_clear);
            println(df.format(cle_data[4]),jeonnam_clear);
            println(df.format(cle_data[5]),jeonbuk_clear);
            println(df.format(cle_data[6]),chungnam_clear);
            println(df.format(cle_data[7]),chungbuk_clear);
            println(df.format(cle_data[8]),gangwon_clear);
            println(df.format(cle_data[9]),gyeongi_clear);
            println(df.format(cle_data[10]),sejong_clear);
            println(df.format(cle_data[11]),ulsan_clear);
            println(df.format(cle_data[12]),daegeon_clear);
            println(df.format(cle_data[13]),gwangju_clear);
            println(df.format(cle_data[14]),incheon_clear);
            println(df.format(cle_data[15]),daegu_clear);
            println(df.format(cle_data[16]),busan_clear);
            println(df.format(cle_data[17]),seoul_clear);

        }





    }
    public void println(Object data, TextView textView) {
        textView.setText(data.toString());
    }
}