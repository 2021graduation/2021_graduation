package org.techtown.graduation_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class MaskActivity extends AppCompatActivity {

    private ArrayList<MaskData> maskData;
    private MaskAdapter maskadapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    String ITEM_NAME;
    String ENTP_NAME;
    String ITEM_PERMIT_DATE;

    EditText editText; // 마스크 이름 검색

    String key = "pPaSpIZ%2BXFweoQb0rmHH5gguuqHRO00DHw7CgOuW9wZ2c5HDm%2BwqWpv%2B29V9NIHAcggmnJz3ztzM8206Hkkw7A%3D%3D";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_mask);

        recyclerView =(RecyclerView)findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        maskData = new ArrayList<>();

        maskadapter = new MaskAdapter(maskData);
        recyclerView.setAdapter(maskadapter);


        editText = (EditText) findViewById(R.id.editText);

    }

    public void mOnClick(View v){

        maskadapter.ClearMaskList(); // 초기화


        new Thread(new Runnable() {
            @Override
            public void run() {

                getXmlMask();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(maskadapter.getItemCount() == 0){
                            AlertDialog.Builder ad = new AlertDialog.Builder(MaskActivity.this);
                            ad.setIcon(R.mipmap.corona_round);
                            ad.setTitle("결과 없음");
                            ad.setMessage("다시 검색 하겠습니까?");
                            ad.setNegativeButton("예", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            ad.show();
                        }
                        maskadapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();
    }

    String getXmlMask(){
        StringBuffer buffer =new StringBuffer();

        String str = editText.getText().toString();
        String item_name = URLEncoder.encode(str);// 마스크 이름 검색



        String queryUrl="http://apis.data.go.kr/1471057/NonMdcinPrductPrmisnInfoService/getNonMdcinPrductPrmisnInfoList?ServiceKey="+key
                +"&class_no=32200&numOfRows=100&item_name="+item_name;
        try{
            URL url= new URL(queryUrl);//문자열로 된 요청 url을 URL 객체로 생성
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
                        else if(tag.equals("ITEM_NAME")){
                            xpp.next();
                            ITEM_NAME = "이름: " + xpp.getText().toString();
                        }
                        else if(tag.equals("ENTP_NAME")){
                            xpp.next();
                            ENTP_NAME = "업체명: " + xpp.getText().toString();
                        }
                        else if(tag.equals("ITEM_PERMIT_DATE")){
                            buffer.append("허가일: ");
                            xpp.next();
                            ITEM_PERMIT_DATE = "허가일: " + xpp.getText().toString();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        break;

                    case XmlPullParser.END_TAG:
                        tag= xpp.getName(); //테그 이름 얻어오기
                        if(tag.equals("item")) {
                            MaskData mask_Data = new MaskData(ITEM_NAME, ENTP_NAME, ITEM_PERMIT_DATE);
                            maskData.add(mask_Data);
                            buffer.append("\n");
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
