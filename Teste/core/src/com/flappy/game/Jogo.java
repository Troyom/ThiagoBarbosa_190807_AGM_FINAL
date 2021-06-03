package com.flappy.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.audio.Sound;

import java.util.Random;

public class Jogo extends ApplicationAdapter {

    private SpriteBatch batch;
	private Texture[] passaro;
	private Texture fundo;
	private Texture canoTopo;
	private Texture canoBaixo;
	private  Texture gameOver;

	private float larguraDispositivo;
	private float alturaDispositivo;


	private int gravidade = 0;
	private int pontos=0;
	private int pontuacaoMaxima=0;
    private int estadoJogo=0;
	private float variacao = 0;
	private float posicaoInicialVerticalPassaro = 0;
    private float posicaoHorizontalPassaro = 0;
	private float posicaoCanoHorizontal;
	private float posicaoCanoVertical;
	private float espaçoEntreCano;

	BitmapFont textoPontuacao;
    BitmapFont textoReiniciar;
    BitmapFont textoMelhorPountuacao;

    Sound somVoando;
    Sound somColisao;
    Sound somPontuacao;

    Preferences preferencias;


	private  boolean passouCano=false;
	private Random random;

	private ShapeRenderer shapeRenderer;
	private Circle circuloPassaro;
	private Rectangle retanguloCanoCima;

	//
	private Rectangle retanguloCanoBaixo;



	@Override
	public void create() {


		inicializarTexturas();
		inicializarObjetos();

	}

	private void inicializarObjetos() {

        random=new Random();

		batch = new SpriteBatch();

		//Ageita a largura da tela
		larguraDispositivo = Gdx.graphics.getWidth();

		//Ageita a altur da tela
		alturaDispositivo= Gdx.graphics.getHeight();

		//Ageita o posicionamento da tela
		posicaoInicialVerticalPassaro = alturaDispositivo / 2;

		posicaoCanoHorizontal =larguraDispositivo;
		espaçoEntreCano=350;

		textoPontuacao=new BitmapFont();
		textoPontuacao.setColor(Color.WHITE);
		textoPontuacao.getData().setScale(10);

        textoReiniciar=new BitmapFont();
        textoReiniciar.setColor(Color.GREEN);
        textoReiniciar.getData().setScale(3);

        textoMelhorPountuacao=new BitmapFont();
        textoMelhorPountuacao.setColor(Color.RED);
        textoMelhorPountuacao.getData().setScale(3);

		shapeRenderer=new ShapeRenderer();
		circuloPassaro=new Circle();
		retanguloCanoCima=new Rectangle();
		retanguloCanoBaixo=new Rectangle();

		somVoando=Gdx.audio.newSound(Gdx.files.internal("som_asa.wav"));
		somColisao=Gdx.audio.newSound(Gdx.files.internal("som_batida.wav"));
		somPontuacao=Gdx.audio.newSound(Gdx.files.internal("som_pontos.wav"));

		preferencias=Gdx.app.getPreferences("FlappyBird");
		pontuacaoMaxima=preferencias.getInteger("pontuacaoMaxima", 0);

	}

	private void inicializarTexturas() {
		//Cria fundo
		fundo = new Texture("fundo.png");

		//Cria sprites de passaro
		passaro = new Texture[3];

		//sprit 1
		passaro[0] = new Texture("passaro1.png");

		//sprit 2
		passaro[1] = new Texture("passaro2.png");

		//sprit 3
		passaro[2] = new Texture("passaro3.png");

		canoBaixo = new Texture("cano baixo maior.png");

		canoTopo = new Texture("cano topo maior.png");

		gameOver=new Texture("game_over.png");

	}

	@Override
	public void render() {

		verificarEstadoJogo();

		desenharTexturas();

		detectarColisao();

		validaPontos();


	}

