package com.example.intent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.intent.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    // instancia view binding
    ActivityMainBinding activityMainBinding;

    public static final String PARAMETRO = "PARAMETRO";

    private final int OUTRA_ACTIVITY_REQUEST_CODE = 0;

    private final int CALL_PHONE_REQUEST_CODE = 1;

    private final int PICK_IMAGEM_REQUEST_CODE = 2;

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
                return true;

            case R.id.viewMi:
                Intent abrirNavegadorIntent = new Intent(Intent.ACTION_VIEW);
                abrirNavegadorIntent.setData(Uri.parse(activityMainBinding.parametroEt.getText().toString()));
                startActivity(abrirNavegadorIntent);

                return true;
            case R.id.callMi:
                verifyCallPermission();
                return true;
            case R.id.dialMi:
                Intent discarIntent = new Intent(Intent.ACTION_DIAL);
                discarIntent.setData(Uri.parse("tel:" + activityMainBinding.parametroEt.getText().toString()));
                startActivity(discarIntent);
                return true;
            case R.id.pickMi:
                Intent pegarImagemIntent = new Intent(Intent.ACTION_PICK);
                String diretorioImagens = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath();
                pegarImagemIntent.setDataAndType(Uri.parse(diretorioImagens), "image/*");
                startActivityForResult(pegarImagemIntent, PICK_IMAGEM_REQUEST_CODE);
                return true;

        }
        return false;
    }

    private void verifyCallPermission() {
        Intent ligarIntent = new Intent(Intent.ACTION_CALL);
        ligarIntent.setData(Uri.parse("tel:" + activityMainBinding.parametroEt.getText().toString()));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
                startActivity(ligarIntent);
            } else {
                // solicitar permissao
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, CALL_PHONE_REQUEST_CODE);
            }
        } else {
            startActivity(ligarIntent);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == CALL_PHONE_REQUEST_CODE){
            if (permissions[0].equals(Manifest.permission.CALL_PHONE)
            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                verifyCallPermission();
            }
            Toast.makeText(this, "Preciso de permissão", Toast.LENGTH_SHORT).show();
        }
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
        } else {
            if(requestCode == PICK_IMAGEM_REQUEST_CODE && resultCode == RESULT_OK){
                Uri imagemUri = data.getData();

                Intent visualizarImagem = new Intent(Intent.ACTION_VIEW, imagemUri);
                startActivity(visualizarImagem);
            }
        }

    }
}