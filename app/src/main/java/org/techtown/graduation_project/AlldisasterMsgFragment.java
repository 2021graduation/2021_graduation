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

                            if (adspin1.getItem(i).equals("전체")) {
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
                            } else if (adspin1.getItem(i).equals("서울")) {
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
                                                case "전체":
                                                    seoulAll(response);
                                                    break;
                                                case "강남구":
                                                    seoul_gangnam(response);
                                                    break;
                                                case "강동구":
                                                    seoul_gangdong(response);
                                                    break;
                                                case "강북구":
                                                    seoul_gangbuk(response);
                                                    break;
                                                case "강서구":
                                                    seoul_gangseo(response);
                                                    break;
                                                case "관악구":
                                                    seoul_gwanak(response);
                                                    break;
                                                case "광진구":
                                                    seoul_gwangin(response);
                                                    break;
                                                case "구로구":
                                                    seoul_guro(response);
                                                    break;
                                                case "금천구":
                                                    seoul_geumcheon(response);
                                                    break;
                                                case "노원구":
                                                    seoul_nowon(response);
                                                    break;
                                                case "도봉구":
                                                    seoul_dobong(response);
                                                    break;
                                                case "동대문구":
                                                    seoul_dongdaemun(response);
                                                    break;
                                                case "동작구":
                                                    seoul_dongjak(response);
                                                    break;
                                                case "마포구":
                                                    seoul_mapo(response);
                                                    break;
                                                case "서대문구":
                                                    seoul_seodaemun(response);
                                                    break;
                                                case "서초구":
                                                    seoul_seocho(response);
                                                    break;
                                                case "성동구":
                                                    seoul_seongdong(response);
                                                    break;
                                                case "성북구":
                                                    seoul_seongbuk(response);
                                                    break;
                                                case "송파구":
                                                    seoul_songpa(response);
                                                    break;
                                                case "양천구":
                                                    seoul_yangcheon(response);
                                                    break;
                                                case "영등포구":
                                                    seoul_yeongdeungpo(response);
                                                    break;
                                                case "용산구":
                                                    seoul_yongsan(response);
                                                    break;
                                                case "은평구":
                                                    seoul_eunpyeong(response);
                                                    break;
                                                case "종로구":
                                                    seoul_jongro(response);
                                                    break;
                                                case "중구":
                                                    seoul_junggu(response);
                                                    break;
                                                case "중랑구":
                                                    seoul_jungrang(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("부산")) {
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
                                                case "전체":
                                                    busanAll(response);
                                                    break;
                                                case "강서구":
                                                    busan_gangseo(response);
                                                    break;
                                                case "금정구":
                                                    busan_geumjeong(response);
                                                    break;
                                                case "기장군":
                                                    busan_gijang(response);
                                                    break;
                                                case "남구":
                                                    busan_namgu(response);
                                                    break;
                                                case "동구":
                                                    busan_donggu(response);
                                                    break;
                                                case "동래구":
                                                    busan_dongrae(response);
                                                    break;
                                                case "부산진구":
                                                    busan_busanjin(response);
                                                    break;
                                                case "북구":
                                                    busan_bukgu(response);
                                                    break;
                                                case "사상구":
                                                    busan_sasang(response);
                                                    break;
                                                case "사하구":
                                                    busan_saha(response);
                                                    break;
                                                case "서구":
                                                    busan_seogu(response);
                                                    break;
                                                case "수영구":
                                                    busan_suyeong(response);
                                                    break;
                                                case "연제구":
                                                    busan_yeonjae(response);
                                                    break;
                                                case "영도구":
                                                    busan_yeongdo(response);
                                                    break;
                                                case "중구":
                                                    busan_junggu(response);
                                                    break;
                                                case "해운대구":
                                                    busan_haeeundae(response);
                                                    break;
                                            }

                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("대구")) {
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
                                                case "전체":
                                                    daeguAll(response);
                                                    break;
                                                case "남구":
                                                    daegu_namgu(response);
                                                    break;
                                                case "달서구":
                                                    daegu_dalseo(response);
                                                    break;
                                                case "달성군":
                                                    daegu_dalseong(response);
                                                    break;
                                                case "동구":
                                                    daegu_donggu(response);
                                                    break;
                                                case "북구":
                                                    daegu_bukgu(response);
                                                    break;
                                                case "서구":
                                                    daegu_seogu(response);
                                                    break;
                                                case "수성구":
                                                    daegu_susung(response);
                                                    break;
                                                case "중구":
                                                    daegu_junggu(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("인천")) {
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
                                                case "전체":
                                                    incheonAll(response);
                                                    break;
                                                case "강화군":
                                                    incheon_ganghwa(response);
                                                    break;
                                                case "계양구":
                                                    incheon_gaeyang(response);
                                                    break;
                                                case "미추홀구":
                                                    incheon_michuhol(response);
                                                    break;
                                                case "남동구":
                                                    incheon_namdong(response);
                                                    break;
                                                case "동구":
                                                    incheon_donggu(response);
                                                    break;
                                                case "부평구":
                                                    incheon_bupyeong(response);
                                                    break;
                                                case "서구":
                                                    incheon_seogu(response);
                                                    break;
                                                case "연수구":
                                                    incheon_yeonsu(response);
                                                    break;
                                                case "옹진군":
                                                    incheon_ongjin(response);
                                                    break;
                                                case "중구":
                                                    incheon_junggu(response);
                                                    break;
                                            }
                                        });

                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("광주")) {
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
                                                case "전체":
                                                    gwangjuAll(response);
                                                    break;
                                                case "광산구":
                                                    gwangju_gwangsan(response);
                                                    break;
                                                case "남구":
                                                    gwangju_namgu(response);
                                                    break;
                                                case "동구":
                                                    gwangju_donggu(response);
                                                    break;
                                                case "북구":
                                                    gwangju_bukgu(response);
                                                    break;
                                                case "서구":
                                                    gwangju_seogu(response);
                                                    break;
                                            }

                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("대전")) {
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
                                                case "전체":
                                                    daejeonAll(response);
                                                    break;
                                                case "대덕구":
                                                    daejeon_daedeok(response);
                                                    break;
                                                case "동구":
                                                    daejeon_donggu(response);
                                                    break;
                                                case "서구":
                                                    daejeon_seogu(response);
                                                    break;
                                                case "유성구":
                                                    daejeon_yuseong(response);
                                                    break;
                                                case "중구":
                                                    daejeon_junggu(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("울산")) {
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
                                                case "전체":
                                                    ulsannAll(response);
                                                    break;
                                                case "남구":
                                                    ulsan_namgu(response);
                                                    break;
                                                case "동구":
                                                    ulsan_donggu(response);
                                                    break;
                                                case "북구":
                                                    ulsan_bukgu(response);
                                                    break;
                                                case "울주군":
                                                    ulsan_ulju(response);
                                                    break;
                                                case "중구":
                                                    ulsan_junggu(response);
                                                    break;
                                            }

                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("세종")) {
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
                            } else if (adspin1.getItem(i).equals("경기")) {
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
                                                case "전체":
                                                    gyeonggiAll(response);
                                                    break;
                                                case "가평군":
                                                    gyeonggi_gapyeong(response);
                                                    break;
                                                case "고양시":
                                                    gyeonggi_goyang(response);
                                                    break;
                                                case "과천시":
                                                    gyeonggi_gwacheon(response);
                                                    break;
                                                case "광명시":
                                                    gyeonggi_gwangmyeong(response);
                                                    break;
                                                case "광주시":
                                                    gyeonggi_gwangju(response);
                                                    break;
                                                case "구리시":
                                                    gyeonggi_guri(response);
                                                    break;
                                                case "군포시":
                                                    gyeonggi_gunpo(response);
                                                    break;
                                                case "김포시":
                                                    gyeonggi_gimpo(response);
                                                    break;
                                                case "남양주시":
                                                    gyeonggi_namyangju(response);
                                                    break;
                                                case "동두천시":
                                                    gyeonggi_dongducheon(response);
                                                    break;
                                                case "부천시":
                                                    gyeonggi_bucheon(response);
                                                    break;
                                                case "성남시":
                                                    gyeonggi_seongnam(response);
                                                    break;
                                                case "수원시":
                                                    gyeonggi_suwon(response);
                                                    break;
                                                case "시흥시":
                                                    gyeonggi_siheung(response);
                                                    break;
                                                case "안산시":
                                                    gyeonggi_ansan(response);
                                                    break;
                                                case "안성시":
                                                    gyeonggi_anseong(response);
                                                    break;
                                                case "안양시":
                                                    gyeonggi_anyang(response);
                                                    break;
                                                case "양주시":
                                                    gyeonggi_yangju(response);
                                                    break;
                                                case "양평군":
                                                    gyeonggi_yangpyeong(response);
                                                    break;
                                                case "여주시":
                                                    gyeonggi_yeoju(response);
                                                    break;
                                                case "연천군":
                                                    gyeonggi_yeoncheon(response);
                                                    break;
                                                case "오산시":
                                                    gyeonggi_osan(response);
                                                    break;
                                                case "용인시":
                                                    gyeonggi_yongin(response);
                                                    break;
                                                case "의왕시":
                                                    gyeonggi_uiwang(response);
                                                    break;
                                                case "의정부시":
                                                    gyeonggi_uijeongbu(response);
                                                    break;
                                                case "이천시":
                                                    gyeonggi_icheon(response);
                                                    break;
                                                case "파주시":
                                                    gyeonggi_paju(response);
                                                    break;
                                                case "평택시":
                                                    gyeonggi_pyeongtaek(response);
                                                    break;
                                                case "포천시":
                                                    gyeonggi_pocheon(response);
                                                    break;
                                                case "하남시":
                                                    gyeonggi_hanam(response);
                                                    break;
                                                case "화성시":
                                                    gyeonggi_hwaseong(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("강원")) {
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
                                                case "전체":
                                                    gangwonAll(response);
                                                    break;
                                                case "강릉시":
                                                    gangwon_gangneung(response);
                                                    break;
                                                case "고성군":
                                                    gangwon_gosung(response);
                                                    break;
                                                case "동해시":
                                                    gangwon_donghae(response);
                                                    break;
                                                case "삼척시":
                                                    gangwon_samcheok(response);
                                                    break;
                                                case "속초시":
                                                    gangwon_sokcho(response);
                                                    break;
                                                case "양구군":
                                                    gangwon_yanggu(response);
                                                    break;
                                                case "양양군":
                                                    gangwon_yangyang(response);
                                                    break;
                                                case "영월군":
                                                    gangwon_yeongwol(response);
                                                    break;
                                                case "원주시":
                                                    gangwon_wonju(response);
                                                    break;
                                                case "인제군":
                                                    gangwon_inje(response);
                                                    break;
                                                case "정선군":
                                                    gangwon_jeongsun(response);
                                                    break;
                                                case "철원군":
                                                    gangwon_cheorwon(response);
                                                    break;
                                                case "춘천시":
                                                    gangwon_chuncheon(response);
                                                    break;
                                                case "태백시":
                                                    gangwon_taebaek(response);
                                                    break;
                                                case "평창군":
                                                    gangwon_pyeongchang(response);
                                                    break;
                                                case "홍천군":
                                                    gangwon_hongcheon(response);
                                                    break;
                                                case "화천군":
                                                    gangwon_hwacheon(response);
                                                    break;
                                                case "횡성군":
                                                    gangwon_hoengseong(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("충북")) {
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
                                                case "전체":
                                                    chungbukAll(response);
                                                    break;
                                                case "괴산군":
                                                    chungbuk_goesan(response);
                                                    break;
                                                case "단양군":
                                                    chungbuk_danyang(response);
                                                    break;
                                                case "보은군":
                                                    chungbuk_boeun(response);
                                                    break;
                                                case "영동군":
                                                    chungbuk_yeongdong(response);
                                                    break;
                                                case "옥천군":
                                                    chungbuk_okcheon(response);
                                                    break;
                                                case "음성군":
                                                    chungbuk_eumseong(response);
                                                    break;
                                                case "제천시":
                                                    chungbuk_jaecheon(response);
                                                    break;
                                                case "증평군":
                                                    chungbuk_jeungpyeong(response);
                                                    break;
                                                case "진천시":
                                                    chungbuk_jincheon(response);
                                                    break;
                                                case "청주시":
                                                    chungbuk_cheongju(response);
                                                    break;
                                                case "충주시":
                                                    chungbuk_chungju(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("충남")) {
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
                                                case "전체":
                                                    chungnamAll(response);
                                                    break;
                                                case "계룡시":
                                                    chungnam_gyeryong(response);
                                                    break;
                                                case "공주시":
                                                    chungnam_gongju(response);
                                                    break;
                                                case "금산군":
                                                    chungnam_geumsan(response);
                                                    break;
                                                case "논산시":
                                                    chungnam_nonsan(response);
                                                    break;
                                                case "당진시":
                                                    chungnam_dangjin(response);
                                                    break;
                                                case "보령시":
                                                    chungnam_boryeong(response);
                                                    break;
                                                case "부여군":
                                                    chungnam_buyeo(response);
                                                    break;
                                                case "서산시":
                                                    chungnam_seosan(response);
                                                    break;
                                                case "서천군":
                                                    chungnam_seocheon(response);
                                                    break;
                                                case "아산시":
                                                    chungnam_asan(response);
                                                    break;
                                                case "예산군":
                                                    chungnam_yaesan(response);
                                                    break;
                                                case "천안시":
                                                    chungnam_cheonan(response);
                                                    break;
                                                case "청양군":
                                                    chungnam_chungyang(response);
                                                    break;
                                                case "태안군":
                                                    chungnam_taean(response);
                                                    break;
                                                case "홍성군":
                                                    chungnam_hongseong(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("전북")) {
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
                                                case "전체":
                                                    jeonbukAll(response);
                                                    break;
                                                case "고창군":
                                                    jeonbuk_gochang(response);
                                                    break;
                                                case "군산시":
                                                    jeonbuk_gunsan(response);
                                                    break;
                                                case "김제시":
                                                    jeonbuk_gimjae(response);
                                                    break;
                                                case "남원시":
                                                    jeonbuk_namwon(response);
                                                    break;
                                                case "무주군":
                                                    jeonbuk_muju(response);
                                                    break;
                                                case "부안군":
                                                    jeonbuk_buan(response);
                                                    break;
                                                case "순창군":
                                                    jeonbuk_sunchang(response);
                                                    break;
                                                case "완주군":
                                                    jeonbuk_wanju(response);
                                                    break;
                                                case "익산시":
                                                    jeonbuk_iksan(response);
                                                    break;
                                                case "임실군":
                                                    jeonbuk_imsil(response);
                                                    break;
                                                case "장수군":
                                                    jeonbuk_jangsu(response);
                                                    break;
                                                case "전주시":
                                                    jeonbuk_jeonju(response);
                                                    break;
                                                case "정읍시":
                                                    jeonbuk_jeongeup(response);
                                                    break;
                                                case "진안군":
                                                    jeonbuk_jinan(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("전남")) {
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
                                                case "전체":
                                                    jeonnamAll(response);
                                                    break;
                                                case "강진군":
                                                    jeonnam_gangjin(response);
                                                    break;
                                                case "고흥군":
                                                    jeonnam_goheung(response);
                                                    break;
                                                case "곡성군":
                                                    jeonnam_gokseung(response);
                                                    break;
                                                case "광양시":
                                                    jeonnam_gwangyang(response);
                                                    break;
                                                case "구례군":
                                                    jeonnam_gurae(response);
                                                    break;
                                                case "나주시":
                                                    jeonnam_naju(response);
                                                    break;
                                                case "담양군":
                                                    jeonnam_damyang(response);
                                                    break;
                                                case "목포시":
                                                    jeonnam_mokpo(response);
                                                    break;
                                                case "무안군":
                                                    jeonnam_muan(response);
                                                    break;
                                                case "보성군":
                                                    jeonnam_boseung(response);
                                                    break;
                                                case "순천시":
                                                    jeonnam_suncheon(response);
                                                    break;
                                                case "신안군":
                                                    jeonnam_sinan(response);
                                                    break;
                                                case "여수시":
                                                    jeonnam_yeosu(response);
                                                    break;
                                                case "영광군":
                                                    jeonnam_yeonggwang(response);
                                                    break;
                                                case "영암군":
                                                    jeonnam_yeongam(response);
                                                    break;
                                                case "완도군":
                                                    jeonnam_wando(response);
                                                    break;
                                                case "장성군":
                                                    jeonnam_jangseung(response);
                                                    break;
                                                case "장흥군":
                                                    jeonnam_jangheung(response);
                                                    break;
                                                case "진도군":
                                                    jeonnam_jindo(response);
                                                    break;
                                                case "함평군":
                                                    jeonnam_hampyeong(response);
                                                    break;
                                                case "해남군":
                                                    jeonnam_haenam(response);
                                                    break;
                                                case "화순군":
                                                    jeonnam_hwasun(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("경북")) {
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
                                                case "전체":
                                                    gyeongbukAll(response);
                                                    break;
                                                case "경산시":
                                                    gyeongbuk_gyeongsan(response);
                                                    break;
                                                case "경주시":
                                                    gyeongbuk_gyeongju(response);
                                                    break;
                                                case "고령군":
                                                    gyeongbuk_goryeong(response);
                                                    break;
                                                case "구미시":
                                                    gyeongbuk_gumi(response);
                                                    break;
                                                case "군위군":
                                                    gyeongbuk_gunwi(response);
                                                    break;
                                                case "김천시":
                                                    gyeongbuk_gimcheon(response);
                                                    break;
                                                case "문경시":
                                                    gyeongbuk_mungyeong(response);
                                                    break;
                                                case "봉화군":
                                                    gyeongbuk_bonghwa(response);
                                                    break;
                                                case "상주시":
                                                    gyeongbuk_sangju(response);
                                                    break;
                                                case "성주군":
                                                    gyeongbuk_seungju(response);
                                                    break;
                                                case "안동시":
                                                    gyeongbuk_andong(response);
                                                    break;
                                                case "영덕군":
                                                    gyeongbuk_yeongdeok(response);
                                                    break;
                                                case "영양군":
                                                    gyeongbuk_yeongyang(response);
                                                    break;
                                                case "영주시":
                                                    gyeongbuk_yeongju(response);
                                                    break;
                                                case "영천시":
                                                    gyeongbuk_yeongcheon(response);
                                                    break;
                                                case "예천군":
                                                    gyeongbuk_yaecheon(response);
                                                    break;
                                                case "울릉군":
                                                    gyeongbuk_ulleung(response);
                                                    break;
                                                case "울진군":
                                                    gyeongbuk_uljin(response);
                                                    break;
                                                case "의성군":
                                                    gyeongbuk_jusoen(response);
                                                    break;
                                                case "청도군":
                                                    gyeongbuk_chungdo(response);
                                                    break;
                                                case "청송군":
                                                    gyeongbuk_chungsong(response);
                                                    break;
                                                case "칠곡군":
                                                    gyeongbuk_chilgok(response);
                                                    break;
                                                case "포항시":
                                                    gyeongbuk_pohang(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("경남")) {
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
                                                case "전체":
                                                    gyeongnamAll(response);
                                                    break;
                                                case "거제시":
                                                    gyeongnam_geoje(response);
                                                    break;
                                                case "거창군":
                                                    gyeongnam_geochang(response);
                                                    break;
                                                case "고성군":
                                                    gyeongnam_goseong(response);
                                                    break;
                                                case "김해시":
                                                    gyeongnam_gimhae(response);
                                                    break;
                                                case "남해군":
                                                    gyeongnam_namhae(response);
                                                    break;
                                                case "밀양시":
                                                    gyeongnam_milyang(response);
                                                    break;
                                                case "사천시":
                                                    gyeongnam_sacheon(response);
                                                    break;
                                                case "산청군":
                                                    gyeongnam_sancheong(response);
                                                    break;
                                                case "양산시":
                                                    gyeongnam_yangsan(response);
                                                    break;
                                                case "의령군":
                                                    gyeongnam_uiryeong(response);
                                                    break;
                                                case "진주시":
                                                    gyeongnam_jinju(response);
                                                    break;
                                                case "창녕군":
                                                    gyeongnam_changnyeong(response);
                                                    break;
                                                case "창원시":
                                                    gyeongnam_changwon(response);
                                                    break;
                                                case "통영시":
                                                    gyeongnam_tongyeong(response);
                                                    break;
                                                case "하동군":
                                                    gyeongnam_hadong(response);
                                                    break;
                                                case "함안군":
                                                    gyeongnam_haman(response);
                                                    break;
                                                case "함양군":
                                                    gyeongnam_hamyang(response);
                                                    break;
                                                case "합천군":
                                                    gyeongnam_habcheon(response);
                                                    break;
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });
                            } else if (adspin1.getItem(i).equals("제주")) {
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
                                                case "전체":
                                                    jejuAll(response);
                                                    break;
                                                case "서귀포시":
                                                    jeju_seogwipo(response);
                                                    break;
                                                case "제주시":
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

    // 전체 재난문자 호출
    public void All(String response) {

        Gson gson = new Gson();
        DisasterMsg disasterMsg = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterMsg.DisasterMsg.row.size(); i++){

            row = disasterMsg.DisasterMsg.row.get(i);
            disasterAdapter.addItem(row);
        }
        disasterAdapter.notifyDataSetChanged();
    }
    /*서울*/
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
    /*부산*/
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
    /*대구*/
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
    /*인천*/
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
    /*광주*/
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
    /*대전*/
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
    /*울산*/
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
    /*세종*/
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
    /*경기*/
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
    /*강원*/
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
    /*충북*/
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
    /*충남*/
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
    /*전북*/
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
    /*전남*/
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
    /*경북*/
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
    /*경남*/
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
    /*제주*/
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
