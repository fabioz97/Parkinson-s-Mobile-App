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

public class QuestionarioNoite extends AppCompatActivity {
    // criação das variáveis globais
    Button btn_Submeter;
    RadioGroup RG_Pergunta1, RG_Pergunta2, RG_Pergunta3, RG_Pergunta4, RG_Pergunta5,RG_Pergunta6,RG_Pergunta7,RG_Pergunta8,RG_Pergunta9,RG_Pergunta10;
    FirebaseAuth mAuth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionario_noite);
        // inicialização das variáveis
        RG_Pergunta1 = findViewById(R.id.Pergunta1);
        RG_Pergunta2 = findViewById(R.id.Pergunta2);
        RG_Pergunta3 = findViewById(R.id.Pergunta3);
        RG_Pergunta4 = findViewById(R.id.Pergunta4);
        RG_Pergunta5 = findViewById(R.id.Pergunta5);
        RG_Pergunta6 = findViewById(R.id.Pergunta6);
        RG_Pergunta7 = findViewById(R.id.Pergunta7);
        RG_Pergunta8 = findViewById(R.id.Pergunta8);
        RG_Pergunta9 = findViewById(R.id.Pergunta9);
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
        int idRadioButton6 = RG_Pergunta6.getCheckedRadioButtonId();
        int idRadioButton7 = RG_Pergunta7.getCheckedRadioButtonId();
        int idRadioButton8 = RG_Pergunta8.getCheckedRadioButtonId();
        int idRadioButton9 = RG_Pergunta9.getCheckedRadioButtonId();
        // averiguação se todas as respostas foram respondidas
        if (idRadioButton1 != -1 && idRadioButton2 != -1 && idRadioButton3 != -1 && idRadioButton4 != -1 && idRadioButton5 != -1 && idRadioButton6 != -1 && idRadioButton7 != -1 && idRadioButton8 != -1 && idRadioButton9 != -1 ) {
            // inicialização do objeto radiButton com a varivel inteira criada anteriormente
            RadioButton radioButton1 = findViewById(idRadioButton1);
            RadioButton radioButton2 = findViewById(idRadioButton2);
            RadioButton radioButton3 = findViewById(idRadioButton3);
            RadioButton radioButton4 = findViewById(idRadioButton4);
            RadioButton radioButton5 = findViewById(idRadioButton5);
            RadioButton radioButton6 = findViewById(idRadioButton6);
            RadioButton radioButton7 = findViewById(idRadioButton7);
            RadioButton radioButton8 = findViewById(idRadioButton8);
            RadioButton radioButton9 = findViewById(idRadioButton9);
            //obtenção do valor de sentimento com o metodo getTag
            int valorSentimento1 = Integer.parseInt(radioButton1.getTag().toString());
            int valorSentimento2 = Integer.parseInt(radioButton2.getTag().toString());
            int valorSentimento3 = Integer.parseInt(radioButton3.getTag().toString());
            int valorSentimento4 = Integer.parseInt(radioButton4.getTag().toString());
            int valorSentimento5 = Integer.parseInt(radioButton5.getTag().toString());
            int valorSentimento6 = Integer.parseInt(radioButton6.getTag().toString());
            int valorSentimento7 = Integer.parseInt(radioButton7.getTag().toString());
            int valorSentimento8 = Integer.parseInt(radioButton8.getTag().toString());
            int valorSentimento9 = Integer.parseInt(radioButton9.getTag().toString());

            long timestamp = System.currentTimeMillis();
            //Instanciamento dos objetos Resposta
            Resposta Resposta1 = new Resposta("Onde esteve no decorrer da tarde?", radioButton1.getText().toString(), valorSentimento1, timestamp);
            Resposta Resposta2 = new Resposta("Com quem esteve de tarde?", radioButton2.getText().toString(), valorSentimento2, timestamp);
            Resposta Resposta3 = new Resposta("O que esteve a fazer de tarde?", radioButton3.getText().toString(), valorSentimento3, timestamp);
            Resposta Resposta4 = new Resposta("Experienciei rizidez.", radioButton4.getText().toString(), valorSentimento4, timestamp);
            Resposta Resposta5 = new Resposta("Movimentei-me devagar", radioButton5.getText().toString(), valorSentimento5, timestamp);
            Resposta Resposta6 = new Resposta("Consegui estar de pé com facilidade?", radioButton6.getText().toString(), valorSentimento6, timestamp);
            Resposta Resposta7 = new Resposta("Consegui falar com facilidade?", radioButton7.getText().toString(), valorSentimento7, timestamp);
            Resposta Resposta8 = new Resposta("Experienciei tremor?", radioButton8.getText().toString(), valorSentimento8, timestamp);
            Resposta Resposta9 = new Resposta("Consegui andar com facilidade?.", radioButton9.getText().toString(), valorSentimento9, timestamp);

            salvarRespostaNoFirebase(Resposta1);
            salvarRespostaNoFirebase(Resposta2);
            salvarRespostaNoFirebase(Resposta3);
            salvarRespostaNoFirebase(Resposta4);
            salvarRespostaNoFirebase(Resposta5);
            salvarRespostaNoFirebase(Resposta6);
            salvarRespostaNoFirebase(Resposta7);
            salvarRespostaNoFirebase(Resposta8);
            salvarRespostaNoFirebase(Resposta9);
        } else {
            Log.d("Resposta", "Resposta nao selecionada.");
            Toast.makeText(QuestionarioNoite.this, "Responda a Todas as Perguntas", Toast.LENGTH_SHORT).show();

        }
    }

    private void salvarRespostaNoFirebase(Resposta RespostaUtilizador) {
        FirebaseUser UtilizadorAtual = mAuth.getCurrentUser();
        String uid = UtilizadorAtual.getUid();
        DatabaseReference referenciaQuestionarioUtilizador = ref
                .child(uid)
                .child("questionarios")
                .child("questionario noite")
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

