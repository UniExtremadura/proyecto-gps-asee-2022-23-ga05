package es.unex.dinopedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import es.unex.dinopedia.roomdb.UsuarioDatabase;

public class CuentaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);
        Button bAyuda = findViewById(R.id.bAyuda);
        Button bContacto = findViewById(R.id.bContactar);

        bAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CuentaActivity.this, AyudaActivity.class);
                startActivity(intent);
            }
        });

        bContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CuentaActivity.this, ContactoActivity.class);
                startActivity(intent);
            }
        });

    }
    @Override
    public void onResume(){
        super.onResume();
    }
}