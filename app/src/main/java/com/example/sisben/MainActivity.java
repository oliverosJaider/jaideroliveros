package com.example.sisben;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText jetidentioficacion, jettipoidentificacion, jetnombre, jetdepartamento, jetmunicipio,jetsalario,jetprofesion,jetgrupoSisben;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}