package com.example.esameits2014;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.esameits2014.adapters.AlbergoAdapter;
import com.example.esameits2014.data.AlbergoProvider;
import com.example.esameits2014.data.AlbergoTableHelper;
import com.example.esameits2014.fragments.ConfirmDialogFragment;
import com.example.esameits2014.fragments.ConfirmDialogFragmentListener;

public class ElencoRilevazioni extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> , ConfirmDialogFragmentListener {

    Button btnNuovaValutazione;
    ListView listviewElencoRilevazioni;

    AlbergoAdapter adapter;

    private static final int LOADER_ID = 1;

    public static final String ID = "USER_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnNuovaValutazione = (Button) findViewById(R.id.btn_nuova_valutazione);
        listviewElencoRilevazioni = (ListView) findViewById(R.id.lv_elenco_rilevazioni);

        adapter = new AlbergoAdapter(this,null);

        listviewElencoRilevazioni.setAdapter(adapter);

        listviewElencoRilevazioni.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ElencoRilevazioni.this, UpdateRilevazione.class);
                intent.putExtra(ID, id);
                startActivity(intent);
            }
        });

        listviewElencoRilevazioni.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                ConfirmDialogFragment dialogFragment = new ConfirmDialogFragment("ATTENZIONE", "Vuoi eliminare l’elemento selezionato?", id);
                dialogFragment.show(ElencoRilevazioni.this.getSupportFragmentManager(), null);
                return true;
            }
        });

        btnNuovaValutazione.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ElencoRilevazioni.this, NuovaRilevazione.class));
            }
        });

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, AlbergoProvider.ALBERGO_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        adapter.changeCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        adapter.changeCursor(null);
    }

    @Override
    public void onPositivePressed(long id) {
        getContentResolver().delete(AlbergoProvider.ALBERGO_URI, AlbergoTableHelper._ID + "=" + id, null);
        Toast.makeText(this, "Rilevazione Eliminato", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNegativePressed() {
        Toast.makeText(this, "Operazione annullata", Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onResume() {
        super.onResume();

        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}
