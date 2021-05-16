package com.example.intent;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.intent.databinding.ActivityOutraBinding;

public class OutraActivity extends AppCompatActivity {
    private ActivityOutraBinding activityOutraBinding;
    public static String RETORNO = "RETORNO";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityOutraBinding = ActivityOutraBinding.inflate(getLayoutInflater());
        setContentView(activityOutraBinding.getRoot());

        // recebendo do jeito 1
        /*Bundle parametrosBundle = getIntent().getExtras();
        if(parametrosBundle != null){
            String parametro = parametrosBundle.getString(MainActivity.PARAMETRO);
            activityOutraBinding.recebidoTv.setText(parametro);
        }*/

        // jeito 2
        String parametro = getIntent().getStringExtra(MainActivity.PARAMETRO);
        if (parametro != null)
            activityOutraBinding.recebidoTv.setText(parametro);

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
        Intent intent = new Intent();
        intent.putExtra(RETORNO, activityOutraBinding.retornoEt.getText().toString());
        setResult(RESULT_OK, intent);

        finish(); // chama onpause, stop e destroy
    }
}