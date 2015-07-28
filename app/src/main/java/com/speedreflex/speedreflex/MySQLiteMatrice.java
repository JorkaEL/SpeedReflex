package com.speedreflex.speedreflex;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by xavier on 09/01/15.
 */
public class MySQLiteMatrice extends SQLiteOpenHelper {

    // SQL BDD
    static final String DATABASE_NAME = "bddSpeedReflex.db";
    static final int DATABASE_VERSION = 2;

    // SQL requete
    static final String TABLE = "DIFFICULTER";
    static final String COLUMN_ID = "_ID";
    static final String COLUMN_SPEUDO = "_SPEUDO";

    static final String COLUMN_SCORE = "_SCORE";

    static final String COLUMN_DIF = "_DIF";

    public MySQLiteMatrice(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    private static final String DATABASE_CREATE = "create table " + TABLE + "("
            + COLUMN_ID + " integer primary key autoincrement, "+ COLUMN_DIF+" text not null,"
            + COLUMN_SPEUDO + " text not null, " +COLUMN_SCORE+" text not null);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Class MySQLiteMatrice1", "Fct : onCreate");
        db.execSQL(DATABASE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("Class MySQLiteMatrice1", "Fct : onDrop");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE + ";");
        onCreate(db);
    }

    public void onDrop(SQLiteDatabase db) {
        Log.d("Class MySQLiteMatrice1", "Fct : onDrop");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE + ";");
        onCreate(db);
    }

}
