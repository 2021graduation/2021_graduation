package org.techtown.graduation_project;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Infection extends AppCompatActivity {

    TextView text;
    String data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infection);

        text= (TextView)findViewById(R.id.result_mask);
        new Thread(new Runnable() {
            @Override
            public void run() {
                data = getinfection();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(data);
                    }
                });
            }
        }).start();
    }

    String getinfection(){
        StringBuffer buffer=new StringBuffer();

        String queryUrl="http://openapi.data.go.kr/openapi/service/rest/Covid19/getCovid19InfStateJson?serviceKey=RFGquavKPUrcCE%2BLmZyFZ02tx6tq7lgkoevDFqgSuH%2FrZMZsdI8akZUk5Qe7tO%2FDFrAV%2FhJbr1ABTxX%2BgnBZmA%3D%3D&pageNo=1&numOfRows=10&startCreateDt=20210225&endCreateDt=20210225";
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
                        else if(tag.equals("createDt")){
                            buffer.append("현 시각: ");
                            xpp.next();
                            buffer.append(xpp.getText());//title 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        else if(tag.equals("decideCnt")){
                            buffer.append("확진자 수:");
                            xpp.next();
                            buffer.append(xpp.getText());//description 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("deathCnt")){
                            buffer.append("사망자 수:");
                            xpp.next();
                            buffer.append(xpp.getText());//address 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n");//줄바꿈 문자 추가
                        }
                        else if(tag.equals("clearCnt")){
                            buffer.append("격리해제 수:");
                            xpp.next();
                            buffer.append(xpp.getText());//mapx 요소의 TEXT 읽어와서 문자열버퍼에 추가
                            buffer.append("\n"); //줄바꿈 문자 추가
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기

                        if(tag.equals("item")) buffer.append("\n");// 첫번째 검색결과종료..줄바꿈
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
