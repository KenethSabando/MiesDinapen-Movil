package com.example.mies_dinapen.Controller.Ubicacion;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mies_dinapen.databinding.FragmentMenuIncidenciaBinding;


public class Localizacion   extends ViewModel{

    MutableLiveData<Double> Latitud;
    MutableLiveData<Double> Longitud;

    public Localizacion() {
        Latitud = new MutableLiveData<>();
        Longitud = new MutableLiveData<>();
        initData();
    }
    private void initVariable(){
        Latitud = new MutableLiveData<>();
        Longitud = new MutableLiveData<>();
    }
    private void initData(){
        Latitud.setValue(new Double(0.00));
        Longitud.setValue(new Double(0.00));
    }

    public LiveData<Double> getLatitud() {
        return Latitud;
    }

    public void setLatitud(Double latitud) {
        Latitud.setValue(latitud);
    }

    public LiveData<Double> getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud.setValue(longitud);
    }

}
