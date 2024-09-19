package br.com.fabriciocurvello.appdetectchargedbrjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView tvPowerStatus;

    // BroadcastReceiver para capturar os eventos de conexão e desconexão do cabo de energia
    private BroadcastReceiver powerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_POWER_CONNECTED.equals(action)) {
                tvPowerStatus.setText("Cabo de energia conectado");
            } else if (Intent.ACTION_POWER_DISCONNECTED.equals(action)) {
                tvPowerStatus.setText("Cabo de energia desconectado");
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Conectar o TextView do layout
        tvPowerStatus = findViewById(R.id.tv_power_status);

        // Registrar o BroadcastReceiver dinamicamente
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(powerReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Cancelar o registro do BroadcastReceiver ao destruir a atividade
        unregisterReceiver(powerReceiver);
    }
}


