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

public class UpdateRilevazione extends AppCompatActivity {

    EditText etNomeAlbergo,etCitta,etCosto;

    Button btnAnulla,btnSalva;

    RatingBar ratingBar;

    long id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_rilevazione);

        etNomeAlbergo = (EditText) findViewById(R.id.et_update_nome_albergo);
        etCitta = (EditText) findViewById(R.id.et_update_nome_citta);
        etCosto = (EditText) findViewById(R.id.et_update_costo_medio);

        ratingBar = (RatingBar) findViewById(R.id.update_ratingBar);


        btnAnulla = (Button) findViewById(R.id.btn_update_anulla);
        btnSalva = (Button) findViewById(R.id.btn_update_salva);

        id = getIntent().getLongExtra(ElencoRilevazioni.ID, 0);

        Cursor cursor = getContentResolver().query(AlbergoProvider.ALBERGO_URI, null, AlbergoTableHelper._ID+ "=" + id, null, null);
        cursor.moveToNext();

        etNomeAlbergo.setText(cursor.getString(cursor.getColumnIndex(AlbergoTableHelper.NOME)));
        etCitta.setText(cursor.getString(cursor.getColumnIndex(AlbergoTableHelper.CITTA)));
        etCosto.setText(String.valueOf(cursor.getFloat(cursor.getColumnIndex(AlbergoTableHelper.PREZZO))));
        ratingBar.setRating(cursor.getFloat(cursor.getColumnIndex(AlbergoTableHelper.VOTO)));

        btnSalva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etNomeAlbergo.getText().toString()) ||
                        TextUtils.isEmpty(etCitta.getText().toString()) ||
                        TextUtils.isEmpty(etCosto.getText().toString()) ) {
                    Toast.makeText(UpdateRilevazione.this, "Devi riempire tutti i campi", Toast.LENGTH_SHORT).show();
                }
                else doUpdate();
            }
        });


        btnAnulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UpdateRilevazione.this, ElencoRilevazioni.class);
                startActivity(intent);
            }
        });


    }

    private void doUpdate() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(AlbergoTableHelper.NOME, etNomeAlbergo.getText().toString());
        contentValues.put(AlbergoTableHelper.CITTA, etCitta.getText().toString());
        contentValues.put(AlbergoTableHelper.PREZZO, Float.parseFloat(etCosto.getText().toString()));
        contentValues.put(AlbergoTableHelper.VOTO, ratingBar.getRating());

        getContentResolver().update(AlbergoProvider.ALBERGO_URI,contentValues,AlbergoTableHelper._ID + " = " + id,null);

        finish();
    }
}
