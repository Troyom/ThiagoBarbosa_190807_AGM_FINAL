package com.example.dao;

import com.example.text.model.Personagem;
import java.util.ArrayList;
import java.util.List;




public class PersonagemDAO {

    private final static List<Personagem> personagens= new ArrayList<>();
    private static int contadorDeId=1;//atribuir contador

    public void salva(Personagem personagemSalvo) {//salva item

        personagemSalvo.setId(contadorDeId);//cada personagem salvo add um id

        personagens.add(personagemSalvo);// adciona personagem
        atualizaId();//adciona 1


    }

    private void atualizaId() {
        contadorDeId++;
    }

    public void editar(Personagem personagem){
        Personagem personagemEscolhido = buscaPersonagemId(personagem);
        if (personagemEscolhido!=null) {
            int posicaoDoPersonagem = personagens.indexOf(personagemEscolhido);//posicionamente ideal
            personagens.set(posicaoDoPersonagem, personagem);
        }
    }

    private Personagem buscaPersonagemId(Personagem personagem) {

        for (Personagem p:personagens){ //passar pela listagem
            if (p.getId()==personagem.getId()){
                return p;//armazena e armazenar informaçoes
            }

        }
        return null;
    }

    public  List<Personagem> todos(){

        return new ArrayList<>(personagens);
    }

    public void remove(Personagem personagem){
        Personagem personagemDevolvido = buscaPersonagemId(personagem);
        if(personagemDevolvido !=null){
            personagens.remove(personagemDevolvido);
        }
    }
}
