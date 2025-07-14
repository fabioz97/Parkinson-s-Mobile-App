package com.example.aplicaoparkinson;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Launcher extends AppCompatActivity {
    Button btnComerçar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        btnComerçar = findViewById(R.id.button);
        btnComerçar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Registar.class);
                startActivity(intent);
                finish();
            }
        });
    }
}