package com.example.titulacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

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
            /*Intent perfil = new Intent(Becas.this, Perfil.class);
            startActivity(perfil);
            finish();*/

            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.activity_perfil, null);

            //int width = LinearLayout.LayoutParams.MATCH_PARENT;
            //int height = LinearLayout.LayoutParams.MATCH_PARENT;
            boolean focusable = true;
            final PopupWindow popupWindow = new PopupWindow(popupView, 900, 2040, focusable);

            popupWindow.setAnimationStyle(R.style.PopupAnimation);

            popupWindow.showAtLocation(findViewById(android.R.id.content), Gravity.CENTER, 90, -200);
        }
    }
}