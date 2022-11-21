package es.unex.dinopedia;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import es.unex.dinopedia.roomdb.LogroDatabase;

public class MainActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppExecutors.getInstance().diskIO().execute(
                new Runnable() {
                    @Override
                    public void run() {
                        LogroDatabase.getInstance(MainActivity.this).getDao().deleteAll();

                        if (LogroDatabase.getInstance(MainActivity.this).getDao().count() == 0) {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.logros)));
                            String receiveString = "";
                            StringBuilder stringBuilder = new StringBuilder();

                            while (true) {
                                try {
                                    if (!((receiveString = bufferedReader.readLine()) != null)) break;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                stringBuilder.append("\n").append(receiveString);
                            }

                            String read = stringBuilder.toString();

                            List<Logro> logro = Arrays.asList(new Gson().fromJson(read, Logro[].class));
                            for (int i = 0; i < logro.size(); i++) {
                                Logro l = logro.get(i);
                                LogroDatabase.getInstance(MainActivity.this).getDao().insert(l);
                            }
                        }

                    }
                }
        );
    }
}