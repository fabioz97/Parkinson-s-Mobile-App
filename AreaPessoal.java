package com.example.aplicaoparkinson;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class AreaPessoal extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextNome, editTextIdade, editTextNivelParkinson, editTextNUtente;
    Button btnAtualizar, btnVoltar;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_pessoal);

        editTextEmail = findViewById(R.id.email);
        editTextNome = findViewById(R.id.Nome);
        editTextIdade = findViewById(R.id.Idade);
        editTextNUtente = findViewById(R.id.nutente);
        editTextNivelParkinson = findViewById(R.id.nivel_parkinson);
        btnAtualizar = findViewById(R.id.btn_atualizar);
        btnVoltar = findViewById(R.id.btn_voltar);
        progressBar = findViewById(R.id.progressbar);
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users");

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String nome = editTextNome.getText().toString().trim();
                String idadeStr = editTextIdade.getText().toString().trim();
                String nutenteStr = editTextNUtente.getText().toString().trim();
                String nivelParkinsonStr = editTextNivelParkinson.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(AreaPessoal.this, "Insira o seu Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nome)) {
                    Toast.makeText(AreaPessoal.this, "Insira o seu Nome", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(idadeStr)) {
                    Toast.makeText(AreaPessoal.this, "Insira a sua Idade", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nutenteStr)) {
                    Toast.makeText(AreaPessoal.this, "Insira o seu Número de Utente", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nivelParkinsonStr)) {
                    Toast.makeText(AreaPessoal.this, "Insira o Nível de Parkinson", Toast.LENGTH_SHORT).show();
                    return;
                }

                int idade, nutente, nivelParkinson;
                try {
                    idade = Integer.parseInt(idadeStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(AreaPessoal.this, "A Idade deve ser um valor numérico.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    nutente = Integer.parseInt(nutenteStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(AreaPessoal.this, "O Número de Utente deve ser um valor numérico.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    nivelParkinson = Integer.parseInt(nivelParkinsonStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(AreaPessoal.this, "O Nível de Parkinson deve ser um valor numérico.", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                FirebaseUser user = mAuth.getCurrentUser();
                String uid = user.getUid();

                Map<String, Object> userInfo = new HashMap<>();
                userInfo.put("nome", nome);
                userInfo.put("idade", idade);
                userInfo.put("nivelParkinson", nivelParkinson);

                ref.child(uid).updateChildren(userInfo)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(AreaPessoal.this, "Atualização completa.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(AreaPessoal.this, "Falha ao atualizar os dados na base de dados.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
