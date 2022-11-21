package es.unex.dinopedia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AyudaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        Button bAtras = findViewById(R.id.bBack);

        bAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Remplaza con una acción", Snackbar.LENGTH_LONG)
                //       .setAction("Action", null).show();
                finish();
            }
        });
    }


}