package com.example.aplicaoparkinson;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class Questionarios extends AppCompatActivity {
    Button questionario_matinal,questionario_tarde,questionario_noite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questionarios);

        questionario_matinal = findViewById(R.id.btn_questionário_matinal);
        questionario_tarde = findViewById(R.id.btn_questionário_tarde);
        questionario_noite = findViewById(R.id.btn_questionário_noite);
        questionario_matinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuestionarioMatinal.class);
                startActivity(intent);
                finish();
            }
        });
        questionario_tarde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuestionarioTarde.class);
                startActivity(intent);
                finish();
            }
        });
        questionario_noite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), QuestionarioNoite.class);
                startActivity(intent);
                finish();
            }
        });
    }
}