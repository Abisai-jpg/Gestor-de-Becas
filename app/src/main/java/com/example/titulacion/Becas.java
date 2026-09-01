package com.example.titulacion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.nio.channels.ScatteringByteChannel;

public class Becas extends AppCompatActivity implements View.OnClickListener {

    ImageButton btn1, btnPerfil;
    private ImageView imgPerfilActual;
    private ActivityResultLauncher<String> seleccionarImagenLauncher;


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
        btn1 = findViewById(R.id.btnR1);
        btnPerfil = findViewById(R.id.btnPerfil);


        btn1.setOnClickListener(this);
        btnPerfil.setOnClickListener(this);

        seleccionarImagenLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                new ActivityResultCallback<Uri>() {
                    @Override
                    public void onActivityResult(Uri uri) {
                        if (uri != null && imgPerfilActual != null) {

                            try {
                                final int takeFlags = Intent.FLAG_GRANT_READ_URI_PERMISSION;
                                getContentResolver().takePersistableUriPermission(uri, takeFlags);
                            } catch (Exception e) {
                                Toast.makeText(Becas.this, "Error:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            // Muestra la imagen seleccionada directamente en el ImageView del popup
                            imgPerfilActual.setImageURI(uri);

                            // Guarda la ruta real de la imagen seleccionada
                            SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("imagen_guardada", uri.toString());
                            editor.apply();
                        }
                    }
                }
        );
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.tool);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_i, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.barraB){

            buscar();
        }
        if(item.getItemId() == R.id.itemSalir){
            Intent contacto = new Intent(Becas.this, MainActivity.class);
            startActivity(contacto);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void buscar() {

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnR1) {
            Intent regresarM = new Intent(Becas.this, MainActivity.class);
            startActivity(regresarM);
            finish();
        }

        if (v.getId() == R.id.btnPerfil) {
            try {


                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                View popupView = inflater.inflate(R.layout.activity_perfil, null);

                android.util.DisplayMetrics displayMetrics = new android.util.DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int screenWidth = displayMetrics.widthPixels;
                int screenHeight = displayMetrics.heightPixels;

                int popupWidth = (int) (screenWidth * 0.85);
                int popupHeight = (int) (screenHeight * 0.83);

                boolean focusable = true;

                final PopupWindow popupWindow = new PopupWindow(popupView, popupWidth, popupHeight, focusable);

                popupWindow.setAnimationStyle(R.style.PopupAnimation);
                popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 90, -80);

                ImageButton btnEdit = popupView.findViewById(R.id.btnEdit);
                ImageButton btnGuardar = popupView.findViewById(R.id.btnGuardar);
                ImageButton btnIcono = popupView.findViewById(R.id.btnIcono);
                ImageView imgPerfil = popupView.findViewById(R.id.imgPerfil);
                TextView lblNombre = popupView.findViewById(R.id.lblNombre);
                TextView lblEdad = popupView.findViewById(R.id.lblEdad);
                TextView lblGene = popupView.findViewById(R.id.lblGene);
                EditText txtEdad = popupView.findViewById(R.id.txtEdad);
                Spinner spGene = popupView.findViewById(R.id.spGene);

                imgPerfilActual = imgPerfil;

                // ÚNICA declaración correcta del botón para abrir la galería
                btnIcono.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        seleccionarImagenLauncher.launch("image/*");
                    }
                });

                SharedPreferences preferences = getSharedPreferences("credenciales", MODE_PRIVATE);
                String edadRegistrada = preferences.getString("edad_guardada", "");
                String generoRegistrada = preferences.getString("genero_guardado", "");
                String imgRegistrada = preferences.getString("imagen_guardada", "");
                String nombreRegistrado = preferences.getString("nombre_guardado", "");

                lblEdad.setText(edadRegistrada);
                lblGene.setText(generoRegistrada);
                lblNombre.setText(nombreRegistrado);

                if (!imgRegistrada.equals("")) {
                    imgPerfil.setImageURI(Uri.parse(imgRegistrada));
                }



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
                        int ubiRegistrada = preferences.getInt("ubisp_guardado", 0);

                        txtEdad.setText(edadRegistrada);
                        spGene.setSelection(ubiRegistrada);

                        btnGuardar.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
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

                                // Ocultar teclado virtual
                                android.view.inputmethod.InputMethodManager imm = (android.view.inputmethod.InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(txtEdad.getWindowToken(), 0);

                                String edadRegistrada = preferences.getString("edad_guardada", "");
                                String generoRegistrada = preferences.getString("genero_guardado", "");

                                lblEdad.setText(edadRegistrada);
                                lblGene.setText(generoRegistrada);
                            }
                        });
                    }
                });
            } catch (Exception e) {
                Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


}