    private void detectarColisao() {
	    circuloPassaro.set(50+passaro[0].getWidth()/2,
                posicaoInicialVerticalPassaro   + passaro[0].getHeight()/2, passaro[0].getWidth()/2);

	    retanguloCanoBaixo.set(posicaoCanoHorizontal,
                alturaDispositivo/2-canoBaixo.getHeight()-espaçoEntreCano/2+posicaoCanoVertical, canoBaixo.getWidth(),
                canoBaixo.getHeight());

	    retanguloCanoCima.set(posicaoCanoHorizontal,
                alturaDispositivo/2-canoTopo.getHeight()-espaçoEntreCano/2+posicaoCanoVertical,
                canoTopo.getWidth(), canoBaixo.getHeight());

	    boolean bateuCanoCima= Intersector.overlaps(circuloPassaro,retanguloCanoCima);
        boolean bateuCanoBaixo= Intersector.overlaps(circuloPassaro,retanguloCanoBaixo);

        if (bateuCanoBaixo||bateuCanoCima){
            Gdx.app.log("log","bateu");
            if (estadoJogo==1){
                somColisao.play();
                estadoJogo=2;
            }
        }
    }

    private void validaPontos() {
	    if (posicaoCanoHorizontal<50-passaro[0].getWidth()){
            if (!passouCano){
                pontos++;
                passouCano=true;
                somPontuacao.play();
            }
        }
        variacao+=Gdx.graphics.getDeltaTime()*10;
        if(variacao>3)
            variacao=0;

    }

    private void desenharTexturas() {

		batch.begin();

		batch.draw(fundo, 0, 0, larguraDispositivo,alturaDispositivo);
		batch.draw(passaro[(int)variacao], 50+ posicaoHorizontalPassaro,posicaoInicialVerticalPassaro);
		batch.draw(canoBaixo, posicaoCanoHorizontal,
                alturaDispositivo/2-canoBaixo.getHeight()-espaçoEntreCano/2+posicaoCanoVertical);
		batch.draw(canoTopo, posicaoCanoHorizontal, alturaDispositivo/2+espaçoEntreCano/2+posicaoCanoVertical);
		textoPontuacao.draw(batch, String.valueOf(pontos), larguraDispositivo/2, alturaDispositivo-100 );

		if (estadoJogo==2) {
            batch.draw(gameOver, larguraDispositivo / 2-gameOver.getWidth()/2, alturaDispositivo / 2);
            textoReiniciar.draw(batch, "TOQUE NA TELA PARA REINICIAR",
                    larguraDispositivo / 2-200, alturaDispositivo / 2-gameOver.getHeight()/2);
            textoMelhorPountuacao.draw(batch, "SUA MELHOR PONTUAÇÂO É : "+ pontuacaoMaxima+ "PONTOS!",
                    larguraDispositivo / 2-300, alturaDispositivo / 2-gameOver.getHeight()*2);
        }

		batch.end();

	}

	private void verificarEstadoJogo() {

        boolean toqueTela = Gdx.input.justTouched();

	    if(estadoJogo==0){

            if (Gdx.input.justTouched()) {
                gravidade = -15;
                estadoJogo=1;
                somVoando.play();
            }

        } else if (estadoJogo==1){

            if (Gdx.input.justTouched()) {
                gravidade = -15;
                somVoando.play();
            }

            posicaoCanoHorizontal -=Gdx.graphics.getDeltaTime()*200;
            if (posicaoCanoHorizontal <-canoBaixo.getHeight()){
                posicaoCanoHorizontal =larguraDispositivo;
                posicaoCanoVertical=random.nextInt(400)-200;
                passouCano=false;
            }

            //Faz ele pular
            if(posicaoInicialVerticalPassaro>0||toqueTela)
                posicaoInicialVerticalPassaro=posicaoInicialVerticalPassaro-gravidade;

            gravidade++;

        }else if (estadoJogo==2){

	        if (pontos>pontuacaoMaxima){
	            pontuacaoMaxima=pontos;
	            preferencias.putInteger("pontuacaoMaxima", pontuacaoMaxima);
            }

            posicaoHorizontalPassaro-=Gdx.graphics.getDeltaTime()*500;

	        if (toqueTela){
	            estadoJogo=0;
	            pontos=0;
	            gravidade=0;
	            posicaoHorizontalPassaro =0;
	            posicaoInicialVerticalPassaro=alturaDispositivo/2;
	            posicaoCanoHorizontal=larguraDispositivo;
            }

        }

    }

	@Override
	public void dispose () {

	}
}
