package com.example.mies_dinapen.View.Fragment.ReproducirAudio.Adaptador;

import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mies_dinapen.R;
import com.example.mies_dinapen.View.Activity.Activity_Contenedor;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Adaptador_ReproAudio extends RecyclerView.Adapter<Adaptador_ReproAudio.ViewHolder> {

    Activity_Contenedor activityContenedor;
    LayoutInflater layoutInflater;
    MediaPlayer mediaPlayer;

    public Adaptador_ReproAudio(Activity_Contenedor activityContenedor) {
        this.activityContenedor = activityContenedor;
        this.layoutInflater = LayoutInflater.from(activityContenedor);
    }

    @NonNull
    @Override
    public Adaptador_ReproAudio.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_audioslista, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador_ReproAudio.ViewHolder holder, int position) {
        holder.bindata(activityContenedor.getLstA().get(position));
    }

    @Override
    public int getItemCount() {
        return activityContenedor.getLstA().size();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView nombre;

        Button delete, play;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.I_AudioL_TextView_Nombre);
            play = itemView.findViewById(R.id.I_AudioL_Button_Play);
            delete = itemView.findViewById(R.id.I_AudioL_Button_Delete);

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    play.setBackground(activityContenedor.getResources().getDrawable(R.drawable.ic_play_24));
                }
            });
            play.setOnClickListener(this);
            delete.setOnClickListener(this);
        }

        public void bindata(String name) {
            Log.e("TAG", "bindata: " + name );
            Uri uri = Uri.parse(name);
            File file = new File(uri.getPath());
            nombre.setText(file.getName());
        }

        @Override
        public void onClick(View view) {
            if(view == delete){
                activityContenedor.getLstA().remove(getAdapterPosition());
                notifyDataSetChanged();
            }
            if(view == play){
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.reset();
                    play.setBackground(activityContenedor.getResources().getDrawable(R.drawable.ic_play_24));
                } else {
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(
                                activityContenedor
                                ,Uri.parse(activityContenedor.getLstA().get(getAdapterPosition()))
                        );
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                        play.setBackground(activityContenedor.getResources().getDrawable(R.drawable.ic_pause_24));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
