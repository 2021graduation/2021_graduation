package org.techtown.graduation_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class Infection extends AppCompatActivity {

    FragmentTransaction tran;
    SidomapFragment sidomap;
    SidotableFragment sidotable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infection);
        Button map_button = findViewById(R.id.map_button);
        Button table_button = findViewById(R.id.disaster);
        map_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tran = getSupportFragmentManager().beginTransaction();
                sidomap = new SidomapFragment();
                tran.replace(R.id.frame, sidomap);
                tran.commit();
            }
        });
        table_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tran = getSupportFragmentManager().beginTransaction();
                sidotable = new SidotableFragment();
                tran.replace(R.id.frame, sidotable);
                tran.commit();
            }
        });
    }
}