package br.com.example.jornadaescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.example.jornadaescolar.R;
import br.com.example.jornadaescolar.model.Disciplina;

public class CalculaNotaActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_CALCULA_NOTA = "CALCULAR NOTAS";
    private TextView campoNomeDisciplina;
    private Disciplina disciplina;
    private TextView campoParecer;
    private EditText campoNotaA1;
    private EditText campoNotaA2;
    private EditText campoNotaAF;
    private Double notaMaiorA1A2 = 0.0;
    private TextView tituloAF;
    private Button botaoCalcularA1A2;
    private Button botaoCalcularAvaliacaoFinal;
    private Intent vaiParaActivityMediaFinal;
    private double enviarMedia = -2.0;
    private double primeiraMedia;
    private double segundaMedia;
    private double notaA1;
    private double notaA2;
    private double notaAF;
    private boolean preenchimentoValido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcula_nota);
        setTitle(TITULO_APPBAR_CALCULA_NOTA);

        inicializacaoDosCampos();
        configuraFabVoltar();
        preencheNomeDaDisciplina();
        botaoCalcularA1A2();

    }

    private void configuraFabVoltar() {
        ImageButton botaoVoltar = findViewById(R.id.image_btn_activity_calcular_nota_voltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void inicializacaoDosCampos() {
        campoNomeDisciplina = findViewById(R.id.txt_activity_calcula_nota_nome_disciplina);
        campoParecer = findViewById(R.id.txt_activity_calcula_nota_parecer);
        campoNotaA1 = findViewById(R.id.activity_calcula_nota_a1);
        campoNotaA2 = findViewById(R.id.activity_calcula_nota_a2);
        tituloAF = findViewById(R.id.txt_activity_calcula_nota_af);
        campoNotaAF = findViewById(R.id.activity_calcula_nota_af);
        botaoCalcularAvaliacaoFinal = findViewById(R.id.btn_activity_calcula_nota_af);
        botaoCalcularA1A2 = findViewById(R.id.btn_activity_calcula_nota_calcular);
    }

    private void botaoCalcularA1A2() {
        botaoCalcularA1A2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verificando campos em branco
                if(campoNotaA1.getText().toString().equals("")||campoNotaA2.getText().toString().equals("")){
                    campoParecer.setText("Preencha Todos os campos antes de calcular");
                    campoParecer.setTextColor(getColor(R.color.corReprovado));
                }
                //verificando campos com ponto "."
                else if(campoNotaA1.getText().toString().equals(".")||campoNotaA2.getText().toString().equals(".")){
                    campoParecer.setText("Preencha Todos os campos com numeros de 0 até 5");
                    campoParecer.setTextColor(getColor(R.color.corReprovado));
                }
                //iniciando calculo
                else{
                    calculandoNotaExibindoResultado();
                }
            }
        });
    }

    private void calculandoNotaExibindoResultado() {
        calculandoPrimeiraMedia();
        separaNotaMaiorA1A2(notaA1, notaA2);
        validandoPreenchimentoDosCampos();
        if (preenchimentoValido) {
            arredondandoPrimeiraMedia();
            if (primeiraMedia >= 6) {
                //Ações caso seja aprovado direto
                vaiParaActivityMediaFinal = new Intent(CalculaNotaActivity.this, MediaFinalActivity.class);
                vaiParaActivityMediaFinal.putExtra("media", primeiraMedia);
                vaiParaActivityMediaFinal.putExtra("nomeDisciplina", disciplina.getNomeDisciplina());
                startActivity(vaiParaActivityMediaFinal);
                finish();
            } else {
                //Ações caso vá para avaliação final
                campoParecer.setText("Media: " + primeiraMedia + " \nConceito: Nota Insuficiente\n\nPrecisa fazer Avaliação Final !!\nInforme nota AF e calcule novamente");
                campoParecer.setTextColor(getColor(R.color.corReprovado));
                tituloAF.setVisibility(View.VISIBLE);
                campoNotaAF.setVisibility(View.VISIBLE);
                botaoCalcularA1A2.setVisibility(View.INVISIBLE);
                configurandoBotaoAvaliacaoFinal();
            }
        }
    }

    private void validandoPreenchimentoDosCampos() {
        if((notaA1 > 5.0 || notaA1 < 0.0) && (notaA2 > 5.0 || notaA2 < 0.0)){
            campoParecer.setText("Erro:\nInforme uma nota A1 e nota A2 de 0 até 5.");
            campoParecer.setTextColor(getColor(R.color.corReprovado));
            campoNotaA1.setText(null);
            campoNotaA2.setText(null);
            preenchimentoValido = false;
        }else if((notaA1 > 5.0 || notaA1 < 0.0)){
            campoParecer.setText("Erro:\nInforme uma nota A1 de 0 até 5.");
            campoParecer.setTextColor(getColor(R.color.corReprovado));
            campoNotaA1.setText(null);
            preenchimentoValido = false;
        }else if(notaA2 > 5.0 || notaA2 < 0.0){
            campoParecer.setText("Erro:\nInforme uma nota A2 de 0 até 5.");
            campoParecer.setTextColor(getColor(R.color.corReprovado));
            campoNotaA2.setText(null);
            preenchimentoValido = false;
        }else{
            preenchimentoValido = true;
        }

    }

    private void calculandoPrimeiraMedia() {
        notaA1 = Double.parseDouble(campoNotaA1.getText().toString());
        notaA2 = Double.parseDouble(campoNotaA2.getText().toString());
        primeiraMedia = notaA1 + notaA2;
    }

    private void configurandoBotaoAvaliacaoFinal() {
        campoNotaA1.setFocusable(false); //Não deixa mais digitar nesse campo
        campoNotaA2.setFocusable(false); //Não deixa mais digitar nesse campo
        //Button AF *************************************************
        botaoCalcularAvaliacaoFinal.setVisibility(View.VISIBLE);
        botaoCalcularAvaliacaoFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campoNotaAF.getText().toString().equals("")){
                    Toast.makeText(CalculaNotaActivity.this, "Informe o valor da Nota AF", Toast.LENGTH_SHORT).show();
                }
                else if(campoNotaAF.getText().toString().equals(".")){
                    Toast.makeText(CalculaNotaActivity.this, "Informe uma nota AF com numeros de 0 até 5", Toast.LENGTH_SHORT).show();
                }
                else{
                    calculandoSegundaMedia();
                    if(notaAF > 5 || notaAF < 0){
                        Toast.makeText(CalculaNotaActivity.this, "Informe uma nota AF de 0 até 5", Toast.LENGTH_SHORT).show();
                        campoNotaAF.setText(null);
                    }else{
                        arredondandoSegundaMedia();
                        varificandoMediaMaior();

                        //Enviando dados e indo para outra Activity ****************************************************************************************
                        vaiParaActivityMediaFinal = new Intent(CalculaNotaActivity.this, MediaFinalActivity.class);
                        vaiParaActivityMediaFinal.putExtra("media", enviarMedia);
                        vaiParaActivityMediaFinal.putExtra("nomeDisciplina", disciplina.getNomeDisciplina());
                        startActivity(vaiParaActivityMediaFinal);
                        finish();
                    }
                }
            }
        });
    }

    private void calculandoSegundaMedia() {
        //calculo nota af
        notaAF = Double.parseDouble(campoNotaAF.getText().toString());
        segundaMedia = notaMaiorA1A2 + notaAF;
    }

    private void varificandoMediaMaior() {
        if(segundaMedia > primeiraMedia) enviarMedia = segundaMedia;
        else if (segundaMedia < primeiraMedia) enviarMedia = primeiraMedia;
        else enviarMedia = primeiraMedia;
    }

    private void arredondandoSegundaMedia() {
        if (segundaMedia >= 5.75 && segundaMedia < 6) segundaMedia = 6.00;
    }

    private void arredondandoPrimeiraMedia() {
        if(primeiraMedia >= 5.75 && primeiraMedia < 6) primeiraMedia = 6.00;
    }

    private void separaNotaMaiorA1A2(Double notaA1, Double notaA2) {
        if(notaA1 > notaA2) notaMaiorA1A2 = notaA1;
        else if (notaA2 > notaA1) notaMaiorA1A2 = notaA2;
        else if (notaA1 == notaA2) notaMaiorA1A2 = notaA1;
        else notaMaiorA1A2 = -1.0;
    }

    private void preencheNomeDaDisciplina() {
        Intent dados = getIntent();
        disciplina = (Disciplina) dados.getSerializableExtra("disciplina");
        campoNomeDisciplina.setText("Disciplina: " + disciplina.getNomeDisciplina());
    }
}