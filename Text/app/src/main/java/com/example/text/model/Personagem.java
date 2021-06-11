package com.example.text.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Personagem implements Serializable {

    //Pegando as variaveis
    private  String nome;
    private  String altura;
    private  String nascimento;
    private  int id=0;

    public Personagem(String nome, String altura, String nascimento) {

        //Setando as variaveis
        this.nome = nome;
        this.altura = altura;
        this.nascimento = nascimento;
    }

    public  Personagem() {

    }

    @NonNull
    @Override
    public String toString(){return nome;}

    public String getNome(){ return nome;}

    public void setNome(String nome){ this.nome=nome;
    }

    public void setAltura(String altura) {
        this.altura=altura;
    }

    public String getAltura() {
        return altura;
    }

    public void setNascimento(String nascimento) {
        this.nascimento=nascimento;
    }
    public String getNascimento() {
        return nascimento;
    }

    public int getId(   ) {
        return id;
    }
    public void setId(int id) {
        this.id=id;
    }

    public boolean IdValido() {
        return id>0;
    }

}

   //public boolean IdValido(){return id>0;}}
