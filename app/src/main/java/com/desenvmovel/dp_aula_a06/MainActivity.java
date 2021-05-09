package com.desenvmovel.dp_aula_a06;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private NumberPicker mNumberPicker;
    private RadioGroup mRadioGroup;
    private RadioButton mBtnAdicionar, mBtnExcluir;
    private EditText mEditText;
    private TextView mTextView, mTextView2;
    private Button mButton, mButtonTitulo;

    public static final String MEU_BANCO_DADOS = "BaseDadosAula06"; //MinhaBaseDeDados.xml

    public static  final int ANO_MIN = 2016;
    public static  final int ANO_MAX = 2021;


    private NumberPicker.OnValueChangeListener valorAlteradoListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int anoAntigo, int anoAtual) {
            System.out.println("Valor selecionado"+anoAtual);
            exibirSaldo(anoAtual);
            mTextView2.setText("");
            mBtnAdicionar.setChecked(false);
            mBtnExcluir.setChecked(false);
        }
    };

    private Button.OnClickListener mButtonAdicionarTituloClick = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intentCadastroTitulo = new Intent(getBaseContext(), CadastroTitulo.class);
            startActivity(intentCadastroTitulo);
        }
    };

    private Button.OnClickListener mButtonConfirmarClick = new Button.OnClickListener(){
        @Override
        public void onClick(View view) {

            if(!mEditText.getText().toString().isEmpty()){
                float valor = Float.parseFloat(mEditText.getText().toString());
                int ano = mNumberPicker.getValue();

                switch (mRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.idRadioButtonAdicionar:
                        adicionarValor(ano,valor);
                        mTextView2.setText("Adicionado no ano:"+ ano + " o valor de " + String.format("R$ %.2f",valor));
                        break;
                    case R.id.idRadioButtonExcluir:
                        excluirValor(ano,valor);
                        if(valor == 0){
                            mTextView2.setText("Exclusão não permitida!");
                        }else {
                            mTextView2.setText("Excluido no ano:" + ano + " o valor de " + String.format("R$ %.2f",valor));
                        }
                        break;
                    default:
                        mTextView2.setText("Marque uma opção Adicionar ou Excluir");
                }
                exibirSaldo(ano);

            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumberPicker = findViewById(R.id.numberPicker);
        mNumberPicker.setMinValue(ANO_MIN);
        mNumberPicker.setMaxValue(ANO_MAX);
        mNumberPicker.setOnValueChangedListener(valorAlteradoListener);

        mRadioGroup = findViewById(R.id.idRadioGroup);
        mBtnAdicionar = findViewById(R.id.idRadioButtonAdicionar);
        mBtnExcluir = findViewById(R.id.idRadioButtonExcluir);
        mEditText = findViewById(R.id.idEditTextNF);
        mTextView = findViewById(R.id.idTextViewNF);
        mTextView2 = findViewById(R.id.idTextView2);
        mButton = findViewById(R.id.idButtonConfirmar);
        mButtonTitulo = findViewById(R.id.idButtonTitulo);
        exibirSaldo(mNumberPicker.getValue());
        mButton.setOnClickListener(mButtonConfirmarClick);
        mButtonTitulo.setOnClickListener(mButtonAdicionarTituloClick);

        SharedPreferences sharedPreferences = getSharedPreferences(MEU_BANCO_DADOS, Context.MODE_PRIVATE);
        String nomeFantasia = sharedPreferences.getString("nomeFantasia", null);
        System.out.println(">>>>>>>>>>>>>>>>>>"+nomeFantasia);
        if(null != nomeFantasia && !nomeFantasia.isEmpty()) {
            setTitle(nomeFantasia);
        }
    }

    private void adicionarValor(int ano, float valor){
        SharedPreferences sharedPreferences = getSharedPreferences(MEU_BANCO_DADOS, Context.MODE_PRIVATE);
        float valorAtual = sharedPreferences.getFloat(String.valueOf(ano), 0);
        float novoValor = valorAtual + valor;
        sharedPreferences.edit().putFloat(String.valueOf(ano), novoValor).apply();
        mEditText.setText("");

    }
    private void excluirValor(int ano, float valor){
        SharedPreferences sharedPreferences = getSharedPreferences(MEU_BANCO_DADOS, Context.MODE_PRIVATE);
        float valorAtual =  sharedPreferences.getFloat(String.valueOf(ano), 0);
        float novoValor = valorAtual - valor;
        if(novoValor < 0){
            novoValor = 0;
            mTextView2.setText("Exclusão não permitida!");
        }
        sharedPreferences.edit().putFloat(String.valueOf(ano), novoValor).apply();
        mEditText.setText("");
    }
    private void exibirSaldo(int ano){
        SharedPreferences sharedPreferences = getSharedPreferences(MEU_BANCO_DADOS, Context.MODE_PRIVATE);
        float saldo = sharedPreferences.getFloat(String.valueOf(ano), 0);
        mTextView.setText(String.format("R$ %.2f",saldo));
    }
    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = getSharedPreferences(MEU_BANCO_DADOS,Context.MODE_PRIVATE);
        String nomeFantasia = sharedPreferences.getString("nomeFantasia", null);
        if(null != nomeFantasia && !nomeFantasia.isEmpty()) {
            setTitle(nomeFantasia);
        }
    }


}
