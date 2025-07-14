package com.example.aplicaoparkinson;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TesteTremorMaoDireita extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    Sensor accelerometer;
    Sensor gyroscope;
    TextView textView;
    Button btnComecarTeste, btnSairMenu;
    DatabaseReference ref;
    String uid; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_tremor_mao_direita);

        textView = findViewById(R.id.textView);
        btnComecarTeste = findViewById(R.id.btnComecarTeste);
        btnSairMenu = findViewById(R.id.btn_Sair);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        ref = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        uid=user.getUid();
        // Definir o clique do botão para começar o teste
        btnComecarTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarTeste();
            }
        });
        btnSairMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void iniciarTeste() {
        // Butão desparece enquanto ocorre o teste
        btnComecarTeste.setEnabled(false);

        // Ligar os sensores para captura de dados
            sensorManager.registerListener(TesteTremorMaoDireita.this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);

            sensorManager.registerListener(TesteTremorMaoDireita.this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);


        // Iniciar contagem de 10 segundos
        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                textView.setText("Tempo restante: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                // Desligar os sensores após a contagem
                sensorManager.unregisterListener(TesteTremorMaoDireita.this, accelerometer);
                sensorManager.unregisterListener(TesteTremorMaoDireita.this, gyroscope);

                // Reativar botão
                btnComecarTeste.setEnabled(true);
                textView.setText("Teste concluído!");
            }
        }.start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            Log.d("Accelerometer", "X: " + x + ", Y: " + y + ", Z: " + z);

            // Enviar dados para o Firebase Realtime Database
            enviarDadosParaFirebase("Acelerometro", x, y, z);
        } else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            Log.d("Gyroscope", "X: " + x + ", Y: " + y + ", Z: " + z);

            // Enviar dados para o Firebase Realtime Database
            enviarDadosParaFirebase("Giroscopio", x, y, z);
        }
    }

    private void enviarDadosParaFirebase(String tipo, float x, float y, float z) {
        // Verificar se o UID do utilizador está disponível
        if (uid != null) {
            // Obter uma referência para o nó do utilizador
            DatabaseReference TesteRef = ref.child("users").child(uid);

            // Construir o objeto de dados para enviar ao Firebase
            DadosSensor dados = new DadosSensor(tipo, x, y, z);

            // Inserir os dados no nó 'testes' -> 'teste_mao_direita'
            TesteRef.child("testes").child("teste mao direita").push().setValue(dados);
        } else {
            Log.e("EnviarParaFirebase", "UID do utilizador não está disponível.");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle changes in sensor accuracy if needed
    }
}


