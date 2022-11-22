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

    private String usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuenta);

        Button bCambiar = findViewById(R.id.bCambiar);
        EditText eNUsuario = findViewById(R.id.eTUsuario);
        usuario=getIntent().getStringExtra("USUARIO");
        eNUsuario.setText(usuario);

        Button bCerrarSesion = findViewById(R.id.bCerrarSesion);

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
    }

}