package com.example.mies_dinapen.View.Fragment.ReproducirAudio;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mies_dinapen.View.Activity.Activity_Contenedor;
import com.example.mies_dinapen.View.Fragment.ReproducirAudio.Adaptador.Adaptador_ReproAudio;
import com.example.mies_dinapen.databinding.FragmentReproducirAudioBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReproducirAudioFragment extends Fragment implements View.OnClickListener {

    FragmentReproducirAudioBinding viewMain;
    Adaptador_ReproAudio adaptador_reproAudio;
    Activity_Contenedor activity;

    public ReproducirAudioFragment() {
    }

    public static ReproducirAudioFragment newInstance(String param1, String param2) {
        ReproducirAudioFragment fragment = new ReproducirAudioFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewMain = FragmentReproducirAudioBinding.inflate(getLayoutInflater());
        activity = (Activity_Contenedor) getActivity();
        adaptador_reproAudio = new Adaptador_ReproAudio((Activity_Contenedor) getActivity());
        viewMain.FConsultarCRecyclerVHinci.setAdapter(adaptador_reproAudio);
        viewMain.FConsultarCRecyclerVHinci.setHasFixedSize(true);
        viewMain.FConsultarCRecyclerVHinci.setLayoutManager(new LinearLayoutManager(getContext()));

        viewMain.FConsultarAButtonAgregar.setOnClickListener(this);

        return viewMain.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adaptador_reproAudio.setMediaPlayer(new MediaPlayer());
    }

    @Override
    public void onClick(View view) {
        if(view == viewMain.FConsultarAButtonAgregar){
            if(adaptador_reproAudio.getMediaPlayer()!= null){
                adaptador_reproAudio.getMediaPlayer().stop();
            }
            String audioDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS).getPath() + "/Mies-Dinapen/";
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(Uri.parse(audioDir),"audio/*");
            reproductorlauncher.launch(intent);
        }
    }

    ActivityResultLauncher<Intent> reproductorlauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        String path = result.getData().getData().toString();
                        Pattern  pattern = Pattern.compile("\\w{7}[:][/]{2}\\w{5}[/]\\w{8}[/]\\w{5}[/]");
                        if(pattern.matcher(path).find()){
                            activity.getLstA().add(getRealPathFromURI(Uri.parse(result.getData().getData().toString())));
                        }else{
                            activity.getLstA().add(result.getData().getData().toString());
                        }
                        adaptador_reproAudio.notifyDataSetChanged();
                    }
                }
            });

    public String getRealPathFromURI(Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Audio.Media.DATA};
            cursor = getContext().getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}