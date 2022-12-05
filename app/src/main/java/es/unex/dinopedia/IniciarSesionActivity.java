package es.unex.dinopedia;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import es.unex.dinopedia.roomdb.LogroDatabase;
import es.unex.dinopedia.roomdb.UsuarioDatabase;

public class IniciarSesionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        Button bConfirmar = findViewById(R.id.bConfirmar);
        final EditText eDName = findViewById(R.id.eTIniciarSesion);

        bConfirmar.setOnClickListener(view -> {
            AppExecutors.getInstance().diskIO().execute(() -> {
                UsuarioDatabase database = UsuarioDatabase.getInstance(IniciarSesionActivity.this);
                Usuario u = new Usuario();
                u.setName(eDName.getText().toString());
                database.getDao().insert(u);

                LogroDatabase database2 = LogroDatabase.getInstance(IniciarSesionActivity.this);
                Logro l = database2.getDao().getLogro("Inicia Sesión en la aplicación");
                l.setChecked("1");
                database2.getDao().update(l);
            });
            finish();
        });

    }
}