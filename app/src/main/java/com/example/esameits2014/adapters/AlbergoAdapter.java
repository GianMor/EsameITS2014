package com.example.esameits2014.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.esameits2014.R;
import com.example.esameits2014.data.AlbergoTableHelper;

public class AlbergoAdapter extends CursorAdapter {

    public AlbergoAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_albergo, null);
        return view;    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String name = cursor.getString(cursor.getColumnIndex(AlbergoTableHelper.NOME));
        String citta = cursor.getString(cursor.getColumnIndex(AlbergoTableHelper.CITTA));
        float prezzo = cursor.getFloat(cursor.getColumnIndex(AlbergoTableHelper.PREZZO));
        float voto = cursor.getFloat(cursor.getColumnIndex(AlbergoTableHelper.VOTO));



        TextView tvNome = view.findViewById(R.id.nome),
                tvCitta = view.findViewById(R.id.citta),
                tvPrezzo = view.findViewById(R.id.prezzo),
                tvVoto = view.findViewById(R.id.voto);

        tvNome.setText(name);
        tvCitta.setText(citta);
        tvPrezzo.setText(String.valueOf(prezzo));
        tvVoto.setText(String.valueOf(voto));
    }
}
