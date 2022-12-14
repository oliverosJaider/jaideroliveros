package com.example.sisben;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class registro extends AppCompatActivity {

    EditText jetidentioficacion, jettipoidentificacion, jetnombre, jetdepartamento, jetmunicipio,
            jetsalario, jetprofesion, jetgrupoSisben;
    CheckBox jcbactivo;
    String identificacion, tipoidentificacion, nombre, departamento, municipio,
            salario, profesion, gruposisben, ident_doc;
    boolean respuesta;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        getSupportActionBar().hide();
        jetidentioficacion = findViewById(R.id.etidentificacion);
        jettipoidentificacion = findViewById(R.id.ettipoidentificacion);
        jetnombre = findViewById(R.id.etnombre);
        jetdepartamento = findViewById(R.id.etdepartamento);
        jetmunicipio = findViewById(R.id.etmunicipio);
        jetsalario = findViewById(R.id.etsalario);
        jetprofesion = findViewById(R.id.etprofesion);
        jetgrupoSisben = findViewById(R.id.etgrupoSisben);
        jcbactivo = findViewById(R.id.cbactivo);

    }

    public void guardar(View View) {
        identificacion = jetidentioficacion.getText().toString();
        tipoidentificacion = jettipoidentificacion.getText().toString();
        nombre = jetnombre.getText().toString();
        departamento = jetdepartamento.getText().toString();
        municipio = jetmunicipio.getText().toString();
        salario = jetsalario.getText().toString();
        profesion = jetprofesion.getText().toString();
        gruposisben = jetgrupoSisben.getText().toString();

        if (identificacion.isEmpty() || tipoidentificacion.isEmpty() || nombre.isEmpty() ||
                departamento.isEmpty() || municipio.isEmpty() || salario.isEmpty() || profesion.isEmpty() ||
                gruposisben.isEmpty()) {
            Toast.makeText(this, "todos los campos son requeridos", Toast.LENGTH_SHORT).show();
            jetidentioficacion.requestFocus();
        } else {

            Map<String, Object> sisben = new HashMap<>();
            sisben.put("identificacion", identificacion);
            sisben.put("tipo identificacion", tipoidentificacion);
            sisben.put("nombre", nombre);
            sisben.put("departamento", departamento);
            sisben.put("municipio", municipio);
            sisben.put("salario", salario);
            sisben.put("profesion", profesion);
            sisben.put("grupo sisben", gruposisben);
            sisben.put("Activo", "si");


            // Add a new document with a generated ID
            db.collection("Registro")
                    .add(sisben)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(registro.this, "registro guardaro", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //  Log.w(TAG, "Error adding document", e);
                            Toast.makeText(registro.this, "erros guardando registro", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        Limpiar_campos();

    }

    public void consultar(View View) {
        identificacion = jetidentioficacion.getText().toString();
        if (identificacion.isEmpty()) {
            Toast.makeText(this, "la identificacion es requeridad", Toast.LENGTH_SHORT).show();
        } else {
            db.collection("Registro")
                    .whereEqualTo("identificacion", identificacion)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    respuesta = true;
                                    if (document.getString("Activo").equals("no")) {
                                        Toast.makeText(registro.this, "El ddocumento existe, sin embargo esta inactivo", Toast.LENGTH_SHORT).show();
                                    }else {
                                            ident_doc = document.getId();
                                            jettipoidentificacion.setText(document.getString("tipo identificacion"));
                                            jetdepartamento.setText(document.getString("departamento"));
                                            jetmunicipio.setText(document.getString("municipio"));
                                            jetprofesion.setText(document.getString("profesion"));
                                            jetgrupoSisben.setText(document.getString("grupo sisben"));
                                            jetnombre.setText(document.getString("nombre"));
                                            jetsalario.setText(document.getString("salario"));
                                        if (document.getString("Activo").equals("si"))
                                            jcbactivo.setChecked(true);
                                        else
                                            jcbactivo.setChecked(false);
                                            // Log.d(TAG, document.getId() + " => " + document.getData());
                                            Toast.makeText(registro.this, "registro encontrado", Toast.LENGTH_SHORT).show();


                                        }
                                    }
                                    } else {
                                        //Log.w(TAG, "Error getting documents.", task.getException());
                                        Toast.makeText(registro.this, "registro no encontrado", Toast.LENGTH_SHORT).show();
                                    }
                            }
                    });
        }
    }

    public void anular(View View) {
        identificacion = jetidentioficacion.getText().toString();
        tipoidentificacion = jettipoidentificacion.getText().toString();
        nombre = jetnombre.getText().toString();
        departamento = jetdepartamento.getText().toString();
        municipio = jetmunicipio.getText().toString();
        salario = jetsalario.getText().toString();
        profesion = jetprofesion.getText().toString();
        gruposisben = jetgrupoSisben.getText().toString();

        if (identificacion.isEmpty() || tipoidentificacion.isEmpty() || nombre.isEmpty() || departamento.isEmpty() || municipio.isEmpty()
                || salario.isEmpty() || profesion.isEmpty() || gruposisben.isEmpty()) {
            Toast.makeText(this, "debe primero consultar", Toast.LENGTH_SHORT).show();
        }
        Map<String, Object> sisben = new HashMap<>();
        sisben.put("identificacion", identificacion);
        sisben.put("tipo identificacion", tipoidentificacion);
        sisben.put("nombre", nombre);
        sisben.put("departamento", departamento);
        sisben.put("municipio", municipio);
        sisben.put("salario", salario);
        sisben.put("profesion", profesion);
        sisben.put("grupo sisben", gruposisben);
        sisben.put("Activo", "no");

        db.collection("Registro").document(ident_doc)
                .set(sisben)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(registro.this, "Documento anulado...", Toast.LENGTH_SHORT).show();
                        Limpiar_campos();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(registro.this, "Error anulando documento...", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void regresar(View View){
        Intent intregistro=new Intent(this,MainActivity.class);
        startActivity(intregistro);
    }

    public void Limpiar_campos(){
        jetidentioficacion.setText("");
        jettipoidentificacion.setText("");
        jetnombre.setText("");
        jetdepartamento.setText("");
        jetmunicipio.setText("");
        jetsalario.setText("");
        jetprofesion.setText("");
        jetgrupoSisben.setText("");
        jcbactivo.setChecked(false);
        respuesta=false;

    }
}