package org.techtown.sqliteexample2;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ListDataActivity extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    private ArrayList<Data> Data;
    private DataAdapter dataAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    int idnum;
    String sData;
    DatabaseHelper mDatabaseHelper;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Data = new ArrayList<>();

        dataAdapter = new DataAdapter(Data);
        recyclerView.setAdapter(dataAdapter);

        populateListView();
    }

//    public void mOnClick(View v) {
//
//        maskadapter.ClearMaskList();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                getXmlMask();
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        maskadapter.notifyDataSetChanged();
//                    }
//                });
//            }
//        }).start();
//    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the View");

        Cursor data = mDatabaseHelper.getData();
        Data = new ArrayList<>();
        while (data.moveToNext()) {
            idnum = data.getInt(0);
            sData = data.getString(1);
            Data data1 = new Data(idnum, sData);
            Data.add(data1);
        }

//        recyclerView.setOnClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            }
//        });
        //set an onItemClickListener to the ListView
//        recyclerView.setOnClickListener( {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                String name = adapterView.getItemAtPosition(i).toString();
//                Log.d(TAG, "onItemClick: You Clicked on " + name);
//
//                Cursor data = mDatabaseHelper.getItemID(name); //get the id associated with that name
//                int itemID = -1;
//                while(data.moveToNext()){
//                    itemID = data.getInt(0);
//                }
//                if(itemID > -1){
//                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
//                    AlertDialog.Builder ad = new AlertDialog.Builder(ListDataActivity.this);
//                    ad.setIcon(R.mipmap.ic_launcher_round);
//                    ad.setTitle("제목");
//                    ad.setMessage("삭제하시겠습니까?");
//
//                    final EditText et = new EditText(ListDataActivity.this);
//                    ad.setView(et);
//
//                    int finalItemID = itemID;
//                    ad.setPositiveButton("예", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            mDatabaseHelper.deleteName(finalItemID);
//                            dialog.dismiss();
//                        }
//                    });
//
//                    ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    });
//
//                    ad.show();
//                }
//                else{
//                    toastMessage("No ID associated with that name");
//                }
//            }
//        });
//    }

//    private void toastMessage(String message){
//        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
//    }
    }
}
