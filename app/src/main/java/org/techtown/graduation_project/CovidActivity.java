package org.techtown.graduation_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CovidActivity extends AppCompatActivity {

    TextView text;

    String key="RFGquavKPUrcCE%2BLmZyFZ02tx6tq7lgkoevDFqgSuH%2FrZMZsdI8akZUk5Qe7tO%2FDFrAV%2FhJbr1ABTxX%2BgnBZmA%3D%3D";

    Button hospital;
    Button infection;
    Button mask;
    Button pharmacy;


    String data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid);

        text= (TextView)findViewById(R.id.result_mask);
        hospital = findViewById(R.id.hospital);
        infection = findViewById(R.id.infection);
        mask = findViewById(R.id.mask);
        pharmacy = findViewById(R.id.pharmacy);


        hospital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(), Hospital.class);
                startActivity(intent);
            }
        });

        infection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent infection_intent = new Intent(getApplicationContext(), Infection.class);
                startActivity(infection_intent);
            }
        });

        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mask_intent = new Intent(getApplicationContext(), Mask.class);
                startActivity(mask_intent);
            }
        });

        pharmacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pharmacy_intent = new Intent(getApplicationContext(), Pharmacy.class);
                startActivity(pharmacy_intent);
            }
        });
    }
}