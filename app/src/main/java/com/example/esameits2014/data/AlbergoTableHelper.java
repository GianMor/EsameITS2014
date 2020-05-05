package com.example.esameits2014.data;

import android.provider.BaseColumns;

public class AlbergoTableHelper implements BaseColumns {


    public static final String TABLE_NAME = "albergo";
    public static final String NOME = "nome";
    public static final String CITTA = "citta";
    public static final String VOTO = "voto";
    public static final String PREZZO = "prezzo";

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
            NOME + " TEXT , " +
            CITTA + " TEXT , " +
            VOTO + " FLOAT DEFAULT 0 , " +
            PREZZO + " FLOAT DEFAULT 0 ) ;";
}

