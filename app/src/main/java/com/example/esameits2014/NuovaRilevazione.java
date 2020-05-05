package com.example.esameits2014;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.esameits2014.data.AlbergoProvider;
import com.example.esameits2014.data.AlbergoTableHelper;

public class NuovaRilevazione extends AppCompatActivity {

    private EditText etNomeAlbergo,etCitta,etCosto;

    private Button btnAnulla,btnSalva;

    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuova_rilevazione);

        etNomeAlbergo = (EditText) findViewById(R.id.et_new_nome_albergo);
        etCitta = (EditText) findViewById(R.id.et_new_nome_citta);
        etCosto = (EditText) findViewById(R.id.et_new_costo_medio);

        ratingBar = (RatingBar) findViewById(R.id.new_ratingBar);
        ratingBar.setNumStars(5);

        btnAnulla = (Button) findViewById(R.id.btn_new_anulla);
        btnSalva = (Button) findViewById(R.id.btn_new_salva);


        btnSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNomeAlbergo.getText().toString()) ||
                        TextUtils.isEmpty(etCitta.getText().toString()) ||
                        TextUtils.isEmpty(etCosto.getText().toString()) ) {
                    Toast.makeText(NuovaRilevazione.this, "Devi riempire tutti i campi", Toast.LENGTH_SHORT).show();
                }
                else doInsert();

                Intent intent = new Intent(NuovaRilevazione.this, ElencoRilevazioni.class);
                startActivity(intent);
            }
        });

        btnAnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NuovaRilevazione.this, ElencoRilevazioni.class);
                startActivity(intent);
            }
        });

    }

    public void doInsert() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(AlbergoTableHelper.NOME, etNomeAlbergo.getText().toString());
        contentValues.put(AlbergoTableHelper.CITTA, etCitta.getText().toString());
        contentValues.put(AlbergoTableHelper.PREZZO,Float.parseFloat(etCosto.getText().toString()));
        contentValues.put(AlbergoTableHelper.VOTO, ratingBar.getRating());

        getContentResolver().insert(AlbergoProvider.ALBERGO_URI,contentValues);

        finish();
    }

}


