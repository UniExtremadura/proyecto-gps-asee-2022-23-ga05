package es.unex.dinopedia;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button bEnciclopedia = findViewById(R.id.bEnciclopedia);
        Button bCombate = findViewById(R.id.bCombate);

        bCombate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CombateActivity.class);
                startActivity(intent);
            }
        });

        bEnciclopedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EnciclopediaActivity.class);
                startActivity(intent);
            }
        });
    }
}