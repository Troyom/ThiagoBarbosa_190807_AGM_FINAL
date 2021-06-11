package com.example.text.model;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.dao.PersonagemDAO;
import com.example.text.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;


import java.io.Serializable;

import static com.example.text.model.ConstantesActivities.CHAVE_PERSONAGEM;

public class formularioPersonagem extends AppCompatActivity {

    public static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar Personagem";
    public static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";
    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private final PersonagemDAO dao= new PersonagemDAO();
    private Personagem personagem;


    @Override
    //cria o menu para salvar
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Pega o Id e checa para ativar
        int itemId=item.getItemId();
        if (itemId==R.id.activity_formulario_personagem_menu_salvar){
            finalizarFormulario();
        }
        return super.onOptionsItemSelected(item);
    }
    
    //Inicia o formulario
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulariopersonagem);
        inicializacaoCampos();
        configuraBotaoSalva();
        carregaPersonagem();


    }

    private void configuraBotaoSalva() {
        Button botaoSalvar=findViewById(R.id.button_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            private com.example.text.model.Personagem Personagem;
            @Override
            public void onClick(View v) {
                finalizarFormulario();
            }
        });
    }

    private void finalizarFormulario() {
        preencherPersonagem();
        if (personagem.IdValido()){
            dao.editar(personagem);
            finish();

        }else {
            dao.salva(personagem);
        }
        finish();
    }

    private void inicializacaoCampos() {
        //Pegando Ids dos campos
        campoNome= findViewById(R.id.editText_nome);
        campoAltura= findViewById(R.id.editText_altura);
        campoNascimento= findViewById(R.id.editText_nascimento);

        SimpleMaskFormatter smfAltura=new SimpleMaskFormatter("N,NN");
        MaskTextWatcher mtwAltura=new MaskTextWatcher(campoAltura, smfAltura);
        campoAltura.addTextChangedListener(mtwAltura);

        SimpleMaskFormatter smfNascimento=new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher mtwNascimento=new MaskTextWatcher(campoNascimento, smfNascimento);
        campoAltura.addTextChangedListener(mtwNascimento);
    }

    private void carregaPersonagem() {
        //carrega personagens feitos
        Intent dados= getIntent();
        if (dados.hasExtra(CHAVE_PERSONAGEM)){
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagem =(Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();
        }else {
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM  );
            personagem=new Personagem();
        }
    }

    private void preencheCampos() {
        //Preenche campos vazios
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
    }

    private void preencherPersonagem(){

        //Convertendo os dados
        String nome=campoNome.getText().toString();
        String altura=campoAltura.getText().toString();
        String nascimento=campoNascimento.getText().toString();

        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);

    }
}
