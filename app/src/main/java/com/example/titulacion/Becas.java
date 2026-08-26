package com.example.titulacion;

import android.content.ClipData;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Becas extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn1,btnPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_becas);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        btn1=findViewById(R.id.btnR1);
        btnPerfil=findViewById(R.id.btnPerfil);

        btn1.setOnClickListener(this);
        btnPerfil.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.btnR1){
            Intent regresarM = new Intent(Becas.this,MainActivity.class);
            startActivity(regresarM);
            finish();
        }

        if (v.getId()==R.id.btnPerfil){

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.activity_perfil, null);

// 1. Obtenemos las medidas de la pantalla del dispositivo actual
            android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int screenWidth = displayMetrics.widthPixels;
            int screenHeight = displayMetrics.heightPixels;

// 2. Definimos el tamaño del popup en base a porcentajes de la pantalla
// Ejemplo: 85% del ancho de la pantalla y 75% del alto
            int popupWidth = (int) (screenWidth * 0.85);
            int popupHeight = (int) (screenHeight * 0.9);

            boolean focusable = true;
// 3. Creamos el PopupWindow usando las medidas dinámicas calculadas
            final PopupWindow popupWindow = new PopupWindow(popupView, popupWidth, popupHeight, focusable);

// Aplicamos tu animación de entrada/salida
            popupWindow.setAnimationStyle(R.style.PopupAnimation);

// Lo mostramos centrado en la pantalla
            popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 90, -60);



            ImageButton btnEdit = popupView.findViewById(R.id.btnEdit);
            ImageButton btnGuardar = popupView.findViewById(R.id.btnGuardar);
            ImageButton btnIcono = popupView.findViewById(R.id.btnIcono);
            TextView lblEdad = popupView.findViewById(R.id.lblEdad);
            TextView lblGene = popupView.findViewById(R.id.lblGene);
            EditText txtEdad = popupView.findViewById(R.id.txtEdad);
            Spinner spGene = popupView.findViewById(R.id.spGene);

            SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
            String edadRegistrada = preferences.getString("edad_guardada", "");
            String generoRegistrada = preferences.getString("genero_guardado", "");

            lblEdad.setText(edadRegistrada);
            lblGene.setText(generoRegistrada);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnEdit.setVisibility(View.GONE);
                    lblEdad.setVisibility(View.GONE);
                    lblGene.setVisibility(View.GONE);
                    btnGuardar.setVisibility(View.VISIBLE);
                    txtEdad.setVisibility(View.VISIBLE);
                    spGene.setVisibility(View.VISIBLE);

                    SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
                    String edadRegistrada = preferences.getString("edad_guardada", "");
                    //String generoRegistrada = preferences.getString("genero_guardado", "");
                    int ubiRegistrada = preferences.getInt("ubisp_guardado", 0);

                    txtEdad.setText(edadRegistrada);
                    spGene.setSelection(ubiRegistrada);

                    btnGuardar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            /*SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
                            String edadRegistrada = preferences.getString("edad_guardada", "");
                            String generoRegistrada = preferences.getString("genero_guardada", "");*/
                            SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("edad_guardada", txtEdad.getText().toString());
                            editor.putString("genero_guardado", spGene.getSelectedItem().toString());
                            editor.putInt("ubisp_guardado", spGene.getSelectedItemPosition());
                            editor.apply();

                            btnEdit.setVisibility(View.VISIBLE);
                            lblEdad.setVisibility(View.VISIBLE);
                            lblGene.setVisibility(View.VISIBLE);
                            btnGuardar.setVisibility(View.GONE);
                            txtEdad.setVisibility(View.GONE);
                            spGene.setVisibility(View.GONE);

                            //Esta instruccion sirve para ocultar el teclado virtual cuando se presione el boton de gusrdar
                            android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(txtEdad.getWindowToken(), 0);

                            String edadRegistrada = preferences.getString("edad_guardada", "");
                            String generoRegistrada = preferences.getString("genero_guardado", "");
                            //int ubisp_guardado = preferences.getString("ubisp_guardado", "");

                            lblEdad.setText(edadRegistrada);
                            lblGene.setText(generoRegistrada);
                        }
                    });
                }
            });

            btnIcono.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
        }
    }
}