package com.example.titulacion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Space;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnE, btnRegistrar;
    Space espacio;
    Boolean na = null;

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

        btnE=findViewById(R.id.btnIni);
        btnRegistrar=findViewById(R.id.btnRegistrar);
        espacio=findViewById(R.id.espacio);

        comprobar();



        btnE.setOnClickListener(this);
        btnRegistrar.setOnClickListener(this);

    }

    //-----------------------------------------------------------------------------------------------------------
    //Este metodo sirve para detectar si ya esta iniciada secion y ocultar el boton de Registrar
    private void comprobar() {

        //Este es una forma de almacenar la informacion local, funciona incluso si se destruye la actividad o se cierra la app
        //Esto es Provicional, solo para provar si funciona el codigo base, despues se cambiara en base a lo que requiera el firebase
        SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
        String nombreRegistrado = preferences.getString("nombre_guardado", "");
        String contraRegistrada = preferences.getString("contra_guardada", "");
        if (nombreRegistrado!=""){
            if (contraRegistrada!=""){
                btnRegistrar.setVisibility(View.GONE);
                espacio.setVisibility(View.GONE);
                //se utiliza una variable boolean para saber su ya se inicio seción
                na=true;
            }
        }
    }


    @Override
    public void onClick(View v) {
        //------------------------------------------------------------------------------------------------------------------------------
        //Boton de Iniciar seción
        if (v.getId()==R.id.btnIni){

                //Estas lineas de codigo sirven para establecer la coneccion con las herramientas de popup (Pantalla emergente)
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
                TextView lblNi = popupView.findViewById(R.id.lblNi);

//Aqui esta otro sistema de guradado, es un duplicado ya que funciona de forma local
                SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
                String nombreRegistrado = preferences.getString("nombre_guardado", "");
                String contraRegistrada = preferences.getString("contra_guardada", "");

                txtNombre.setVisibility(View.VISIBLE);
                lblNi.setVisibility(View.GONE);

                //este codigo sirve para detectar si ya se registro para ocultar el editText y mostrar el textView con el nombre del usuario
                if (na==true){
                    txtNombre.setVisibility(View.GONE);
                    lblNi.setVisibility(View.VISIBLE);
                    lblNi.setText(nombreRegistrado);
                }



                //Boton del popup
                btnCerrar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();

                        Intent intent = new Intent(v.getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

                //Otro boton del popup para Iniciar secion
            btnIniciar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String nomb = txtNombre.getText().toString();
                    String contra = txtContra.getText().toString();

                    //Aqui hay otro, sirve para leer las credenciales
                    SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
                    String nombreRegistrado = preferences.getString("nombre_guardado", "");
                    String contraRegistrada = preferences.getString("contra_guardada", "");

                    //Una condicion para saber si ya se inicio secion
                    if (na==true){
                        //condiciones para saber si coincide la contraseña
                        if (contra.equals(contraRegistrada)){
                            Intent entrar = new Intent(MainActivity.this, Becas.class);
                            startActivity(entrar);
                            finish();
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Contraseña Incorecta", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        //en caso de que no, estan las condiciones completas
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

                }
            });

        }
//--------------------------------------------------------------------------------------------------------------------------
//Boton de Registro
        if (v.getId()==R.id.btnRegistrar){

            //codigo del popup
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


            //boton del popup para regresar
            btnRegre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();

                    Intent intent = new Intent(v.getContext(), MainActivity.class);
                    startActivity(intent);

                }
            });

            //boton del popup para registrar
            btnRegistrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //condiciones para saber si estan llenos los editText
                    if (!txtNomb.getText().toString().isEmpty()){
                        if (!txtCon.getText().toString().isEmpty()){

                            //Codigo para leer los editText y guradarlos en credenciales
                            SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("nombre_guardado", txtNomb.getText().toString());
                            editor.putString("contra_guardada", txtCon.getText().toString());
                            editor.apply();

                            //Pregunta para saber si decide iniciar secion directamente o no (la info esta guardada)
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