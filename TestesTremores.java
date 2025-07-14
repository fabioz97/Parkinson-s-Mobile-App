package com.example.aplicaoparkinson;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;


public class TestesTremores extends AppCompatActivity {
    Button Direita, Esquerda, MenuPrinicipal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testes_tremores);
        Direita = findViewById(R.id.btn_TesteMãoDireita);
        Esquerda = findViewById(R.id.btn_TesteMãoEsquerda);
        MenuPrinicipal = findViewById(R.id.btn_Sair);

        Direita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TesteTremorMaoDireita.class);
                startActivity(intent);
                finish();
            }
        });
        Esquerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), TesteTremorMaoEsquerda.class);
                startActivity(intent);
                finish();
            }
        });
        MenuPrinicipal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}