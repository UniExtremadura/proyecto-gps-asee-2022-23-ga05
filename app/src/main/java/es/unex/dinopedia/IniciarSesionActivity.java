package es.unex.dinopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import es.unex.dinopedia.roomdb.DinosaurioDatabase;
import es.unex.dinopedia.roomdb.UsuarioDatabase;

public class IniciarSesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        Button bResponder = findViewById(R.id.bConfirmar);
        final EditText eDName = findViewById(R.id.eTIniciarSesion);

        bResponder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        UsuarioDatabase database = UsuarioDatabase.getInstance(IniciarSesionActivity.this);
                        Usuario u = new Usuario();
                        u.setName(eDName.getText().toString());
                        database.getDao().insert(u);
                    }
                });
                finish();
            }
        });

    }
}