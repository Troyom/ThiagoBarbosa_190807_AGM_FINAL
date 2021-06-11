package com.example.text.model;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.text.R;
import com.example.dao.PersonagemDAO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.text.model.ConstantesActivities.CHAVE_PERSONAGEM;

public class listaPersonagem extends AppCompatActivity{


    public static final String TITULO_APPBAR = "Formulario de Personagens";

    private final PersonagemDAO dao=new PersonagemDAO();
    private ArrayAdapter<Personagem> adapter;

    //Criando um override para a lista de personagens
    @Override
    protected void onCreate(Bundle savedIntanceState){
        super.onCreate(savedIntanceState);
        setContentView(R.layout.activity_listapersonagem);

        //Setando o nome
        setTitle(TITULO_APPBAR);

        configuraFabNovoPersonagem();
        configuraLista();


    }

    private void configuraFabNovoPersonagem() {
        FloatingActionButton botaoNovoPersonagem=findViewById(R.id.fab_add);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormulario();
            }
        });
    }

    private void abreFormulario() {
        startActivity(new Intent(this, formularioPersonagem.class));
    }

    //fazendo uma proteçao para os dados
    @Override
    protected void onResume() {
        super.onResume();
        atualizaPersonagem();


    }

    private void atualizaPersonagem() {
        adapter.clear();
        adapter.addAll(dao.todos());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        super.onCreateContextMenu(menu, v, menuInfo);
        //menu.add("Remover");
        getMenuInflater().inflate(R.menu.activity_lista_personagens_menu, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        configuraMenu(item);
        return super.onContextItemSelected(item);
    }

    private void configuraMenu(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==R.id.activity_lista_personagem_menu_remover) {

            new AlertDialog.Builder(this).setTitle("Removendo Personagem")
                    .setMessage("Tem certeza que deseja remover?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                            Personagem personagemEscolhido = adapter.getItem(menuInfo.position);
                            remove(personagemEscolhido);

                        }
                    })
                    .setNegativeButton("Não", null)
                    .show();
        }
    }

    private void configuraLista() {
        ListView listaDePersonagens=findViewById(R.id.activity_lista_personagem);
        //referencia ao dao.todos()
        //final List<Personagem> personagens = dao.todos();
        //steando os personagens na lista
        listaDePersonagens(listaDePersonagens);
        configuraItemPorClique(listaDePersonagens);
        registerForContextMenu(listaDePersonagens);
    }

    private void remove(Personagem personagem){
        dao.remove(personagem);
        adapter.remove(personagem);
    }

    private void configuraItemPorClique(ListView listaDePersonagens) {
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            //Metodo para seleçao do personagem
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Personagem personagemEscolhido = (Personagem) adapterView.getItemAtPosition(position);
                abreFormularioEditar(personagemEscolhido);

            }
        });
    }

    private void abreFormularioEditar(Personagem personagemEscolhido) {
        //Entrando no formulario novamente
        Intent vaiParaFormulario = new Intent(listaPersonagem.this, formularioPersonagem.class);
        vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagemEscolhido);
        startActivity(vaiParaFormulario);
    }

    //Cria a lista
    private void listaDePersonagens(ListView listaDePersonagens) {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listaDePersonagens.setAdapter(adapter);
    }
}
