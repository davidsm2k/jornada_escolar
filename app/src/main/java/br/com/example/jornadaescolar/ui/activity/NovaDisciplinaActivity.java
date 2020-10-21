package br.com.example.jornadaescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import br.com.example.jornadaescolar.R;
import br.com.example.jornadaescolar.dao.DisciplinaDAO;
import br.com.example.jornadaescolar.model.Disciplina;

public class NovaDisciplinaActivity extends AppCompatActivity {
    public static final String TITULO_APPBAR_NOVA_DISCIPLINA = "NOVA DISCIPLINA";
    public static final String TITULO_APPBAR_EDITA_DISCIPLINA = "EDITA DISCIPLINA";
    private EditText nomeDisciplina;
    private DisciplinaDAO dao = new DisciplinaDAO();
    private TextView campoParecer;
    private Disciplina disciplina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_disciplina);

        nomeDisciplina = findViewById(R.id.activity_nova_disciplina_nome);
        campoParecer = findViewById(R.id.txt_activity_nova_disciplina_parecer);

        Intent dados = getIntent();
        if (dados.hasExtra("nomeDisciplina")) {
            disciplina = (Disciplina) dados.getSerializableExtra("nomeDisciplina");
            nomeDisciplina.setText(disciplina.getNomeDisciplina());
            setTitle(TITULO_APPBAR_EDITA_DISCIPLINA);
        } else {
            setTitle(TITULO_APPBAR_NOVA_DISCIPLINA);
            disciplina = new Disciplina();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.activity_nova_disciplina_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (nomeDisciplina.getText().toString().equals("")) {
            campoParecer.setText("Informe o nome da disciplina");
            campoParecer.setTextColor(getColor(R.color.corReprovado));
        } else {
            campoParecer.setText("");
            //Adicionando nova disciplina a lista
            preencheDisciplina();
            finalizaFormulario();
            //Limpar campo
            nomeDisciplina.setText(null);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void finalizaFormulario() {
        if (disciplina.setIdValido()) {
            //Edita Disciplina
            dao.edita(disciplina);
        } else {
            //Salvar Disciplina
            dao.salva(disciplina);
        }
    }

    private void preencheDisciplina() {
        String nome = nomeDisciplina.getText().toString();

        disciplina.setNomeDisciplina(nome);
    }
}