package com.example.quiz_application;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class database extends SQLiteOpenHelper {

    // Constantes pour la base de données
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "quiz_app.db";

    // Table des utilisateurs
    private static final String TABLE_CLIENTS = "clients";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LAST_NAME = "last_name";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

    // Table des questions
    private static final String TABLE_QUESTIONS = "questions";
    private static final String QUESTION_ID = "id";
    private static final String QUESTION_DOMAIN = "domain";
    private static final String QUESTION_TEXT = "question";
    private static final String OPTION_1 = "option1";
    private static final String OPTION_2 = "option2";
    private static final String OPTION_3 = "option3";
    private static final String OPTION_4 = "option4";
    private static final String CORRECT_ANSWER = "correct_answer";

    // Constructeur
    public database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Création de la table des utilisateurs
        String CREATE_CLIENTS_TABLE = "CREATE TABLE " + TABLE_CLIENTS + " (" +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_EMAIL + " TEXT UNIQUE, " +
                KEY_NAME + " TEXT, " +
                KEY_LAST_NAME + " TEXT, " +
                KEY_PASSWORD + " TEXT)";
        db.execSQL(CREATE_CLIENTS_TABLE);

        // Insertion d'un utilisateur par défaut
        db.execSQL("INSERT INTO " + TABLE_CLIENTS + " (" + KEY_EMAIL + ", " + KEY_NAME + ", " + KEY_LAST_NAME + ", " + KEY_PASSWORD +
                ") VALUES ('bouchra.khalfaouybh@gmail.com', 'Khalfaouy', 'Bouchra', '1234')");

        // Création de la table des questions
        String CREATE_QUESTIONS_TABLE = "CREATE TABLE " + TABLE_QUESTIONS + " (" +
                QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QUESTION_DOMAIN + " TEXT, " +
                QUESTION_TEXT + " TEXT, " +
                OPTION_1 + " TEXT, " +
                OPTION_2 + " TEXT, " +
                OPTION_3 + " TEXT, " +
                OPTION_4 + " TEXT, " +
                CORRECT_ANSWER + " INTEGER)";
        db.execSQL(CREATE_QUESTIONS_TABLE);

        // Insertion de quelques questions par défaut
        db.execSQL("INSERT INTO " + TABLE_QUESTIONS + " (" + QUESTION_DOMAIN + ", " + QUESTION_TEXT + ", " + OPTION_1 + ", " + OPTION_2 +
                ", " + OPTION_3 + ", " + OPTION_4 + ", " + CORRECT_ANSWER +
                ") VALUES ('Science', 'Quelle est la planète la plus proche du Soleil ?', 'Vénus', 'Terre', 'Mercure', 'Mars', 3)");
        db.execSQL("INSERT INTO " + TABLE_QUESTIONS + " (" + QUESTION_DOMAIN + ", " + QUESTION_TEXT + ", " + OPTION_1 + ", " + OPTION_2 +
                ", " + OPTION_3 + ", " + OPTION_4 + ", " + CORRECT_ANSWER +
                ") VALUES ('Culture', 'Qui a peint La Joconde ?', 'Picasso', 'Da Vinci', 'Van Gogh', 'Rembrandt', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    // Méthode pour valider un utilisateur
    public String validateUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + KEY_NAME + ", " + KEY_LAST_NAME +
                " FROM " + TABLE_CLIENTS +
                " WHERE " + KEY_EMAIL + " = ? AND " + KEY_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        String fullName = null;
        if (cursor.moveToFirst()) {
            fullName = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)) + " " +
                    cursor.getString(cursor.getColumnIndexOrThrow(KEY_LAST_NAME));
        }
        cursor.close();
        return fullName;
    }

    // Méthode pour récupérer les questions par domaine
    public Cursor getQuestionsByDomain(String domain) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_QUESTIONS + " WHERE " + QUESTION_DOMAIN + " = ?", new String[]{domain});
    }

    // Ajouter un utilisateur à la base de données
    public long addUser(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, firstName);
        values.put(KEY_LAST_NAME, lastName);
        values.put(KEY_EMAIL, email);
        values.put(KEY_PASSWORD, password);

        long result = db.insert(TABLE_CLIENTS, null, values);
        db.close();
        return result;
    }

}
