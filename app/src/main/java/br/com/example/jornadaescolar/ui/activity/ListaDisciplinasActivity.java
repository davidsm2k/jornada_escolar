package br.com.example.jornadaescolar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.com.example.jornadaescolar.R;
import br.com.example.jornadaescolar.dao.AlunoDAO;
import br.com.example.jornadaescolar.dao.DisciplinaDAO;
import br.com.example.jornadaescolar.model.Aluno;
import br.com.example.jornadaescolar.model.Disciplina;

public class ListaDisciplinasActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_LISTA_DISCIPLINAS = "LISTA DAS DISCIPLINAS";
    private final DisciplinaDAO daoDisciplina = new DisciplinaDAO();
    private final AlunoDAO daoAluno = new AlunoDAO();
    private TextView campoCurso;
    private List<Aluno> listaAluno;
    private TextView campoNome;
    private TextView campoRA;
    private TextView campoTurma;
    private ArrayAdapter<Disciplina> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_disciplinas);
        setTitle(TITULO_APPBAR_LISTA_DISCIPLINAS);

        configuraLista();
        configuraFabNovaDisciplina();
        inicializandoCampos();
        preenchendoDadosDoAluno();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.activity_lista_disciplinas_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int idMenu = item.getItemId();

        if(idMenu == R.id.activity_lista_disciplina_menu_remover){
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Disciplina disciplinaEscolhida = adapter.getItem(menuInfo.position);
            deletaDisciplina(disciplinaEscolhida);
        } else if(idMenu == R.id.activity_lista_disciplina_menu_editar){
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            Disciplina disciplinaEscolhida = adapter.getItem(menuInfo.position);
            Intent vaiParaNovaDisciplina = new Intent(this, NovaDisciplinaActivity.class);
            vaiParaNovaDisciplina.putExtra("nomeDisciplina", disciplinaEscolhida);
            startActivity(vaiParaNovaDisciplina);
        }
        return super.onContextItemSelected(item);
    }

    private void deletaDisciplina(Disciplina disciplina) {
        daoDisciplina.removeDisciplina(disciplina);
        adapter.remove(disciplina);
    }

    private void preenchendoDadosDoAluno() {
        //Puxando informação do único aluno cadastrado na lista
        listaAluno = daoAluno.todos();

        String ra = null;
        String nomeAluno = null;
        String curso = null;
        String turma = null;

        for(Aluno aluno : listaAluno){
            ra = aluno.getRa();
            nomeAluno = aluno.getNomeAluno();
            curso = aluno.getCurso();
            turma = aluno.getTurma();
        }

        //Exibindo infomações puxadas
        campoRA.setText("RA: " + ra);
        campoNome.setText("Nome: " + nomeAluno);
        campoCurso.setText("Curso: " + curso);
        campoTurma.setText("Turma: " + turma);
    }

    private void inicializandoCampos() {
        campoRA = findViewById(R.id.txt_activity_lista_disciplina_ra);
        campoNome = findViewById(R.id.txt_activity_lista_disciplina_nome);
        campoCurso = findViewById(R.id.txt_activity_lista_disciplina_curso);
        campoTurma = findViewById(R.id.txt_activity_lista_disciplina_turma);
    }

    @Override
    protected void onResume() { // Executados após ficar em segundo plano
        super.onResume();
        atualizarDisciplinas();
    }

    private void atualizarDisciplinas() {
        adapter.clear(); // limpa lista antiga q estava visivel
        adapter.addAll(daoDisciplina.todos()); // apresenta lista atualizada adicionando sua ela novamente
    }

    private void configuraFabNovaDisciplina() {
        FloatingActionButton botaoNovaDisciplina = findViewById(R.id.fab_activity_lista_nova_disciplina);
        botaoNovaDisciplina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abreTelaNovaDisciplina();
            }
        });
    }

    private void abreTelaNovaDisciplina() {
        startActivity(new Intent(ListaDisciplinasActivity.this, NovaDisciplinaActivity.class));
    }

    private void configuraLista() {
        //Abrir Lista Atualizada
        ListView listaDisciplinas = findViewById(R.id.activity_lista_disciplina_listview); //Pegando a lista em uma variavel

        //Puxando copia de lista com os dados cadastrados
        configuraAdapter(listaDisciplinas);
        configuraListenerCliquePorItem(listaDisciplinas);
        registerForContextMenu(listaDisciplinas);
    }

    private void configuraAdapter(ListView listaDisciplinas) {
        adapter = new ArrayAdapter<>(ListaDisciplinasActivity.this, android.R.layout.simple_list_item_1);
        listaDisciplinas.setAdapter(adapter);
    }

    private void configuraListenerCliquePorItem(ListView listaDisciplinas) {
        listaDisciplinas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                Disciplina disciplinaEscolhida = (Disciplina) adapterView.getItemAtPosition(posicao);
                Intent vaiParaTelaCalculaNota = new Intent(ListaDisciplinasActivity.this, CalculaNotaActivity.class);
                vaiParaTelaCalculaNota.putExtra("disciplina", disciplinaEscolhida);
                startActivity(vaiParaTelaCalculaNota);// abrir tela

            }
        });
    }
}