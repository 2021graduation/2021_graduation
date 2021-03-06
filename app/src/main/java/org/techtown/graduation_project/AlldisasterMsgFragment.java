package org.techtown.graduation_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AlldisasterMsgFragment extends Fragment {

    ArrayAdapter<CharSequence> adspin1, adspin2;

    private ArrayList<DisasterRowData> rowData;
    private DisasterAdapter disasterAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public DisasterRowData row;

    Button button;
    Spinner spin1;
    Spinner spin2;
    static RequestQueue requestQueue;

    View view;

    public AlldisasterMsgFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_alldisaster, container, false);

        recyclerView = view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);


        recyclerView.setLayoutManager(linearLayoutManager);

        rowData = new ArrayList<>();

        disasterAdapter = new DisasterAdapter(rowData);
        recyclerView.setAdapter(disasterAdapter);

        button = view.findViewById(R.id.button);

        spin1 = (Spinner) view.findViewById(R.id.spinner);
        spin2 = (Spinner) view.findViewById(R.id.spinner2);

        adspin1 = ArrayAdapter.createFromResource(getActivity(), R.array.sido, android.R.layout.simple_spinner_dropdown_item);


        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());


        }
        sendRequest();

        return view;
    }

    public void sendRequest() {

        String url = "https://apixml-5d25d-default-rtdb.firebaseio.com/Msg.json";

        StringRequest request = new StringRequest(
                Request.Method.GET,
                url,
                (Response.Listener<String>) response -> {
                    //adspin1 = ArrayAdapter.createFromResource(getActivity(), R.array.sido, android.R.layout.simple_spinner_dropdown_item);
                    adspin1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spin1.setAdapter(adspin1);
                    spin1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.all, android.R.layout.simple_spinner_dropdown_item);

                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            All(response);
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> adapterView) {
                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.seoul, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    seoulAll(response);
                                                    break;
                                                case "?????????":
                                                    seoul_gangnam(response);
                                                    break;
                                                case "?????????":
                                                    seoul_gangdong(response);
                                                    break;
                                                case "?????????":
                                                    seoul_gangbuk(response);
                                                    break;
                                                case "?????????":
                                                    seoul_gangseo(response);
                                                    break;
                                                case "?????????":
                                                    seoul_gwanak(response);
                                                    break;
                                                case "?????????":
                                                    seoul_gwangin(response);
                                                    break;
                                                case "?????????":
                                                    seoul_guro(response);
                                                    break;
                                                case "?????????":
                                                    seoul_geumcheon(response);
                                                    break;
                                                case "?????????":
                                                    seoul_nowon(response);
                                                    break;
                                                case "?????????":
                                                    seoul_dobong(response);
                                                    break;
                                                case "????????????":
                                                    seoul_dongdaemun(response);
                                                    break;
                                                case "?????????":
                                                    seoul_dongjak(response);
                                                    break;
                                                case "?????????":
                                                    seoul_mapo(response);
                                                    break;
                                                case "????????????":
                                                    seoul_seodaemun(response);
                                                    break;
                                                case "?????????":
                                                    seoul_seocho(response);
                                                    break;
                                                case "?????????":
                                                    seoul_seongdong(response);
                                                    break;
                                                case "?????????":
                                                    seoul_seongbuk(response);
                                                    break;
                                                case "?????????":
                                                    seoul_songpa(response);
                                                    break;
                                                case "?????????":
                                                    seoul_yangcheon(response);
                                                    break;
                                                case "????????????":
                                                    seoul_yeongdeungpo(response);
                                                    break;
                                                case "?????????":
                                                    seoul_yongsan(response);
                                                    break;
                                                case "?????????":
                                                    seoul_eunpyeong(response);
                                                    break;
                                                case "?????????":
                                                    seoul_jongro(response);
                                                    break;
                                                case "??????":
                                                    seoul_junggu(response);
                                                    break;
                                                case "?????????":
                                                    seoul_jungrang(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.busan, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    busanAll(response);
                                                    break;
                                                case "?????????":
                                                    busan_gangseo(response);
                                                    break;
                                                case "?????????":
                                                    busan_geumjeong(response);
                                                    break;
                                                case "?????????":
                                                    busan_gijang(response);
                                                    break;
                                                case "??????":
                                                    busan_namgu(response);
                                                    break;
                                                case "??????":
                                                    busan_donggu(response);
                                                    break;
                                                case "?????????":
                                                    busan_dongrae(response);
                                                    break;
                                                case "????????????":
                                                    busan_busanjin(response);
                                                    break;
                                                case "??????":
                                                    busan_bukgu(response);
                                                    break;
                                                case "?????????":
                                                    busan_sasang(response);
                                                    break;
                                                case "?????????":
                                                    busan_saha(response);
                                                    break;
                                                case "??????":
                                                    busan_seogu(response);
                                                    break;
                                                case "?????????":
                                                    busan_suyeong(response);
                                                    break;
                                                case "?????????":
                                                    busan_yeonjae(response);
                                                    break;
                                                case "?????????":
                                                    busan_yeongdo(response);
                                                    break;
                                                case "??????":
                                                    busan_junggu(response);
                                                    break;
                                                case "????????????":
                                                    busan_haeeundae(response);
                                                    break;
                                            }

                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.daegu, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    daeguAll(response);
                                                    break;
                                                case "??????":
                                                    daegu_namgu(response);
                                                    break;
                                                case "?????????":
                                                    daegu_dalseo(response);
                                                    break;
                                                case "?????????":
                                                    daegu_dalseong(response);
                                                    break;
                                                case "??????":
                                                    daegu_donggu(response);
                                                    break;
                                                case "??????":
                                                    daegu_bukgu(response);
                                                    break;
                                                case "??????":
                                                    daegu_seogu(response);
                                                    break;
                                                case "?????????":
                                                    daegu_susung(response);
                                                    break;
                                                case "??????":
                                                    daegu_junggu(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.incheon, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    incheonAll(response);
                                                    break;
                                                case "?????????":
                                                    incheon_ganghwa(response);
                                                    break;
                                                case "?????????":
                                                    incheon_gaeyang(response);
                                                    break;
                                                case "????????????":
                                                    incheon_michuhol(response);
                                                    break;
                                                case "?????????":
                                                    incheon_namdong(response);
                                                    break;
                                                case "??????":
                                                    incheon_donggu(response);
                                                    break;
                                                case "?????????":
                                                    incheon_bupyeong(response);
                                                    break;
                                                case "??????":
                                                    incheon_seogu(response);
                                                    break;
                                                case "?????????":
                                                    incheon_yeonsu(response);
                                                    break;
                                                case "?????????":
                                                    incheon_ongjin(response);
                                                    break;
                                                case "??????":
                                                    incheon_junggu(response);
                                                    break;
                                            }
                                        });

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.gwangju, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    gwangjuAll(response);
                                                    break;
                                                case "?????????":
                                                    gwangju_gwangsan(response);
                                                    break;
                                                case "??????":
                                                    gwangju_namgu(response);
                                                    break;
                                                case "??????":
                                                    gwangju_donggu(response);
                                                    break;
                                                case "??????":
                                                    gwangju_bukgu(response);
                                                    break;
                                                case "??????":
                                                    gwangju_seogu(response);
                                                    break;
                                            }

                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.daejeon, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    daejeonAll(response);
                                                    break;
                                                case "?????????":
                                                    daejeon_daedeok(response);
                                                    break;
                                                case "??????":
                                                    daejeon_donggu(response);
                                                    break;
                                                case "??????":
                                                    daejeon_seogu(response);
                                                    break;
                                                case "?????????":
                                                    daejeon_yuseong(response);
                                                    break;
                                                case "??????":
                                                    daejeon_junggu(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.ulsan, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    ulsannAll(response);
                                                    break;
                                                case "??????":
                                                    ulsan_namgu(response);
                                                    break;
                                                case "??????":
                                                    ulsan_donggu(response);
                                                    break;
                                                case "??????":
                                                    ulsan_bukgu(response);
                                                    break;
                                                case "?????????":
                                                    ulsan_ulju(response);
                                                    break;
                                                case "??????":
                                                    ulsan_junggu(response);
                                                    break;
                                            }

                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.sejong, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> sejongAll(response));
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.gyeonggi, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    gyeonggiAll(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_gapyeong(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_goyang(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_gwacheon(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_gwangmyeong(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_gwangju(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_guri(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_gunpo(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_gimpo(response);
                                                    break;
                                                case "????????????":
                                                    gyeonggi_namyangju(response);
                                                    break;
                                                case "????????????":
                                                    gyeonggi_dongducheon(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_bucheon(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_seongnam(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_suwon(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_siheung(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_ansan(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_anseong(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_anyang(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_yangju(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_yangpyeong(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_yeoju(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_yeoncheon(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_osan(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_yongin(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_uiwang(response);
                                                    break;
                                                case "????????????":
                                                    gyeonggi_uijeongbu(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_icheon(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_paju(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_pyeongtaek(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_pocheon(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_hanam(response);
                                                    break;
                                                case "?????????":
                                                    gyeonggi_hwaseong(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.gangwon, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    gangwonAll(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_gangneung(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_gosung(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_donghae(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_samcheok(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_sokcho(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_yanggu(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_yangyang(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_yeongwol(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_wonju(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_inje(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_jeongsun(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_cheorwon(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_chuncheon(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_taebaek(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_pyeongchang(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_hongcheon(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_hwacheon(response);
                                                    break;
                                                case "?????????":
                                                    gangwon_hoengseong(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.chungbuk, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    chungbukAll(response);
                                                    break;
                                                case "?????????":
                                                    chungbuk_goesan(response);
                                                    break;
                                                case "?????????":
                                                    chungbuk_danyang(response);
                                                    break;
                                                case "?????????":
                                                    chungbuk_boeun(response);
                                                    break;
                                                case "?????????":
                                                    chungbuk_yeongdong(response);
                                                    break;
                                                case "?????????":
                                                    chungbuk_okcheon(response);
                                                    break;
                                                case "?????????":
                                                    chungbuk_eumseong(response);
                                                    break;
                                                case "?????????":
                                                    chungbuk_jaecheon(response);
                                                    break;
                                                case "?????????":
                                                    chungbuk_jeungpyeong(response);
                                                    break;
                                                case "?????????":
                                                    chungbuk_jincheon(response);
                                                    break;
                                                case "?????????":
                                                    chungbuk_cheongju(response);
                                                    break;
                                                case "?????????":
                                                    chungbuk_chungju(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.chungnam, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    chungnamAll(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_gyeryong(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_gongju(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_geumsan(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_nonsan(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_dangjin(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_boryeong(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_buyeo(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_seosan(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_seocheon(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_asan(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_yaesan(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_cheonan(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_chungyang(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_taean(response);
                                                    break;
                                                case "?????????":
                                                    chungnam_hongseong(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.jeonbuk, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    jeonbukAll(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_gochang(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_gunsan(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_gimjae(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_namwon(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_muju(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_buan(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_sunchang(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_wanju(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_iksan(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_imsil(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_jangsu(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_jeonju(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_jeongeup(response);
                                                    break;
                                                case "?????????":
                                                    jeonbuk_jinan(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.jeonnam, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    jeonnamAll(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_gangjin(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_goheung(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_gokseung(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_gwangyang(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_gurae(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_naju(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_damyang(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_mokpo(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_muan(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_boseung(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_suncheon(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_sinan(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_yeosu(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_yeonggwang(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_yeongam(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_wando(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_jangseung(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_jangheung(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_jindo(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_hampyeong(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_haenam(response);
                                                    break;
                                                case "?????????":
                                                    jeonnam_hwasun(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.gyeongbuk, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    gyeongbukAll(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_gyeongsan(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_gyeongju(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_goryeong(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_gumi(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_gunwi(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_gimcheon(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_mungyeong(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_bonghwa(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_sangju(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_seungju(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_andong(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_yeongdeok(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_yeongyang(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_yeongju(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_yeongcheon(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_yaecheon(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_ulleung(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_uljin(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_jusoen(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_chungdo(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_chungsong(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_chilgok(response);
                                                    break;
                                                case "?????????":
                                                    gyeongbuk_pohang(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.gyeonnam, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    gyeongnamAll(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_geoje(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_geochang(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_goseong(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_gimhae(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_namhae(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_milyang(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_sacheon(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_sancheong(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_yangsan(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_uiryeong(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_jinju(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_changnyeong(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_changwon(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_tongyeong(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_hadong(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_haman(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_hamyang(response);
                                                    break;
                                                case "?????????":
                                                    gyeongnam_habcheon(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("??????")) {
                                adspin2 = ArrayAdapter.createFromResource(getActivity(), R.array.jeju, android.R.layout.simple_spinner_dropdown_item);
                                adspin2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spin2.setAdapter(adspin2);
                                spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                                        disasterAdapter.Clear();
                                        button.setOnClickListener(v -> {
                                            disasterAdapter.Clear();
                                            String str = adspin2.getItem(i).toString();
                                            switch (str) {
                                                case "??????":
                                                    jejuAll(response);
                                                    break;
                                                case "????????????":
                                                    jeju_seogwipo(response);
                                                    break;
                                                case "?????????":
                                                    jeju_jeju(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                },
                (Response.ErrorListener) error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT)
        ) {
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<>();

                return params;
            }
        };
        request.setShouldCache(false);
        requestQueue.add(request);
    }

    // ?????? ???????????? ??????
    public void All(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){

            row = disasterMsg.DisasterMsg.row.get(i);
            disasterAdapter.addItem(row);
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void seoulAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("137") || row.location_id.equals("138") || row.location_id.equals("139")
                    || row.location_id.equals("140") || row.location_id.equals("141") || row.location_id.equals("142") || row.location_id.equals("143")
                    || row.location_id.equals("144") || row.location_id.equals("145") || row.location_id.equals("146") || row.location_id.equals("147")
                    || row.location_id.equals("148") || row.location_id.equals("149") || row.location_id.equals("150") || row.location_id.equals("151")
                    || row.location_id.equals("152") || row.location_id.equals("153") || row.location_id.equals("154") || row.location_id.equals("155")
                    || row.location_id.equals("156") || row.location_id.equals("157") || row.location_id.equals("158") || row.location_id.equals("159")
                    || row.location_id.equals("160") || row.location_id.equals("161")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_gangnam(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("137")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_gangdong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("138")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_gangbuk(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("139")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_gangseo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("140")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_gwanak(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("141")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_gwangin(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("142")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_guro(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("143")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_geumcheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("144")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_nowon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("145")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_dobong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("146")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_dongdaemun(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("147")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_dongjak(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("148")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_mapo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("149")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_seodaemun(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("150")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_seocho(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("151")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_seongdong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("152")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_seongbuk(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("153")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_songpa(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("154")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_yangcheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("148")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_yeongdeungpo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("156")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_yongsan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("157")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_eunpyeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("158")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_jongro(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("159")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_junggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("160")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void seoul_jungrang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("136") || row.location_id.equals("161")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void busanAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("120")|| row.location_id.equals("121")|| row.location_id.equals("122")
                    || row.location_id.equals("123") || row.location_id.equals("124")|| row.location_id.equals("125")|| row.location_id.equals("126")
                    || row.location_id.equals("127") || row.location_id.equals("128")|| row.location_id.equals("129")|| row.location_id.equals("130")
                    || row.location_id.equals("131") || row.location_id.equals("132")|| row.location_id.equals("133")|| row.location_id.equals("134")
                    || row.location_id.equals("135")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_gangseo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("120")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_geumjeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("121")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_gijang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("122")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_namgu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("123")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_donggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("124")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_dongrae(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("125")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_busanjin(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("126")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_bukgu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("127")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_sasang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("128")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_saha(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("129")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_seogu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("130")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_suyeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("131")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_yeonjae(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("132")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_yeongdo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("133")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_junggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("134")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void busan_haeeundae(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("119") || row.location_id.equals("135")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void daeguAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("104") || row.location_id.equals("105") || row.location_id.equals("106") || row.location_id.equals("107")
                    || row.location_id.equals("108") || row.location_id.equals("109") || row.location_id.equals("110") || row.location_id.equals("111")
                    || row.location_id.equals("112")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daegu_namgu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("104") || row.location_id.equals("105")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daegu_dalseo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("104") || row.location_id.equals("106")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daegu_dalseong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("104") || row.location_id.equals("107")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daegu_donggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("104") || row.location_id.equals("108")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daegu_bukgu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("104") || row.location_id.equals("109")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daegu_seogu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("104") || row.location_id.equals("110")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daegu_susung(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("104") || row.location_id.equals("111")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daegu_junggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("104") || row.location_id.equals("112")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void incheonAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("168") || row.location_id.equals("169") || row.location_id.equals("170") || row.location_id.equals("171")
                    || row.location_id.equals("172") || row.location_id.equals("173") || row.location_id.equals("174") || row.location_id.equals("175")
                    || row.location_id.equals("176") || row.location_id.equals("177") || row.location_id.equals("178")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void incheon_ganghwa(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("168") || row.location_id.equals("169")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void incheon_gaeyang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("168") || row.location_id.equals("170")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void incheon_michuhol(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("168") || row.location_id.equals("171")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void incheon_namdong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("168") || row.location_id.equals("172")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void incheon_donggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("168") || row.location_id.equals("173")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void incheon_bupyeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("168") || row.location_id.equals("174")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void incheon_seogu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("168") || row.location_id.equals("175")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void incheon_yeonsu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("168") || row.location_id.equals("176")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void incheon_ongjin(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("168") || row.location_id.equals("177")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void incheon_junggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("168") || row.location_id.equals("178")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void gwangjuAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("98") || row.location_id.equals("99") || row.location_id.equals("100") || row.location_id.equals("101")
                    || row.location_id.equals("102") || row.location_id.equals("103")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gwangju_gwangsan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("98") || row.location_id.equals("99")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gwangju_namgu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("98") || row.location_id.equals("100")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gwangju_donggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("98") || row.location_id.equals("101")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gwangju_bukgu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("98") || row.location_id.equals("102")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gwangju_seogu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("98") || row.location_id.equals("103")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void daejeonAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("113") || row.location_id.equals("114") || row.location_id.equals("115") || row.location_id.equals("116")
                    || row.location_id.equals("117") || row.location_id.equals("118")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daejeon_daedeok(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("113") || row.location_id.equals("114")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daejeon_donggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("113") || row.location_id.equals("115")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daejeon_seogu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("113") || row.location_id.equals("116")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daejeon_yuseong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("113") || row.location_id.equals("117")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void daejeon_junggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("113") || row.location_id.equals("118")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void ulsannAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("162") || row.location_id.equals("163") || row.location_id.equals("164") || row.location_id.equals("165")
                    || row.location_id.equals("166") || row.location_id.equals("167")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void ulsan_namgu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("162") || row.location_id.equals("163")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void ulsan_donggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("162") || row.location_id.equals("164")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void ulsan_bukgu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("162") || row.location_id.equals("165")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void ulsan_ulju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("162") || row.location_id.equals("166")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void ulsan_junggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("162") || row.location_id.equals("167")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void sejongAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("6474")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void gyeonggiAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("22") || row.location_id.equals("23") || row.location_id.equals("24")
                    || row.location_id.equals("25") || row.location_id.equals("26") || row.location_id.equals("27") || row.location_id.equals("28")
                    || row.location_id.equals("29") || row.location_id.equals("30") || row.location_id.equals("31") || row.location_id.equals("32")
                    || row.location_id.equals("33") || row.location_id.equals("34") || row.location_id.equals("35") || row.location_id.equals("36")
                    || row.location_id.equals("37") || row.location_id.equals("38") || row.location_id.equals("39") || row.location_id.equals("40")
                    || row.location_id.equals("41") || row.location_id.equals("42") || row.location_id.equals("43") || row.location_id.equals("44")
                    || row.location_id.equals("45") || row.location_id.equals("46") || row.location_id.equals("47") || row.location_id.equals("48")
                    || row.location_id.equals("49") || row.location_id.equals("50") || row.location_id.equals("51") || row.location_id.equals("52")
                    || row.location_id.equals("6487")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_gapyeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("22")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_goyang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("23")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_gwacheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("24")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_gwangmyeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("25")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_gwangju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("26")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_guri(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("27")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_gunpo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("28")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_gimpo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("29")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_namyangju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("30")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_dongducheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("31")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_bucheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("32")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_seongnam(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("33")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_suwon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("34")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_siheung(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("35")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_ansan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("36")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_anseong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("37")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_anyang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("38")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_yangju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("39")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_yangpyeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("40")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_yeoju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("41")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_yeoncheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("42") || row.location_id.equals("6487")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_osan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("43")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_yongin(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("44")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_uiwang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("45")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_uijeongbu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("46")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_icheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("47")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_paju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("48") || row.location_id.equals("6487")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_pyeongtaek(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("49")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_pocheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("50")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_hanam(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("51")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeonggi_hwaseong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("21") || row.location_id.equals("52")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void gangwonAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("3") || row.location_id.equals("4") || row.location_id.equals("5")
                    || row.location_id.equals("6") || row.location_id.equals("7") || row.location_id.equals("8") || row.location_id.equals("9")
                    || row.location_id.equals("10") || row.location_id.equals("11") || row.location_id.equals("12") || row.location_id.equals("13")
                    || row.location_id.equals("14") || row.location_id.equals("15") || row.location_id.equals("16") || row.location_id.equals("17")
                    || row.location_id.equals("18") || row.location_id.equals("19") || row.location_id.equals("20")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_gangneung(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("3")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_gosung(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("4")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_donghae(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("5")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_samcheok(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("6")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_sokcho(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("7")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_yanggu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("8")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_yangyang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("9")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_yeongwol(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("10")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_wonju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("11")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_inje(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("12")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_jeongsun(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("13")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_cheorwon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("14")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_chuncheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("15")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_taebaek(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("16")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_pyeongchang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("17")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_hongcheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("18")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_hwacheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("19")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gangwon_hoengseong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("2") || row.location_id.equals("20")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void chungbukAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("239") || row.location_id.equals("240") || row.location_id.equals("241")
                    || row.location_id.equals("242") || row.location_id.equals("243") || row.location_id.equals("244") || row.location_id.equals("245")
                    || row.location_id.equals("246") || row.location_id.equals("248") || row.location_id.equals("249") || row.location_id.equals("250")
                    || row.location_id.equals("6406")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungbuk_goesan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("239")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungbuk_danyang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("240")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungbuk_boeun(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("241")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungbuk_yeongdong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("242")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungbuk_okcheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("243")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungbuk_eumseong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("244")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungbuk_jaecheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("245")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungbuk_jeungpyeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("6406")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungbuk_jincheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("246")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungbuk_cheongju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("248")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungbuk_chungju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("238") || row.location_id.equals("249")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void chungnamAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("223") || row.location_id.equals("224") || row.location_id.equals("225")
                    || row.location_id.equals("226") || row.location_id.equals("227") || row.location_id.equals("228") || row.location_id.equals("229")
                    || row.location_id.equals("230") || row.location_id.equals("231") || row.location_id.equals("233") || row.location_id.equals("234")
                    || row.location_id.equals("235") || row.location_id.equals("236") || row.location_id.equals("237")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_gyeryong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("250")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_gongju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("223")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_geumsan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("224")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_nonsan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("225")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_dangjin(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("226")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_boryeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("227")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_buyeo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("228")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_seosan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("229")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_seocheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("230")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_asan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("231")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_yaesan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("233")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_cheonan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("234")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_chungyang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("235")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_taean(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("236")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void chungnam_hongseong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("222") || row.location_id.equals("237")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void jeonbukAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("203") || row.location_id.equals("204") || row.location_id.equals("205")
                    || row.location_id.equals("206") || row.location_id.equals("207") || row.location_id.equals("208") || row.location_id.equals("209")
                    || row.location_id.equals("210") || row.location_id.equals("211") || row.location_id.equals("212") || row.location_id.equals("213")
                    || row.location_id.equals("214") || row.location_id.equals("215") || row.location_id.equals("216")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_gochang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("203")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_gunsan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("204")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_gimjae(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("205")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_namwon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("206")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_muju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("207")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_buan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("208")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_sunchang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("209")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_wanju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("210")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_iksan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("211")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_imsil(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("212")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_jangsu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("213")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_jeonju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("214")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_jeongeup(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("215")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonbuk_jinan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("202") || row.location_id.equals("216")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void jeonnamAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("180") || row.location_id.equals("181") || row.location_id.equals("182")
                    || row.location_id.equals("183") || row.location_id.equals("184") || row.location_id.equals("185") || row.location_id.equals("186")
                    || row.location_id.equals("187") || row.location_id.equals("188") || row.location_id.equals("189") || row.location_id.equals("190")
                    || row.location_id.equals("191") || row.location_id.equals("192") || row.location_id.equals("193") || row.location_id.equals("194")
                    || row.location_id.equals("195") || row.location_id.equals("196") || row.location_id.equals("197") || row.location_id.equals("198")
                    || row.location_id.equals("199") || row.location_id.equals("200") || row.location_id.equals("201")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_gangjin(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("180")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_goheung(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("181")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_gokseung(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("182")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_gwangyang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("183")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_gurae(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("184")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_naju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("185")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_damyang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("186")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_mokpo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("187")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_muan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("188")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_boseung(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("189")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_suncheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("190")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_sinan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("191")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_yeosu(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("192")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_yeonggwang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("193")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_yeongam(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("194")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_wando(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("195")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_jangseung(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("196")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_jangheung(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("197")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_jindo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("198")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_hampyeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("199")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_haenam(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("200")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeonnam_hwasun(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("179") || row.location_id.equals("201")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void gyeongbukAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("75") || row.location_id.equals("76") || row.location_id.equals("77")
                    || row.location_id.equals("78") || row.location_id.equals("79") || row.location_id.equals("80") || row.location_id.equals("81")
                    || row.location_id.equals("82") || row.location_id.equals("83") || row.location_id.equals("84") || row.location_id.equals("85")
                    || row.location_id.equals("86") || row.location_id.equals("87") || row.location_id.equals("88") || row.location_id.equals("89")
                    || row.location_id.equals("90") || row.location_id.equals("91") || row.location_id.equals("92") || row.location_id.equals("93")
                    || row.location_id.equals("94") || row.location_id.equals("95") || row.location_id.equals("96") || row.location_id.equals("97")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_gyeongsan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("75")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_gyeongju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("76")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_goryeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("77")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_gumi(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("78")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_gunwi(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("79")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_gimcheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("80")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_mungyeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("81")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_bonghwa(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("82")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_sangju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("83")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_seungju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("84")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_andong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("85")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_yeongdeok(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("86")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_yeongyang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("87")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_yeongju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("88")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_yeongcheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("89")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_yaecheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("90")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_ulleung(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("91")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_uljin(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("92")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_jusoen(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("93")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_chungdo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("94")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_chungsong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("95")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_chilgok(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("96")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongbuk_pohang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("74") || row.location_id.equals("97")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void gyeongnamAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("54") || row.location_id.equals("55") || row.location_id.equals("56")
                    || row.location_id.equals("57") || row.location_id.equals("58") || row.location_id.equals("60") || row.location_id.equals("61")
                    || row.location_id.equals("62") || row.location_id.equals("63") || row.location_id.equals("64") || row.location_id.equals("65")
                    || row.location_id.equals("67") || row.location_id.equals("68") || row.location_id.equals("69") || row.location_id.equals("70")
                    || row.location_id.equals("71") || row.location_id.equals("72") || row.location_id.equals("73")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_geoje(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("54")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_geochang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("55")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_goseong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("56")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_gimhae(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("57")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_namhae(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("58")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_milyang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("60")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_sacheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("61")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_sancheong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("62")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_yangsan(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("63")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_uiryeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("64")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_jinju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("65")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_changnyeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("67")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_changwon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("68")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_tongyeong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("69")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_hadong(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("70")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_haman(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("71")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_hamyang(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("72")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void gyeongnam_habcheon(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("53") || row.location_id.equals("73")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*??????*/
    public void jejuAll(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("217") || row.location_id.equals("220") || row.location_id.equals("221")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeju_seogwipo(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("217") || row.location_id.equals("220")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
    public void jeju_jeju(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){
            row = disasterMsg.DisasterMsg.row.get(i);

            if(row.location_id.equals("217") || row.location_id.equals("221")){
                disasterAdapter.addItem(row);
            }
        }
        disasterAdapter.notifyDataSetChanged();
    }
}
