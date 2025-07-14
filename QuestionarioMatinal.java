package com.example.aplicaoparkinson;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class QuestionarioMatinal extends AppCompatActivity {
    // criação das variáveis globais
     Button btn_Submeter;
     RadioGroup RG_Pergunta1, RG_Pergunta2, RG_Pergunta3, RG_Pergunta4, RG_Pergunta5;
     FirebaseAuth mAuth;
     DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario_matinal);
        // inicialização das variáveis
        RG_Pergunta1 = findViewById(R.id.Pergunta1);
        RG_Pergunta2 = findViewById(R.id.Pergunta2);
        RG_Pergunta3 = findViewById(R.id.Pergunta3);
        RG_Pergunta4 = findViewById(R.id.Pergunta4);
        RG_Pergunta5 = findViewById(R.id.Pergunta5);
        btn_Submeter = findViewById(R.id.botaoSubmeter);
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference("users");

        btn_Submeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submeterRespostas();
            }

        });
    }

    private void submeterRespostas() {
        //inicialização dos id selecionados dos RadioButtons selecionados nos RadioGroups
        int idRadioButton1 = RG_Pergunta1.getCheckedRadioButtonId();
        int idRadioButton2 = RG_Pergunta2.getCheckedRadioButtonId();
        int idRadioButton3 = RG_Pergunta3.getCheckedRadioButtonId();
        int idRadioButton4 = RG_Pergunta4.getCheckedRadioButtonId();
        int idRadioButton5 = RG_Pergunta5.getCheckedRadioButtonId();
        // averiguação se todas as respostas foram respondidas
        if (idRadioButton1 != -1 && idRadioButton2 != -1 && idRadioButton3 != -1 && idRadioButton4 != -1 && idRadioButton5 != -1 ) {
            // inicialização do objeto radiButton com a varivel inteira criada anteriormente
            RadioButton radioButton1 = findViewById(idRadioButton1);
            RadioButton radioButton2 = findViewById(idRadioButton2);
            RadioButton radioButton3 = findViewById(idRadioButton3);
            RadioButton radioButton4 = findViewById(idRadioButton4);
            RadioButton radioButton5 = findViewById(idRadioButton5);
            //obtenção do valor de sentimento com o metodo getTag
            int valorSentimento1 = Integer.parseInt(radioButton1.getTag().toString());
            int valorSentimento2 = Integer.parseInt(radioButton2.getTag().toString());
            int valorSentimento3 = Integer.parseInt(radioButton3.getTag().toString());
            int valorSentimento4 = Integer.parseInt(radioButton4.getTag().toString());
            int valorSentimento5 = Integer.parseInt(radioButton5.getTag().toString());

            long timestamp = System.currentTimeMillis();
            //Instanciamento dos objetos Resposta
            Resposta Resposta1 = new Resposta("Dormi bem?", radioButton1.getText().toString(), valorSentimento1, timestamp);
            Resposta Resposta2 = new Resposta("Acordei várias vezes ao longo da noite?", radioButton2.getText().toString(), valorSentimento2, timestamp);
            Resposta Resposta3 = new Resposta("Sinto-me descansado?", radioButton3.getText().toString(), valorSentimento3, timestamp);
            Resposta Resposta4 = new Resposta("Foi fisicamente difícil levantar-me?", radioButton4.getText().toString(), valorSentimento4, timestamp);
            Resposta Resposta5 = new Resposta("Foi mentalmente difícil levantar-me?", radioButton5.getText().toString(), valorSentimento5, timestamp);

            salvarRespostaNoFirebase(Resposta1);
            salvarRespostaNoFirebase(Resposta2);
            salvarRespostaNoFirebase(Resposta3);
            salvarRespostaNoFirebase(Resposta4);
            salvarRespostaNoFirebase(Resposta5);
        } else {
            Log.d("Resposta", "Resposta nao selecionada.");
            Toast.makeText(QuestionarioMatinal.this, "Responda a Todas as Perguntas", Toast.LENGTH_SHORT).show();

        }
    }

    private void salvarRespostaNoFirebase(Resposta RespostaUtilizador) {
        FirebaseUser UtilizadorAtual = mAuth.getCurrentUser();
            String uid = UtilizadorAtual.getUid();
            DatabaseReference referenciaQuestionarioUtilizador = ref
                    .child(uid)
                    .child("questionarios")
                    .child("questionario matinal")
                    .push();

            Map<String, Object> mapaResposta = new HashMap<>();
            mapaResposta.put("pergunta", RespostaUtilizador.getTextoPergunta());
            mapaResposta.put("resposta", RespostaUtilizador.getTextoResposta());
            mapaResposta.put("valorSentimento", RespostaUtilizador.getValorSentimento());
            mapaResposta.put("timestamp", RespostaUtilizador.getTimestamp());

            referenciaQuestionarioUtilizador.setValue(mapaResposta)
                    .addOnSuccessListener(aVoid -> Log.d("Firebase", "Questionário salvo com sucesso"))
                    .addOnFailureListener(e -> Log.d("Firebase", "Erro ao salvar o questionário", e));
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }
}

