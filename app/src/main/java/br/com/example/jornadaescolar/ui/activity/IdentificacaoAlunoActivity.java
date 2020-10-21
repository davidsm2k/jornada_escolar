package br.com.example.jornadaescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.example.jornadaescolar.R;
import br.com.example.jornadaescolar.dao.AlunoDAO;
import br.com.example.jornadaescolar.model.Aluno;

public class IdentificacaoAlunoActivity extends AppCompatActivity {

    private EditText campoRa;
    private EditText campoNomeAluno;
    private EditText campoCurso;
    private EditText campoTurma;
    private Aluno aluno;
    private AlunoDAO dao = new AlunoDAO();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identificacao_aluno);
        getSupportActionBar().hide(); // tirando appbar

        inicializacaoDosCampos();
        botaoAvancar();
        botaoDesistir();
    }

    private Aluno preencheAluno() {
        String ra = campoRa.getText().toString();
        String nome = campoNomeAluno.getText().toString();
        String curso = campoCurso.getText().toString();
        String turma = campoTurma.getText().toString();

        return new Aluno(ra,nome,curso,turma);
    }

    private void inicializacaoDosCampos() {
        campoRa = findViewById(R.id.activity_identificacao_aluno_ra);
        campoNomeAluno = findViewById(R.id.activity_identificacao_aluno_nome);
        campoCurso = findViewById(R.id.activity_identificacao_aluno_curso);
        campoTurma = findViewById(R.id.activity_identificacao_aluno_turma);
    }

    private void botaoDesistir() {
        Button botaoDesistir = findViewById(R.id.btn_activity_identificacao_aluno_desistir);
        botaoDesistir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void botaoAvancar() {
       Button botaoAvancar = findViewById(R.id.btn_activity_identificacao_aluno_avancar);
       botaoAvancar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(campoRa.getText().toString().equals("")||campoNomeAluno.getText().toString().equals("")||campoCurso.getText().toString().equals("")||campoTurma.getText().toString().equals("")){
                    Toast.makeText(IdentificacaoAlunoActivity.this, "Preecha Todos os Campos", Toast.LENGTH_SHORT).show();
                }else{
                    aluno = preencheAluno();
                    dao.salva(aluno);
                    Intent vaiParaListaDasDisciplinas = new Intent(IdentificacaoAlunoActivity.this, ListaDisciplinasActivity.class);
                    startActivity(vaiParaListaDasDisciplinas);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "Bem Vindo(a) !", Toast.LENGTH_SHORT).show();
    }
}