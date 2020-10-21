package br.com.example.jornadaescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import br.com.example.jornadaescolar.R;

public class MediaFinalActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_MEDIA_FINAL = "MÉDIA FINAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_final);
        setTitle(TITULO_APPBAR_MEDIA_FINAL);
        TextView campoNomeDisciplina = findViewById(R.id.txt_activity_media_final_nome_disciplina);
        TextView campoMediaFinal = findViewById(R.id.txt_activity_media_final_resultado_valor);
        TextView campoParecer = findViewById(R.id.txt_activity_media_final_parecer);

        Double media = getIntent().getDoubleExtra("media", 0.0);
        String disciplina = getIntent().getStringExtra("nomeDisciplina");

        campoMediaFinal.setText(media.toString());
        campoNomeDisciplina.setText("Disciplina: " + disciplina);

        if(media >= 6){
            campoParecer.setText("Parabéns !! Você foi aprovado. \n:)");
            campoParecer.setTextColor(getColor(R.color.corAprovado));
        }else{
            campoParecer.setText("Infelizmente você foi reprovado. \n:(");
            campoParecer.setTextColor(getColor(R.color.corReprovado));
        }

        Button botaoVoltar = findViewById(R.id.btn_activity_media_final_calcular_voltar);
        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarParaListaDeDisciplinas = new Intent(MediaFinalActivity.this, ListaDisciplinasActivity.class);
                startActivity(voltarParaListaDeDisciplinas);
                finish();
            }
        });
    }
}