package br.com.example.jornadaescolar.model;

import java.io.Serializable;

public class Aluno implements Serializable {

    private String ra;
    private String nomeAluno;
    private String curso;
    private String turma;

    public  Aluno (){
    }

    public Aluno (String ra, String nomeAluno, String curso, String turma){
        this.ra = ra;
        this.nomeAluno = nomeAluno;
        this.curso = curso;
        this.turma = turma;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNomeAluno() {
        return nomeAluno;
    }

    public void setNomeAluno(String nomeAluno) {
        this.nomeAluno = nomeAluno;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        this.turma = turma;
    }
}
