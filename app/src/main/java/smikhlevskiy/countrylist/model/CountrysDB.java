package smikhlevskiy.countrylist.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by "SMikhlevskiy" on 09-Feb-16.
 */
public class CountrysDB extends SQLiteOpenHelper {
    public static String TAG=CountrysDB.class.getSimpleName();
    public static String DB_NAME = "COUNTRYSDB";
    public static String TABLE_NAME = "COUNTRYS";
    public static int DB_VERSION = 4;

    public static String KEY_ID = "_ID";
    public static String KEY_NAME = "CODE";
    public static String KEY_CODE = "NAME";

    public CountrysDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "ONCreateDB");
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( " +
                KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT, " +
                KEY_CODE + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "ONUpgrade");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void setCountrys(ArrayList<Country> countrys){
        SQLiteDatabase db = getWritableDatabase();

        for (Country country:countrys){
            ContentValues cv=new ContentValues();
            cv.put(KEY_NAME,country.getName());
            cv.put(KEY_CODE,country.getCode());
            db.insert(TABLE_NAME,null,cv);
        }
        db.close();
    }

    public ArrayList<Country> getCountrys(){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<Country> countries=new ArrayList<Country>();

        if (cursor.moveToFirst()) {
            do {
                Country country=new Country();
                country.setName(cursor.getString(1));
                country.setCode(cursor.getString(2));

                countries.add(country);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return countries;

    }
}
