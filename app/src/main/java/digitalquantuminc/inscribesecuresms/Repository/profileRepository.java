package digitalquantuminc.inscribesecuresms.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import digitalquantuminc.inscribesecuresms.DataBase.profileDBHelper;
import digitalquantuminc.inscribesecuresms.DataType.TypeProfile;

/**
 * Created by Bagus Hanindhito on 29/06/2017.
 */

public class profileRepository {

    private profileDBHelper dbHelper;

    public profileRepository(Context context) {
        dbHelper = new profileDBHelper(context);
    }

    public int insert(TypeProfile profile) {
        // Prepare Values to be inserted
        ContentValues values = new ContentValues();
        values.put(TypeProfile.KEY_phone, profile.getPhone_number());
        values.put(TypeProfile.KEY_name, profile.getName_self());
        values.put(TypeProfile.KEY_date, profile.getGenerated_date());
        values.put(TypeProfile.KEY_rsapub, profile.getRsa_publickey());
        values.put(TypeProfile.KEY_rsapriv, profile.getRsa_privatekey());
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting row
        long profile_id = db.insert(TypeProfile.TABLE, null, values);
        // Closing DB
        db.close();

        return (int) profile_id;
    }

    public void delete(int profile_id) {
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Delete Entry
        db.delete(TypeProfile.TABLE, TypeProfile.KEY_ID + "= ?", new String[]{String.valueOf(profile_id)});
        // Closing DB
        db.close();
    }

    public void delete(String phonenum) {
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Delete Entry
        db.delete(TypeProfile.TABLE, TypeProfile.KEY_phone + "= ?", new String[]{String.valueOf(phonenum)});
        // Closing DB
        db.close();
    }

    public void update(TypeProfile profile, String old_phonenum) {
        // Prepare Values to be inserted
        ContentValues values = new ContentValues();
        values.put(TypeProfile.KEY_phone, profile.getPhone_number());
        values.put(TypeProfile.KEY_name, profile.getName_self());
        values.put(TypeProfile.KEY_date, profile.getGenerated_date());
        values.put(TypeProfile.KEY_rsapub, profile.getRsa_publickey());
        values.put(TypeProfile.KEY_rsapriv, profile.getRsa_privatekey());
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting row
        db.update(TypeProfile.TABLE, values, TypeProfile.KEY_phone + " = ?", new String[]{String.valueOf(old_phonenum)});
        // Closing DB
        db.close();
    }

    public TypeProfile getProfile(int profile_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeProfile.KEY_ID + ", "
                + TypeProfile.KEY_phone + ", "
                + TypeProfile.KEY_name + ", "
                + TypeProfile.KEY_date + ", "
                + TypeProfile.KEY_rsapub + ", "
                + TypeProfile.KEY_rsapriv + " FROM "
                + TypeProfile.TABLE + " WHERE "
                + TypeProfile.KEY_ID + " = ?";
        TypeProfile profile = new TypeProfile();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(profile_id)});
        if (cursor.moveToFirst()) {
            do {
                profile.setPhone_number(cursor.getString(cursor.getColumnIndex(TypeProfile.KEY_phone)));
                profile.setName_self(cursor.getString(cursor.getColumnIndex(TypeProfile.KEY_name)));
                profile.setGenerated_date(cursor.getLong(cursor.getColumnIndex(TypeProfile.KEY_date)));
                profile.setRsa_publickey(cursor.getString(cursor.getColumnIndex(TypeProfile.KEY_rsapub)));
                profile.setRsa_privatekey(cursor.getString(cursor.getColumnIndex(TypeProfile.KEY_rsapriv)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return profile;
    }

    public int getProfileId(String phonenum) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeProfile.KEY_ID + " FROM "
                + TypeProfile.TABLE + " WHERE "
                + TypeProfile.KEY_phone + " = ?";
        int profile_id = 0;
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(phonenum)});
        if (cursor.moveToFirst()) {
            do {
                profile_id = cursor.getInt(cursor.getColumnIndex(TypeProfile.KEY_ID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return profile_id;
    }

    public ArrayList<HashMap<String, String>> getProfileList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeProfile.KEY_ID + ", "
                + TypeProfile.KEY_phone + ", "
                + TypeProfile.KEY_name + ", "
                + TypeProfile.KEY_date + ", "
                + TypeProfile.KEY_rsapub + ", "
                + TypeProfile.KEY_rsapriv + " FROM "
                + TypeProfile.TABLE;
        ArrayList<HashMap<String, String>> profileList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> profile = new HashMap<String, String>();
                profile.put("id", cursor.getString(cursor.getColumnIndex(TypeProfile.KEY_ID)));
                profile.put("phone", cursor.getString(cursor.getColumnIndex(TypeProfile.KEY_phone)));
                profile.put("name", cursor.getString(cursor.getColumnIndex(TypeProfile.KEY_name)));
                profile.put("date", String.valueOf(cursor.getLong(cursor.getColumnIndex(TypeProfile.KEY_date))));
                profile.put("rsapub", cursor.getString(cursor.getColumnIndex(TypeProfile.KEY_rsapub)));
                profile.put("rsapriv", cursor.getString(cursor.getColumnIndex(TypeProfile.KEY_rsapriv)));
                profileList.add(profile);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return profileList;
    }
}
