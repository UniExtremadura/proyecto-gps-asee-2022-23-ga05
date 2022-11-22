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
        Button bCerrarSesion = findViewById(R.id.bCerrarSesion);
        Button bAyuda = findViewById(R.id.bAyuda);
        Button bContacto = findViewById(R.id.bContactar);
        EditText eNUsuario = findViewById(R.id.eTUsuario);

        Switch swInfoDino = findViewById(R.id.sInfoDino);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Usuario u = UsuarioDatabase.getInstance(CuentaActivity.this).getDao().getUsuario();
                if(u!=null) {
                    if (u.isInfoDino()) {
                        AppExecutors.getInstance().mainThread().execute(() -> swInfoDino.setChecked(true));
                    } else {
                        AppExecutors.getInstance().mainThread().execute(() -> swInfoDino.setChecked(false));
                    }
                }
            }
        });

        usuario=getIntent().getStringExtra("USUARIO");
        eNUsuario.setText(usuario);

        bCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        UsuarioDatabase database = UsuarioDatabase.getInstance(CuentaActivity.this);
                        Usuario u = new Usuario(database.getDao().getUsuario().getId(), eNUsuario.getText().toString(), database.getDao().getUsuario().isModo(), database.getDao().getUsuario().isInfoDino());
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

        swInfoDino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(swInfoDino.isChecked()){
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Usuario u = UsuarioDatabase.getInstance(CuentaActivity.this).getDao().getUsuario();
                            u.setInfoDino(true);
                            UsuarioDatabase.getInstance(CuentaActivity.this).getDao().update(u);
                        }
                    });
                }
                else{
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            Usuario u = UsuarioDatabase.getInstance(CuentaActivity.this).getDao().getUsuario();
                            u.setInfoDino(false);
                            UsuarioDatabase.getInstance(CuentaActivity.this).getDao().update(u);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
    }
}