package com.example.aplicaoparkinson;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Registar extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword, editTextNome, editTextIdade, editTextNivelParkinson, editTextNUtente;
    Button btnRegistar;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;
    DatabaseReference ref;

   /* @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registar);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);
        btnRegistar = findViewById(R.id.btn_registar);
        editTextNome = findViewById(R.id.Nome);
        editTextIdade = findViewById(R.id.Idade);
        editTextNUtente = findViewById(R.id.nutente);
        editTextNivelParkinson = findViewById(R.id.nivel_parkinson);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        textView = findViewById(R.id.loginnow);

        ref = FirebaseDatabase.getInstance().getReference("users");

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegistar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String nome = editTextNome.getText().toString().trim();
                String idadeStr = editTextIdade.getText().toString().trim();
                String nutenteStr = editTextNUtente.getText().toString().trim();
                String nivelParkinsonStr = editTextNivelParkinson.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(Registar.this, "Insira o seu Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(Registar.this, "Insira uma Password", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nome)) {
                    Toast.makeText(Registar.this, "Insira o seu Nome", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(idadeStr)) {
                    Toast.makeText(Registar.this, "Insira a sua Idade", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nutenteStr)) {
                    Toast.makeText(Registar.this, "Insira o seu Número de Utente", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(nivelParkinsonStr)) {
                    Toast.makeText(Registar.this, "Insira o Nível de Parkinson", Toast.LENGTH_SHORT).show();
                    return;
                }

                int idade, nutente, nivelParkinson;
                try {
                    idade = Integer.parseInt(idadeStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(Registar.this, "A Idade deve ser um valor numérico.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    nutente = Integer.parseInt(nutenteStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(Registar.this, "O Número de Utente deve ser um valor numérico.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    nivelParkinson = Integer.parseInt(nivelParkinsonStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(Registar.this, "O Nível de Parkinson deve ser um valor numérico.", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    String uid = mAuth.getCurrentUser().getUid();
                                    User user = new User(nome, idade, nutente, nivelParkinson);

                                        ref.child(uid).setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(Registar.this, "Registro completo.", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(Registar.this, "Falha ao salvar os dados na base de dados.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(Registar.this, "Falha na autenticação.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}

