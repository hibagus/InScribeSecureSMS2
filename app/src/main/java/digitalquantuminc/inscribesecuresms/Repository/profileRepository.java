package digitalquantuminc.inscribesecuresms.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import digitalquantuminc.inscribesecuresms.DataBase.profileDBHelper;
import digitalquantuminc.inscribesecuresms.DataType.TypeContact;
import digitalquantuminc.inscribesecuresms.DataType.TypeProfile;

/**
 * Created by Fariz Azmi Pratama on 29/06/2017.
 * Query implementation for database profile
 */

public class profileRepository {

    //region Global Variable
    private profileDBHelper dbHelper;
    private android.util.Log Log;

    //endregion
    //region Constructor
    public profileRepository(Context context) {
        dbHelper = new profileDBHelper(context);
    }

    //endregion

    //region UPDATE Method
    public void update(TypeProfile profile) {
        // Prepare Values to be inserted
        ContentValues values = new ContentValues();
        values.put(TypeProfile.KEY_phone, profile.getPhone_number());
        values.put(TypeProfile.KEY_name, profile.getName_self());
        values.put(TypeProfile.KEY_date, profile.getGenerated_date());
        values.put(TypeProfile.KEY_rsapub, profile.getRsa_publickey());
        values.put(TypeProfile.KEY_rsapriv, profile.getRsa_privatekey());
        values.put(TypeProfile.KEY_lastsync, profile.getLastsync());
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting row
        db.update(TypeProfile.TABLE, values, TypeProfile.KEY_ID + " = ?", new String[]{String.valueOf(TypeProfile.DEFAULTID)});
        // Closing DB
        db.close();
    }

    //endregion
    //region GET ITEM Method
    public TypeProfile getProfile(int profile_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeProfile.KEY_ID + ", "
                + TypeProfile.KEY_phone + ", "
                + TypeProfile.KEY_name + ", "
                + TypeProfile.KEY_date + ", "
                + TypeProfile.KEY_rsapub + ", "
                + TypeProfile.KEY_rsapriv + ", "
                + TypeProfile.KEY_lastsync + " FROM "
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
                profile.setLastsync(cursor.getLong(cursor.getColumnIndex(TypeProfile.KEY_lastsync)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return profile;
    }

    public boolean isProfileExist(int profile_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeProfile.KEY_ID + ", "
                + TypeProfile.KEY_phone + ", "
                + TypeProfile.KEY_name + ", "
                + TypeProfile.KEY_date + ", "
                + TypeProfile.KEY_rsapub + ", "
                + TypeProfile.KEY_rsapriv + ", "
                + TypeProfile.KEY_lastsync + " FROM "
                + TypeProfile.TABLE + " WHERE "
                + TypeProfile.KEY_ID + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(profile_id)});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public boolean isProfileExist(String phonenum) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeProfile.KEY_ID + ", "
                + TypeProfile.KEY_phone + ", "
                + TypeProfile.KEY_name + ", "
                + TypeProfile.KEY_date + ", "
                + TypeProfile.KEY_rsapub + ", "
                + TypeProfile.KEY_rsapriv + ", "
                + TypeProfile.KEY_lastsync + " FROM "
                + TypeProfile.TABLE + " WHERE "
                + TypeProfile.KEY_phone + " = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{phonenum});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    //endregion

    //region TABLE Method
    public void DropTable() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TypeProfile.TABLE);
        db.close();
    }

    public void CreateTable() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String CREATE_TABLE_PROFILE = "CREATE TABLE IF NOT EXISTS " + TypeProfile.TABLE + "("
                + TypeProfile.KEY_ID + " INTEGER, "
                + TypeProfile.KEY_phone + " TEXT, "
                + TypeProfile.KEY_name + " TEXT, "
                + TypeProfile.KEY_date + " INTEGER, "
                + TypeProfile.KEY_rsapub + " TEXT, "
                + TypeProfile.KEY_rsapriv + " TEXT, "
                + TypeProfile.KEY_lastsync + " INTEGER)";
        db.execSQL(CREATE_TABLE_PROFILE);
        db.close();
    }

    public void CreateTableandInitialize() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String CREATE_TABLE_PROFILE = "CREATE TABLE IF NOT EXISTS " + TypeProfile.TABLE + "("
                + TypeProfile.KEY_ID + " INTEGER, "
                + TypeProfile.KEY_phone + " TEXT, "
                + TypeProfile.KEY_name + " TEXT, "
                + TypeProfile.KEY_date + " INTEGER, "
                + TypeProfile.KEY_rsapub + " TEXT, "
                + TypeProfile.KEY_rsapriv + " TEXT, "
                + TypeProfile.KEY_lastsync + " INTEGER)";
        db.execSQL(CREATE_TABLE_PROFILE);

        if(!isProfileExist(TypeProfile.DEFAULTID))
        {
            // Prepare Values to be inserted
            ContentValues values = new ContentValues();
            values.put(TypeProfile.KEY_ID, TypeProfile.DEFAULTID);
            values.put(TypeProfile.KEY_name, TypeProfile.DEFAULTNAME);
            values.put(TypeProfile.KEY_phone, TypeProfile.DEFAULTPHONENUM);
            values.put(TypeProfile.KEY_date, TypeProfile.DEFAULTGENERATEDDATE);
            values.put(TypeProfile.KEY_rsapub, TypeProfile.DEFAULTRSAPUBKEY);
            values.put(TypeProfile.KEY_rsapriv, TypeProfile.DEFAULTRSAPRIVKEY);
            values.put(TypeProfile.KEY_lastsync, TypeProfile.DEFAULTLASTSYNC);

            // Insert Default Value
            db.insert(TypeProfile.TABLE, null, values);
        }
        db.close();
    }

    //endregion
}
