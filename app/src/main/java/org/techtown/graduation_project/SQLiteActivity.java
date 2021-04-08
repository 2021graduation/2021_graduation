package org.techtown.graduation_project;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;

public class SQLiteActivity extends AppCompatActivity {
    private static final String TAG = "SQLiteActivity";

    private ArrayList<Table> Table;
    private TableAdapter TableAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    String message;

    String sData;
    DatabaseHelper mDatabaseHelper;
    SwipeRefreshLayout refreshLayout;


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sqlite_tablename);

        /////////////////// 새로고침하는 부분 ///////////////////
        refreshLayout = findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                TableAdapter.Clearmlist();
                populateListView();
                TableAdapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        });
        //////////////////////////////////////////////////////
        recyclerView = (RecyclerView) findViewById(R.id.rv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        Table = new ArrayList<>();
        mDatabaseHelper = new DatabaseHelper(this);


        TableAdapter = new TableAdapter(Table);
        recyclerView.setAdapter(TableAdapter);

        TableAdapter.setOnItemClickListener(new TableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, TextView sData) {
                Log.d(TAG, "클릭됨: " + sData.getText().toString());
                String date = sData.getText().toString();
                Intent intent = new Intent(SQLiteActivity.this, MapActivity.class);
                intent.putExtra("table_name",date);
                startActivity(intent);
            }
        });

        TableAdapter.setOnItemLongClickListener(new TableAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View v, int pos, TextView sData) {
                String row_data = sData.getText().toString();
                ////////////// 삭제 기능 ///////////////////
                Log.d(TAG, "현재시각: " + mDatabaseHelper.getDate());
                Log.d(TAG, "onItemClick: You Clicked on " + sData.getText());
                AlertDialog.Builder ad = new AlertDialog.Builder(SQLiteActivity.this);
                ad.setIcon(R.mipmap.ic_launcher_round);
                ad.setTitle("제목");
                ad.setMessage("삭제하시겠습니까?");

                final EditText et = new EditText(SQLiteActivity.this);
                ad.setView(et);

                ad.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mDatabaseHelper.deleteName(row_data);
                        dialog.dismiss();
                        TableAdapter.notifyDataSetChanged();
                        TableAdapter.Clearmlist();
                        populateListView();
                    }
                });

                ad.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                ad.show();
                ///////////////////////////////////////////////////////
            }
        });

        populateListView();
    }


    // db 가져오는 부분
    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the View");

        Cursor data = mDatabaseHelper.getTableName();
        while (data.moveToNext()) {
            sData = data.getString(0);
            if(sData.equals("android_metadata")){
                continue;
            }else if(sData.equals("sqlite_sequence")){
                continue;
            }
            Table data1 = new Table(sData);
            Table.add(data1);
        }
    }

    private void toastMessage (String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
