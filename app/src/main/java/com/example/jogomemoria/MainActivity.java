package com.example.jogomemoria;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView curView = null;
    ImageView showView;
    private int countPair = 0;
    final int[] imagens = new int[]{R.drawable.img_1,R.drawable.img_2,R.drawable.img_3,R.drawable.img_4,
            R.drawable.img_5,R.drawable.img_6,R.drawable.img_7,R.drawable.img_8};
    int[] pos = new int[]{-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
    int posAtual = -1;
    private int aleatorio;
    private int e = 0;
    private int i = 0;
    private int imgNum = 0;
    private ArrayList<Integer> placeHold = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        posImag();

        //cria a gridView para colocar as imagens
        GridView gridView = (GridView)findViewById(R.id.gridView);
        ImageAdapter imageAdapter = new ImageAdapter(this);
        gridView.setAdapter(imageAdapter);

        /*View getView(int )
        while (i<16){
            showView = gridView.get;
            ((ImageView)).setImageResource(imagens[pos[i]]);

        }*/


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //mostra a imagem na posição de clicada
                if (!placeHold.contains(pos[position])) {
                    if (posAtual < 0) {
                        posAtual = position;
                        curView = (ImageView) view;
                        ((ImageView) view).setImageResource(imagens[pos[position]]);
                    } else {
                        //se clicar na mesma posição o jogo executa um rollback para a jogada anterior
                        if (posAtual == position) {
                            ((ImageView) view).setImageResource(R.drawable.imgcapa);
                            posAtual = -1;
                            Toast.makeText(getApplicationContext(), "Têm de clicar numa imagem diferente da 1ª", Toast.LENGTH_SHORT).show();
                        }

                        //verifica se as imagens são iguais
                        else {
                            if (pos[posAtual] != pos[position]) {
                                curView.setImageResource(R.drawable.imgcapa);
                                Toast.makeText(getApplicationContext(), "Não é igual!", Toast.LENGTH_SHORT).show();
                            } else {
                                ((ImageView) view).setImageResource(imagens[pos[position]]);
                                countPair++;
                                Toast.makeText(getApplicationContext(), "Imagens iguais!", Toast.LENGTH_SHORT).show();
                                placeHold.add(pos[position]);

                                if (countPair == 8) {
                                    Toast.makeText(getApplicationContext(), "Vitoria!", Toast.LENGTH_SHORT).show();
                                    finalJogo();
                                }
                            }

                            posAtual = -1;
                        }
                    }
                }
            }
        });

    }

    private void finalJogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ganhou, deseja jogar outra vez?");
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        builder.setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
             }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //função de ordenação aleatoria das imagens no inicio do jogo
    private void posImag() {
        while (imgNum < 8) {
            Random random = new Random();
            aleatorio = random.nextInt(16);

            if (pos[aleatorio] == -1){
                pos[aleatorio] = imgNum;
                e++;
                if (e==2){
                    imgNum++;
                    e=0;
                }
            }
        }
    }
}
