package com.example.aplicaoparkinson;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {

    TextInputEditText editTextEmail, editTextPassword;
    Button btnLogin;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    TextView textView;

    /*@Override

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();        }
    }  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail=findViewById(R.id.email);
        editTextPassword=findViewById(R.id.password);
        btnLogin=findViewById(R.id.btn_login);
        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);
        textView=findViewById(R.id.registarnow);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Registar.class);
                startActivity(intent);
                finish();
            }
        });
         btnLogin.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String email, password;
                 email = String.valueOf(editTextEmail.getText());
                 password = String.valueOf(editTextPassword.getText());
                 progressBar.setVisibility(View.VISIBLE);

                 if (TextUtils.isEmpty(email)) {
                     Toast.makeText(Login.this, "Insira o seu Email", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 if (TextUtils.isEmpty(password)) {
                     Toast.makeText(Login.this, "Insira uma Password", Toast.LENGTH_SHORT).show();
                     return;
                 }
                 mAuth.signInWithEmailAndPassword(email, password)
                         .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                             @Override
                             public void onComplete(@NonNull Task<AuthResult> task) {
                                 progressBar.setVisibility(View.GONE);
                                 if (task.isSuccessful()) {
                                     Toast.makeText(Login.this, " Autenticação Completa.",
                                             Toast.LENGTH_SHORT).show();
                                     Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                     startActivity(intent);
                                     finish();
                                 } else {
                                     Toast.makeText(Login.this, "Autenticação falhada.",
                                             Toast.LENGTH_SHORT).show();
                                 }
                             }
                         });

             }

             });

    }
}