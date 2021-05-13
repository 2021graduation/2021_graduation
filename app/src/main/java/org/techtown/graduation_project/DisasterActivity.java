package org.techtown.graduation_project;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class DisasterActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    FragmentTransaction tran;
    AlldisasterMsgFragment alldisasterMsgFragment;
    ContactMsgFragment contactMsgFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disastermsg);

        fragmentManager = getSupportFragmentManager();

        alldisasterMsgFragment = new AlldisasterMsgFragment();
        contactMsgFragment = new ContactMsgFragment();

        tran = fragmentManager.beginTransaction();
        tran.replace(R.id.disaster_frame, contactMsgFragment).commitAllowingStateLoss();

        Button All_button = findViewById(R.id.All_button);
        All_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tran = getSupportFragmentManager().beginTransaction();
                alldisasterMsgFragment = new AlldisasterMsgFragment();
                tran.replace(R.id.disaster_frame, alldisasterMsgFragment).commitAllowingStateLoss();
            }
        });

        Button ContactMsg_button = findViewById(R.id.ContactMsg_button);
        ContactMsg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tran = getSupportFragmentManager().beginTransaction();
                contactMsgFragment = new ContactMsgFragment();
                tran.replace(R.id.disaster_frame, contactMsgFragment).commitAllowingStateLoss();
            }
        });
    }
}
