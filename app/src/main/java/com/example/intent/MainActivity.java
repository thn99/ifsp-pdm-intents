package com.example.intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.intent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    // instancia view binding
    ActivityMainBinding activityMainBinding;

    public static final String PARAMETRO = "PARAMETRO";

    private final int OUTRA_ACTIVITY_REQUEST_CODE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        getSupportActionBar().setTitle("Tratando intents");
        getSupportActionBar().setSubtitle("Subtítulo");

        setContentView(activityMainBinding.getRoot());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.outraActivityMi:
                //Intent outraActivityIntent = new Intent(this, OutraActivity.class);
                Intent outraActivityIntent = new Intent("RECEBER_E_RETORNAR_ACTION");

                // jeito 1
                /*Bundle params = new Bundle();
                params.putString(PARAMETRO, activityMainBinding.parametroEt.getText().toString());
                outraActivityIntent.putExtras(params);*/

                // jeito 2 - precisa recuperar de outra forma na outra activity
                outraActivityIntent.putExtra(PARAMETRO, activityMainBinding.parametroEt.getText().toString());

                startActivityForResult(outraActivityIntent, OUTRA_ACTIVITY_REQUEST_CODE);

            case R.id.viewMi:
                Intent abrirNavegadorIntent = new Intent(Intent.ACTION_VIEW);
                abrirNavegadorIntent.setData(Uri.parse(activityMainBinding.parametroEt.getText().toString()));
                startActivity(abrirNavegadorIntent);

                return true;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (OUTRA_ACTIVITY_REQUEST_CODE == requestCode && resultCode == RESULT_OK)
            activityMainBinding.retornoTv.setText(data.getExtras().getString(OutraActivity.RETORNO));*/
        String retorno = data.getStringExtra(OutraActivity.RETORNO);
        if (OUTRA_ACTIVITY_REQUEST_CODE == requestCode && resultCode == RESULT_OK) {
            if (retorno != null){
                activityMainBinding.retornoTv.setText(retorno);
            }
        }

    }
}