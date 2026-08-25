package com.example.titulacion;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Space;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnE, btnRegistrar;
    Space espacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnE=findViewById(R.id.btnE);
        btnRegistrar=findViewById(R.id.btnRegistrar);
        espacio=findViewById(R.id.espacio);

        coprobar();



        btnE.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);

    }

    private void coprobar() {
        android.content.SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
        String nombreRegistrado = preferences.getString("nombre_guardado", "");
        String contraRegistrada = preferences.getString("contra_guardada", "");
        if (nombreRegistrado!=""){
            if (contraRegistrada!=""){
                btnRegistrar.setVisibility(View.GONE);
                espacio.setVisibility(View.GONE);
            }
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnE){
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_iniciar_s, null);

                int width = LinearLayout.LayoutParams.WRAP_CONTENT;
                int height = LinearLayout.LayoutParams.WRAP_CONTENT;
                boolean focusable = true;
                final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

                popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);

                Button btnCerrar = popupView.findViewById(R.id.btnRegresar);
                Button btnIniciar = popupView.findViewById(R.id.btnIniciar);
                EditText txtNombre = popupView.findViewById(R.id.txtNombre);
                EditText txtContra = popupView.findViewById(R.id.txtContra);



                //btnCerrar.setText(getString(R.string.btnS));


                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

            btnIniciar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nomb = txtNombre.getText().toString();
                    String contra = txtContra.getText().toString();

                    android.content.SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
                    String nombreRegistrado = preferences.getString("nombre_guardado", "");
                    String contraRegistrada = preferences.getString("contra_guardada", "");

                    if (nomb.equals(nombreRegistrado)){
                        if (contra.equals(contraRegistrada)){
                            Intent entrar = new Intent(MainActivity.this, Becas.class);
                            startActivity(entrar);
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Contraseña Incorecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Nombre Incorecto", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
//--------------------------------------------------------------------------------------------------------------------------
        if (v.getId()==R.id.btnRegistrar){

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.activity_registro, null);

            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true;
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 0, 0);

            Button btnRegre = popupView.findViewById(R.id.btnRegre);
            Button btnRegistrar = popupView.findViewById(R.id.btnRegistro);
            EditText txtNomb = popupView.findViewById(R.id.txtNomb);
            EditText txtCon = popupView.findViewById(R.id.txtCon);



            //btnCerrar.setText(getString(R.string.btnS));


            btnRegre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);

                }
            });

            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    if (!txtNomb.getText().toString().isEmpty()){
                        if (!txtCon.getText().toString().isEmpty()){
                            /*nombre = txtNomb.getText().toString();
                            contrasena = txtCon.getText().toString();*/

                            android.content.SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
                            android.content.SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("nombre_guardado", txtNomb.getText().toString());
                            editor.putString("contra_guardada", txtCon.getText().toString());
                            editor.apply();

                            AlertDialog.Builder pregunta = new AlertDialog.Builder(MainActivity.this);
                            pregunta.setTitle("Guardado con exito").setMessage("Deseas iniciar seción")
                                    .setPositiveButton("Si",new DialogInterface.OnClickListener(){
                                        public void onClick(DialogInterface dialogInterface,int i){
                                            Intent entrar = new Intent(MainActivity.this, Becas.class);
                                            startActivity(entrar);
                                            finish();
                                        }
                                    })
                                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.cancel();
                                        }
                                    });
                            pregunta.show();

                        }
                        else {
                            Toast.makeText(MainActivity.this, "Falta Contraseña", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Falta Nombre", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}