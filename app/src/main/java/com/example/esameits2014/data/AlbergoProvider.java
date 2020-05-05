package com.example.esameits2014.data;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AlbergoProvider extends ContentProvider {


    public static final String AUTORITY = "com.example.esameits2014.data.ContentProvider";
    public static final String BASE_PATH_ALBERGO = "albergo";
    public static final int ALL_ALBERGO = 1;
    public static final int SINGLE_ALBERGO = 0;
    public static final String MIME_TYPE_ORDERS = ContentResolver.CURSOR_DIR_BASE_TYPE + "vnd.all_albergo";
    public static final String MIME_TYPE_ORDER = ContentResolver.CURSOR_ITEM_BASE_TYPE + "vnd.single_albergo";
    public static final Uri ALBERGO_URI = Uri.parse(ContentResolver.SCHEME_CONTENT + "://" + AUTORITY
            + "/" + BASE_PATH_ALBERGO);
    private AlbergoDB database;
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTORITY, BASE_PATH_ALBERGO, ALL_ALBERGO);
        uriMatcher.addURI(AUTORITY, BASE_PATH_ALBERGO + "/#", SINGLE_ALBERGO);
    }

    @Override
    public boolean onCreate() {
        database = new AlbergoDB(getContext());
        return true;    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = database.getReadableDatabase();
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        switch (uriMatcher.match(uri)) {
            case SINGLE_ALBERGO:
                builder.setTables(AlbergoTableHelper.TABLE_NAME);
                builder.appendWhere(AlbergoTableHelper._ID + " = " + uri.getLastPathSegment());
                break;
            case ALL_ALBERGO:
                builder.setTables(AlbergoTableHelper.TABLE_NAME);
                break;
        }
        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case SINGLE_ALBERGO:
                return MIME_TYPE_ORDER;

            case ALL_ALBERGO:
                return MIME_TYPE_ORDERS;
        }
        return null;    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (uriMatcher.match(uri) == ALL_ALBERGO) {
            SQLiteDatabase db = database.getWritableDatabase();
            long result = db.insert(AlbergoTableHelper.TABLE_NAME, null, values);
            String resultString = ContentResolver.SCHEME_CONTENT + "://" + BASE_PATH_ALBERGO + "/" + result;
            getContext().getContentResolver().notifyChange(uri, null);
            return Uri.parse(resultString);
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case SINGLE_ALBERGO:
                table = AlbergoTableHelper.TABLE_NAME;
                query = AlbergoTableHelper._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;
            case ALL_ALBERGO:
                table = AlbergoTableHelper.TABLE_NAME;
                query = selection;
                break;
        }
        int deletedRows = db.delete(table, query, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return deletedRows;    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String table = "", query = "";
        SQLiteDatabase db = database.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case SINGLE_ALBERGO:
                table = AlbergoTableHelper.TABLE_NAME;
                query = AlbergoTableHelper._ID + " = " + uri.getLastPathSegment();
                if (selection != null) {
                    query += " AND " + selection;
                }
                break;
            case ALL_ALBERGO:
                table = AlbergoTableHelper.TABLE_NAME;
                query = selection;
                break;
        }
        int updatedRows = db.update(table, values, query, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);

        return updatedRows;    }
}
