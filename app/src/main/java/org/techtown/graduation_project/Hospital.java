package org.techtown.graduation_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.ArrayList;

public class Hospital extends AppCompatActivity {
    private ArrayList<MainData> arrayList;
    private MainAdapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    String sgguNm;
    String sidoNm;
    String yadmNm;
    String telno;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        recyclerView = (RecyclerView)findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        arrayList = new ArrayList<>();
        mainAdapter = new MainAdapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                gethospital();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mainAdapter.notifyDataSetChanged(); // 새로고침
                    }
                });
            }
        }).start();

//        Button btn_add = (Button)findViewById(R.id.btn_add);
//        btn_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                MainData mainData = new MainData(R.mipmap.ic_launcher, "홍드로이드", "리사이클러뷰");
//                arrayList.add(mainData);
//                mainAdapter.notifyDataSetChanged(); // 새로고침
//            }
//        });

    }

    String gethospital(){
        StringBuffer buffer=new StringBuffer();

        String queryUrl="http://apis.data.go.kr/B551182/pubReliefHospService/getpubReliefHospList?serviceKey=RFGquavKPUrcCE%2BLmZyFZ02tx6tq7lgkoevDFqgSuH%2FrZMZsdI8akZUk5Qe7tO%2FDFrAV%2FhJbr1ABTxX%2BgnBZmA%3D%3D&pageNo=2&spclAdmTyCd=99";
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
                        buffer.append("파싱 시작...\n\n");
                        break;

                    case XmlPullParser.START_TAG:
                        tag= xpp.getName();//테그 이름 얻어오기

                        if(tag.equals("item")) ;// 첫번째 검색결과
                        else if(tag.equals("sgguNm")) {
                            xpp.next();
                            sgguNm = "시군: " + xpp.getText().toString();
                        }
                        else if(tag.equals("sidoNm")){
                            xpp.next();
                            sidoNm = "시도: " + xpp.getText().toString();
                        }
                        else if(tag.equals("yadmNm")){
                            xpp.next();
                            yadmNm = "병원이름: " + xpp.getText().toString();
                        }
                        else if(tag.equals("telno")){
                            xpp.next();
                            telno = "전화번호: " + xpp.getText().toString();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("item")){
                            MainData mainData = new MainData(R.mipmap.ic_launcher, sgguNm, sidoNm, yadmNm, telno);
                            arrayList.add(mainData);
                            buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
                        }

                        break;
                }

                eventType= xpp.next();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        buffer.append("파싱 끝\n");
        return buffer.toString();//StringBuffer 문자열 객체 반환

    }


}
