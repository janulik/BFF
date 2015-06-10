package sk.upjs.ics.bff.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import sk.upjs.ics.bff.util.Defaults;

/**
 * Created by Jana on 4.6.2015.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "BFF_database_almostFinal";
    public static final int DATABASE_VERSION = 1;


    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, Defaults.DEFAULT_CURSOR_FACTORY, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(createTableUserSql());
        db.execSQL(createTableAlcoholSql());
        db.execSQL(createTableStatisticsSql());

        insertSampleEntryToUser(db, "Jana", 53, "090X");
        insertSampleEntryToUser(db, "JohnDoe", 63, "090X");

        insertSampleEntryToStatistics(db, 1);
        insertSampleEntryToStatistics(db, 1);
        insertSampleEntryToStatistics(db, 2);

        insertSampleEntryToAlcohol(db, "Rum", 32, 1);
        insertSampleEntryToAlcohol(db, "Vodka", 35, 1.2);
        insertSampleEntryToAlcohol(db, "Red wine", 17, 1.30);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private String createTableUserSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT,"
                + "%s INTEGER,"
                + "%s TEXT"
                + ")";
        return String.format(sqlTemplate,
                Provider.user.TABLE_NAME,
                Provider.user._ID,
                Provider.user.NAME,
                Provider.user.WEIGHT,
                Provider.user.BFF_NUMBER);
    }

    private String createTableAlcoholSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s TEXT,"
                + "%s INTEGER,"
                + "%s REAL"
                + ")";
        return String.format(sqlTemplate,
                Provider.alcohol.TABLE_NAME,
                Provider.alcohol._ID,
                Provider.alcohol.NAME,
                Provider.alcohol.VOLUME,
                Provider.alcohol.COST);
    }

    private String createTableStatisticsSql() {
        String sqlTemplate = "CREATE TABLE %s ("
                + "%s INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "%s DATETIME,"
                + "%s INTEGER"
                + ")";
        return String.format(sqlTemplate,
                Provider.statistics.TABLE_NAME,
                Provider.statistics._ID,
                Provider.statistics.TIMESTAMP,
                Provider.statistics.ID_ALCOHOL);
    }

    private void insertSampleEntryToStatistics(SQLiteDatabase db, int idAlcohol) {
        ContentValues contentValuesStatistics = new ContentValues();
        contentValuesStatistics.put(Provider.statistics.ID_ALCOHOL, idAlcohol);
        contentValuesStatistics.put(Provider.statistics.TIMESTAMP, System.currentTimeMillis() / 1000);
        db.insert(Provider.statistics.TABLE_NAME, Defaults.NO_NULL_COLUMN_HACK, contentValuesStatistics);
    }

    private void insertSampleEntryToUser(SQLiteDatabase db, String name, int weight, String phoneNumber) {
        ContentValues contentValuesProfile = new ContentValues();
        contentValuesProfile.put(Provider.user.NAME, name);
        contentValuesProfile.put(Provider.user.WEIGHT, weight);
        contentValuesProfile.put(Provider.user.BFF_NUMBER, phoneNumber);
        db.insert(Provider.user.TABLE_NAME, Defaults.NO_NULL_COLUMN_HACK, contentValuesProfile);
    }

    private void insertSampleEntryToAlcohol(SQLiteDatabase db, String name, int volume, double cost) {
        ContentValues contentValuesAlcohol = new ContentValues();
        contentValuesAlcohol.put(Provider.alcohol.NAME, name);
        contentValuesAlcohol.put(Provider.alcohol.VOLUME, volume);
        contentValuesAlcohol.put(Provider.alcohol.COST, cost);
        db.insert(Provider.alcohol.TABLE_NAME, Defaults.NO_NULL_COLUMN_HACK, contentValuesAlcohol);
    }

}
