package org.techtown.graduation_project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Infection extends AppCompatActivity {

    private ArrayList<InfectionData> infectionData;
    private InfectionAdapter infectionAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    String gubnu;
    String defCnt;
    String incDec;
    String localOccCnt;
    String deathCnt;
    String isolClearCnt;

    TextView createDt;
    String data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infection);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        infectionData = new ArrayList<>();
        infectionAdapter = new InfectionAdapter(infectionData);
        recyclerView.setAdapter(infectionAdapter);

        createDt = findViewById(R.id.createDt);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String day = sdf.format(calendar.getTime());

        createDt.setText(day + "\t00시 기준");

        new Thread(new Runnable() {
            @Override
            public void run() {

                getXmlData();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        infectionAdapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

    }

    String getXmlData()
    {
        StringBuffer buffer=new StringBuffer();

        String queryUrl="http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19SidoInfStateJson?serviceKey=pPaSpIZ%2BXFweoQb0rmHH5gguuqHRO00DHw7CgOuW9wZ2c5HDm%2BwqWpv%2B29V9NIHAcggmnJz3ztzM8206Hkkw7A%3D%3D";
        try{
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성.
            InputStream is= url.openStream(); //url위치로 입력스트림 연결

            XmlPullParserFactory factory= XmlPullParserFactory.newInstance();//xml파싱을 위한
            XmlPullParser xpp= factory.newPullParser();
            xpp.setInput( new InputStreamReader(is, "UTF-8") ); //inputstream 으로부터 xml 입력받기

            String tag;

            xpp.next();
            int eventType= xpp.getEventType();
            while( eventType != XmlPullParser.END_DOCUMENT ){
                switch( eventType ){
                    case XmlPullParser.START_DOCUMENT:
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기
                        if(tag.equals(("itme")));
                        else if(tag.equals("gubun")){
                            xpp.next();
                            gubnu = xpp.getText().toString();
                        }
                        else if(tag.equals("defCnt")){
                            xpp.next();
                            defCnt = xpp.getText().toString();

                        }
                        else if(tag.equals("incDec")){
                            xpp.next();
                            incDec = xpp.getText().toString();
                        }
                        else if(tag.equals("localOccCnt")){
                            xpp.next();
                            localOccCnt = xpp.getText().toString();
                        }
                        else if(tag.equals("deathCnt")){
                            xpp.next();
                            deathCnt = xpp.getText().toString();
                        }
                        else if(tag.equals("isolClearCnt")){
                            xpp.next();
                            isolClearCnt = xpp.getText().toString();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("item")){
                            InfectionData infectionDataa = new InfectionData(gubnu,defCnt,incDec,localOccCnt,deathCnt,isolClearCnt);
                            infectionData.add(infectionDataa);
                            buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        }
                        break;
                }

                eventType= xpp.next();
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return buffer.toString();//StringBuffer 문자열 객체 반환
    }
}