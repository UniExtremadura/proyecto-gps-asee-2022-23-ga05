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

        Switch swModo = findViewById(R.id.swModo);



        usuario=getIntent().getStringExtra("USUARIO");
        eNUsuario.setText(usuario);

        bCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        UsuarioDatabase database = UsuarioDatabase.getInstance(CuentaActivity.this);
                        Usuario u = new Usuario(database.getDao().getUsuario().getId(), eNUsuario.getText().toString(), database.getDao().getUsuario().isModo());
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

        swModo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Usuario aux = UsuarioDatabase.getInstance(CuentaActivity.this).getDao().getUsuario();
                        if(aux.isModo()==false){
                            UsuarioDatabase.getInstance(CuentaActivity.this).getDao().updateModoUsuario(aux.getId(), true);
                        }
                        else{
                            UsuarioDatabase.getInstance(CuentaActivity.this).getDao().updateModoUsuario(aux.getId(), false);
                        }
                    }
                });
                if (swModo.isChecked()){
                    CuentaActivity.this.setDayNight(0);
                }
                else{
                    CuentaActivity.this.setDayNight(1);
                }
            }
        });
    }

    public void setDayNight (int modo){
        if (modo == 0){
            getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else{
            getDelegate().setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    public void cambiarSwitch(){
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Switch swModo = findViewById(R.id.swModo);
                Usuario u = UsuarioDatabase.getInstance(CuentaActivity.this).getDao().getUsuario();
                if(u.isModo()){
                    AppExecutors.getInstance().mainThread().execute(()->swModo.setChecked(true));
                }
                else{
                    AppExecutors.getInstance().mainThread().execute(()->swModo.setChecked(false));
                }
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        cambiarSwitch();
    }
}