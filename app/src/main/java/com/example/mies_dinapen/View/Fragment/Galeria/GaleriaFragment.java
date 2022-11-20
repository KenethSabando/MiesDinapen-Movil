package com.example.mies_dinapen.View.Fragment.Galeria;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.BitmapFactory;
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

import com.example.mies_dinapen.R;
import com.example.mies_dinapen.View.Activity.Activity_Contenedor;
import com.example.mies_dinapen.View.Fragment.Galeria.Adaptador.Adaptador_Galeria;
import com.example.mies_dinapen.View.Fragment.ReproducirAudio.Adaptador.Adaptador_ReproAudio;
import com.example.mies_dinapen.databinding.FragmentGaleriaBinding;
import com.example.mies_dinapen.databinding.FragmentReproducirAudioBinding;

public class GaleriaFragment extends Fragment implements View.OnClickListener{

    FragmentGaleriaBinding viewMain;
    Adaptador_Galeria adaptador_galeria;
    Activity_Contenedor activity;

    public GaleriaFragment() {
        // Required empty public constructor
    }

    public static GaleriaFragment newInstance(String param1, String param2) {
        GaleriaFragment fragment = new GaleriaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewMain = FragmentGaleriaBinding.inflate(getLayoutInflater());
        activity = (Activity_Contenedor) getActivity();
        adaptador_galeria = new Adaptador_Galeria((Activity_Contenedor) getActivity());
        viewMain.FGaleriaRecyclerVGaleria.setAdapter(adaptador_galeria);
        viewMain.FGaleriaRecyclerVGaleria.setHasFixedSize(true);

        viewMain.FGaleriaButtonAgregar.setOnClickListener(this);
        return viewMain.getRoot();
    }


    @Override
    public void onClick(View view) {
        if(view == viewMain.FGaleriaButtonAgregar){
            String fotoDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath() + "/Mies-Dinapen/";
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setDataAndType(Uri.parse(fotoDir),"image/*");
            galerialauncher.launch(intent);
        }
    }

    ActivityResultLauncher<Intent> galerialauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        activity.getLstF().add(result.getData().getData().toString());
                        adaptador_galeria.notifyDataSetChanged();
                    }
                }
            });
}