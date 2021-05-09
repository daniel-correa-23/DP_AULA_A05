package com.desenvmovel.dp_aula_a06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CadastroTitulo extends AppCompatActivity {
    public static final String MEU_BANCO_DADOS = "BaseDadosAula06";
    private EditText mEditTextNomeFantasia;
    private TextView mTextViewNomeFantasia;
    private Button mButtonConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_titulo);
        mTextViewNomeFantasia = findViewById(R.id.idTextViewNF);
        mEditTextNomeFantasia = findViewById(R.id.idEditTextNF);
        mButtonConfirmar = findViewById(R.id.idButtonConfirmar);
    }

    public void cadastrarNomeEmpresa(View view){
        String nomeFantasia = mEditTextNomeFantasia.getText().toString();
        if(!nomeFantasia.isEmpty()) {
            getSharedPreferences(MainActivity.MEU_BANCO_DADOS, Context.MODE_PRIVATE).edit().putString("nomeFantasia", nomeFantasia).apply();
        }
        CadastroTitulo.super.onBackPressed();
    }
}