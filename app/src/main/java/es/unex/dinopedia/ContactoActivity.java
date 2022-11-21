package es.unex.dinopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ContactoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        Button bAtras2 = findViewById(R.id.bBack2);

        bAtras2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Remplaza con una acción", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                finish();
            }
        });

    }
}