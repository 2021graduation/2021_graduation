package org.techtown.graduation_project;

import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ContactMsgFragment extends Fragment {



    private ArrayList<DisasterRowData> rowData;
    private DisasterAdapter disasterAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    public DisasterRowData row;

    static RequestQueue requestQueue;
    View view;

    SigunguDatabaseHelper sigunguDatabaseHelper;

    public ContactMsgFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        view = inflater.inflate(R.layout.fragment_mydisastermsg, container, false);

        recyclerView = view.findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);


        recyclerView.setLayoutManager(linearLayoutManager);

        rowData = new ArrayList<>();

        disasterAdapter = new DisasterAdapter(rowData);
        recyclerView.setAdapter(disasterAdapter);

        if (requestQueue == null){
            requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        }
        sendRequest();
        sigunguDatabaseHelper = new SigunguDatabaseHelper(view.getContext());
        return view;
    }


    public void sendRequest() {
        String url = "https://apixml-5d25d-default-rtdb.firebaseio.com/Msg.json";

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
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT);
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
        sigunguDatabaseHelper.SigungudMsg_dropTable();
        List<List<Address>> list = null;

        Gson gson = new Gson();
        DisasterMsg disasterList = gson.fromJson(response, DisasterMsg.class);
        for(int i=0;i< disasterList.DisasterMsg.row.size(); i++){
            row = disasterList.DisasterMsg.row.get(i);
            // ???????????? ??????????????? ???????????? ?????? ????????? ????????????????????? ????????????.
            // ????????? ???????????????????????? ???????????? ???????????? ???????????? ???????????? ??????????????????.
            Cursor sigunguCursor = sigunguDatabaseHelper.getSigungu();
            while(sigunguCursor.moveToNext()) {
                if (row.getLocation_name().equals(sigunguCursor.getString(0) + " ??????") ||
                        row.getLocation_name().equals(sigunguCursor.getString(0) + " "
                                + sigunguCursor.getString(1))) {
                    //Log.d("????????? ???????????? ??????: ", sigunguCursor.getString(0) + " ??????" + sigunguCursor.getString(0) + " "
//                            + sigunguCursor.getString(1));
                    //Log.d("???????????? ?????????: ", row.getMsg());
                    if (sigunguDatabaseHelper.SigungudMsg_Check(row.getMsg()) == false){
                        // ???????????? ???????????? ?????? ?????????
                        // ???????????? ????????????
                        // ???????????? ?????? ????????? ????????? ????????????
                        sigunguDatabaseHelper.SigunguAddMsg(row.getMsg());
                        disasterAdapter.addItem(row);
                    }else{
                        // ???????????? ?????????
                        // ???????????? ?????? ?????? ????????????
                        continue;
                    }
                }
            }
            sigunguCursor.close();
        }
        disasterAdapter.notifyDataSetChanged();
    }
}
