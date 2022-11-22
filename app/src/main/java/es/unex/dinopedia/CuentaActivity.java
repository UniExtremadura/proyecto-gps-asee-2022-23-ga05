package es.unex.dinopedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import es.unex.dinopedia.roomdb.UsuarioDatabase;

public class CuentaActivity extends AppCompatActivity {

    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        Button bCambiar = findViewById(R.id.bCambiar);
        Button bAyuda = findViewById(R.id.bAyuda);
        Button bContacto = findViewById(R.id.bContactar);
        Button bCerrarSesion = findViewById(R.id.bCerrarSesion);
        EditText eNUsuario = findViewById(R.id.eTUsuario);
        usuario=getIntent().getStringExtra("USUARIO");
        eNUsuario.setText(usuario);

        bCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        UsuarioDatabase database = UsuarioDatabase.getInstance(CuentaActivity.this);
                        Usuario u = new Usuario(database.getDao().getUsuario().getId(), eNUsuario.getText().toString());
                        database.getDao().update(u);
                    }
                });
            }
        });

        bCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        UsuarioDatabase database = UsuarioDatabase.getInstance(CuentaActivity.this);
                        database.getDao().deleteAll();
                    }
                });
                finish();
            }
        });

        bAyuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Remplaza con una acción", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                Intent intent = new Intent(CuentaActivity.this, AyudaActivity.class);
                startActivity(intent);
            }
        });

        bContacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Remplaza con una acción", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                Intent intent = new Intent(CuentaActivity.this, ContactoActivity.class);
                startActivity(intent);
            }
        });
    }

}