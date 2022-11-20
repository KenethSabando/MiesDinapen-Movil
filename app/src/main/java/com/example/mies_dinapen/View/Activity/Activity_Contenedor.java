package com.example.mies_dinapen.View.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.mies_dinapen.Controller.Retrofit.Retrofit;
import com.example.mies_dinapen.Controller.Ubicacion.Localizacion;
import com.example.mies_dinapen.Model.Operador;
import com.example.mies_dinapen.R;
import com.example.mies_dinapen.databinding.ActivityContendorBinding;
import com.example.mies_dinapen.service.Servicios;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Activity_Contenedor extends AppCompatActivity  {

    ActivityContendorBinding viewMain;
    Servicios servicios;

    ArrayList<String> lstA , lstF;
    Operador operador;

    Localizacion mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_MiesDinapen);

        super.onCreate(savedInstanceState);
        viewMain = ActivityContendorBinding.inflate(getLayoutInflater());
        setContentView(viewMain.getRoot());
        mViewModel = new ViewModelProvider(this).get(Localizacion.class);
        IniciarLocation();
        this.getPermises();
        this.initdata();

    }

    private void initdata() {
        lstA = new ArrayList<>();
        lstF = new ArrayList<>();
        operador = new Operador();
        servicios = Retrofit.getConnetion().create(Servicios.class);
    }

    public Servicios getServicios() {
        return servicios;
    }

    public void getPermises() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.RECORD_AUDIO
                }, PackageManager.PERMISSION_GRANTED);
    }

    public Localizacion getmViewModel() {
        return mViewModel;
    }

    public ArrayList<String> getLstA() {
        return lstA;
    }

    public void setLstA(ArrayList<String> lstA) {
        this.lstA = lstA;
    }

    public ArrayList<String> getLstF() {
        return lstF;
    }

    public void setLstF(ArrayList<String> lstF) {
        this.lstF = lstF;
    }

    public Operador getOperador() {
        return operador;
    }

    public void setOperador(Operador operador) {
        this.operador = operador;
    }

    private void IniciarLocation() {
        Ubicacion ubicacion = new Ubicacion();
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(settingsIntent);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,}, 1000);
            return;
        }
        mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, (LocationListener) ubicacion);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) ubicacion);
    }

    public class Ubicacion implements LocationListener{

        @Override
        public void onLocationChanged(Location loc) {
            loc.getLatitude();
            loc.getLongitude();
            mViewModel.setLatitud(loc.getLatitude());
            mViewModel.setLongitud(loc.getLongitude());
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText( getApplicationContext(), "El provedor de Ubicacion esta deshabilitado ", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(getApplicationContext(), "El provedor de Ubicacion esta habilitado ", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.d("debug", "LocationProvider.AVAILABLE");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.d("debug", "LocationProvider.OUT_OF_SERVICE");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.d("debug", "LocationProvider.TEMPORARILY_UNAVAILABLE");
                    break;
            }
        }

    }




}