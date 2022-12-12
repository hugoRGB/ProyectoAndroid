/*------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                                CAMPUS LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: AGO-DIC/2022    HORA: 08-09 HRS
:*
:*             Clase que permite la reproducción de video
:*
:*  Archivo     : AcercaDeActivity.java
:*  Autores     : Juan Francisco Barragán Barron 19130891
:*                Javier Arath De La Cerda Martínez 19130900
:*                Hugo René Guerra Barajas 19130917
:*  Fecha       : 13/12/2022
:*  Compilador  : Android Studio Chipmunk | 2021.2.1 Patch 2
:*  Descripción : Clase que muestra un video de los integrantes del equipo
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==========================================================================================
:*
:*------------------------------------------------------------------------------------------*/

package mx.edu.itl.c19130900.u5tomaryguardarfotosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

public class AcercaDeActivity extends AppCompatActivity {

    // Declaración de variables
    private String ruta;
    private VideoView videoView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        // Atributos para la barra de progreso
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Reproducción");
        progressDialog.setMessage("Cargando...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        ruta = "android.resource://" + this.getPackageName() + "/" + R.raw.la_pachanga;
        videoView = findViewById(R.id.videoView);

        // Pasamos la ruta del video
        videoView.setVideoURI(Uri.parse(ruta));
        videoView.setMediaController(new MediaController(this));

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(false);
                videoView.requestFocus();
                progressDialog.dismiss();
                videoView.start();
            }
        });

        // Cuando se acabe el video se cierra
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {finish();}
        });
    }

    // Para pausar el video
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("position", videoView.getCurrentPosition());
        videoView.pause();
    }

    // Para reanudar el video
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        int position= savedInstanceState.getInt("position");
        videoView.seekTo(position);
    }
}