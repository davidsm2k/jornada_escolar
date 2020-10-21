package br.com.example.jornadaescolar.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Disciplina implements Serializable { //Serializable: Convertendo objetos em bytes e dps em objetos novamente

    private int idDisciplina = 0;
    private String nomeDisciplina;

    public  Disciplina(){

    }

    public Disciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public String getNomeDisciplina() {
        return nomeDisciplina;
    }

    public int getIdDisciplina() {
        return idDisciplina;
    }

    public void setIdDisciplina(int idDisciplina) {
        this.idDisciplina = idDisciplina;
    }

    public void setNomeDisciplina(String nomeDisciplina) {
        this.nomeDisciplina = nomeDisciplina;
    }

    public boolean setIdValido() {
        return idDisciplina > 0;
    }

    @NonNull
    @Override
    public String toString() {
        return nomeDisciplina;
    }
}