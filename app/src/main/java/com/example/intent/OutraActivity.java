package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.intent.databinding.ActivityOutraBinding;

public class OutraActivity extends AppCompatActivity {
    private ActivityOutraBinding activityOutraBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOutraBinding = ActivityOutraBinding.inflate(getLayoutInflater());
        setContentView(activityOutraBinding.getRoot());

        Bundle parametrosBundle = getIntent().getExtras();
        if(parametrosBundle != null){
            String parametro = parametrosBundle.getString(MainActivity.PARAMETRO);
            activityOutraBinding.recebidoTv.setText(parametro);
        }

        Log.v(R.string.app_name + "/" + getLocalClassName(), "onCreate: iniciando ciclo completo");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.v(R.string.app_name + "/" + getLocalClassName(), "onStart: iniciando ciclo visivel");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(R.string.app_name + "/" + getLocalClassName(), "onResume: iniciando ciclo foreground");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.v(R.string.app_name + "/" + getLocalClassName(), "onPause: finalizando ciclo foreground");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.v(R.string.app_name + "/" + getLocalClassName(), "onStop: finalizando ciclo visivel");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(R.string.app_name + "/" + getLocalClassName(), "onDestroy: finalizando ciclo completo");
    }

    public void onClick(View view) {
        finish(); // chama onpause, stop e destroy
    }
}