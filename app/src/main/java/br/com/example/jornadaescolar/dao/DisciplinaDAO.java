package br.com.example.jornadaescolar.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.example.jornadaescolar.model.Disciplina;

public class DisciplinaDAO {

    private final static List<Disciplina> disciplinas = new ArrayList<>(); //Lista temporaria Criada (Enquanto o app estiver aberto)
    private static int contadorDeIds = 1;

    public void salva(Disciplina disciplina) {
        disciplina.setIdDisciplina(contadorDeIds);
        disciplinas.add(disciplina); // adiciona o nome da disciplina a lista
        contadorDeIds++;
    }

    public List<Disciplina> todos() {
        return new ArrayList<>(disciplinas); //Retorna uma cópia da lista criada
    }
    public void edita(Disciplina disciplina){
        Disciplina disciplinaEncontrada = buscaDisciplinaPeloId(disciplina);
        //Edita disciplina pela posicao
        if(disciplinaEncontrada != null){ // Se a disiciplina foi realmente encontrada
            int posicaoDaDisciplina = disciplinas.indexOf(disciplinaEncontrada); //pegar posição da disciplina
            disciplinas.set(posicaoDaDisciplina,disciplina);// Editar trocar disciplina antiga pela nova editada
        }
    }

    private Disciplina buscaDisciplinaPeloId(Disciplina disciplina){
        for(Disciplina d : disciplinas){//percorre toda a lista até achar o disciplina 'd'
            if(d.getIdDisciplina() == disciplina.getIdDisciplina()){// se achar ela informar
                return d; // retorna id da disciplina
            }
        }
        return null;
    }

    public void removeDisciplina(Disciplina disciplina){
        Disciplina disciplinaDevolvida = buscaDisciplinaPeloId(disciplina);
        if(disciplinaDevolvida != null){
            disciplinas.remove(disciplinaDevolvida);
        }
    }

}
