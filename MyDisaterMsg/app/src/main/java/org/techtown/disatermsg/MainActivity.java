
package org.techtown.disatermsg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Row> rowData;
    private DisaterAdapter disaterAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public Row row;

    static RequestQueue requestQueue;



    String key = "pPaSpIZ%2BXFweoQb0rmHH5gguuqHRO00DHw7CgOuW9wZ2c5HDm%2BwqWpv%2B29V9NIHAcggmnJz3ztzM8206Hkkw7A%3D%3D";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        rowData = new ArrayList<>();

        disaterAdapter = new DisaterAdapter(rowData);
        recyclerView.setAdapter(disaterAdapter);

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());

            sendRequest();

        }
    }
    public void sendRequest() {

        String url = "http://apis.data.go.kr/1741000/DisasterMsg3/getDisasterMsg1List?serviceKey="+key+"&numOfRows=1000";

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
        Digest digestList = gson.fromJson(xmlToJson.toJson().toString(), Digest.class);
        for(int i=0;i< digestList.DisasterMsg.row.size(); i++){
            row = digestList.DisasterMsg.row.get(i);

            if(row.location_name.equals("부산광역시 사하구") || row.location_name.equals("부산광역시 전체")){
                disaterAdapter.addItem(row);
            }
        }
        disaterAdapter.notifyDataSetChanged();
    }

    public void println(Object data, TextView textView) {
        textView.setText(data.toString());
    }

}