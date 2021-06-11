package com.example.text.model;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Personagem implements Serializable {

    //Pegando as variaveis
    private  String nome;
    private  String altura;
    private  String nascimento;
    private  String rg;
    private  String cep;
    private  String genero;
    private  String phone;
    private  int id=0;

    public Personagem(String nome, String altura, String nascimento, String rg, String cep, String genero, String phone) {

        //Setando as variaveis
        this.nome = nome;
        this.altura = altura;
        this.nascimento = nascimento;
        this.rg = rg;
        this.cep = cep;
        this.genero = genero;
        this.phone = phone;
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

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

   //public boolean IdValido(){return id>0;}}
