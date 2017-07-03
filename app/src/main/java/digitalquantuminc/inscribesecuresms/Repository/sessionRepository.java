package digitalquantuminc.inscribesecuresms.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;

import digitalquantuminc.inscribesecuresms.DataBase.sessionDBHelper;
import digitalquantuminc.inscribesecuresms.DataType.TypeSession;

/**
 * Created by Bagus Hanindhito on 29/06/2017.
 * This class handle the operation for Session Data and its SQLite Database
 */

public class sessionRepository {
    //region Global Variable
    private sessionDBHelper dbHelper;

    //endregion
    //region Constructor
    public sessionRepository(Context context) {
        dbHelper = new sessionDBHelper(context);
    }

    //endregion
    //region INSERT Method
    public int insert(TypeSession session) {
        // Prepare Values to be inserted
        ContentValues values = new ContentValues();
        values.put(TypeSession.KEY_phone, session.getPhone_number());
        values.put(TypeSession.KEY_name, session.getName());
        values.put(TypeSession.KEY_valid, session.getSession_validity());
        values.put(TypeSession.KEY_date, session.getSession_handshake_date());
        values.put(TypeSession.KEY_role, session.getSession_role());
        values.put(TypeSession.KEY_ecdhpriv, session.getSession_ecdh_public_key());
        values.put(TypeSession.KEY_ecdhpub, session.getSession_ecdh_private_key());
        values.put(TypeSession.KEY_ecdhpubpart, session.getSession_ecdh_partner_public_key());
        values.put(TypeSession.KEY_ecdhds, session.getSession_ecdh_partner_digital_signature());
        values.put(TypeSession.KEY_ecdhvalid, session.getSession_ecdh_partner_validity());
        values.put(TypeSession.KEY_ecdhsecret, session.getSession_ecdh_shared_secret());
        values.put(TypeSession.KEY_aeskey, session.getSession_ecdh_aes_key());
        values.put(TypeSession.KEY_nummessage, session.getSession_num_message());

        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting row
        long session_id = db.insert(TypeSession.TABLE, null, values);
        // Closing DB
        db.close();

        return (int) session_id;
    }

    //endregion
    //region DELETE Method
    public void delete(int session_id) {
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Delete Entry
        db.delete(TypeSession.TABLE, TypeSession.KEY_ID + " = ?", new String[]{String.valueOf(session_id)});
        // Closing DB
        db.close();
    }

    public void delete(String phonenum) {
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Delete Entry
        db.delete(TypeSession.TABLE, TypeSession.KEY_phone + " = ?", new String[]{String.valueOf(phonenum)});
        // Closing DB
        db.close();
    }

    //endregion
    //region UPDATE Method
    public void update(TypeSession session, String old_phonenum) {
        // Prepare Values to be inserted
        ContentValues values = new ContentValues();
        values.put(TypeSession.KEY_phone, session.getPhone_number());
        values.put(TypeSession.KEY_name, session.getName());
        values.put(TypeSession.KEY_valid, session.getSession_validity());
        values.put(TypeSession.KEY_date, session.getSession_handshake_date());
        values.put(TypeSession.KEY_role, session.getSession_role());
        values.put(TypeSession.KEY_ecdhpriv, session.getSession_ecdh_public_key());
        values.put(TypeSession.KEY_ecdhpub, session.getSession_ecdh_private_key());
        values.put(TypeSession.KEY_ecdhpubpart, session.getSession_ecdh_partner_public_key());
        values.put(TypeSession.KEY_ecdhds, session.getSession_ecdh_partner_digital_signature());
        values.put(TypeSession.KEY_ecdhvalid, session.getSession_ecdh_partner_validity());
        values.put(TypeSession.KEY_ecdhsecret, session.getSession_ecdh_shared_secret());
        values.put(TypeSession.KEY_aeskey, session.getSession_ecdh_aes_key());
        values.put(TypeSession.KEY_nummessage, session.getSession_num_message());

        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting row
        db.update(TypeSession.TABLE, values, TypeSession.KEY_phone + " = ?", new String[]{String.valueOf(old_phonenum)});
        // Closing DB
        db.close();
    }

    //endregion
    //region GET ITEM Method
    public TypeSession getSession(int session_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeSession.KEY_ID + ", "
                + TypeSession.KEY_phone + ", "
                + TypeSession.KEY_name + ", "
                + TypeSession.KEY_valid + ", "
                + TypeSession.KEY_date + ", "
                + TypeSession.KEY_role + ", "
                + TypeSession.KEY_ecdhpriv + ", "
                + TypeSession.KEY_ecdhpub + ", "
                + TypeSession.KEY_ecdhpubpart + ", "
                + TypeSession.KEY_ecdhds + ", "
                + TypeSession.KEY_ecdhvalid + ", "
                + TypeSession.KEY_ecdhsecret + ", "
                + TypeSession.KEY_aeskey + ", "
                + TypeSession.KEY_nummessage + " FROM "
                + TypeSession.TABLE + " WHERE "
                + TypeSession.KEY_ID + " = ?";
        TypeSession session = new TypeSession();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(session_id)});
        if (cursor.moveToFirst()) {
            do {
                session.setPhone_number(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_phone)));
                session.setName(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_name)));
                session.setSession_validity(cursor.getInt(cursor.getColumnIndex(TypeSession.KEY_valid)));
                session.setSession_handshake_date(cursor.getLong(cursor.getColumnIndex(TypeSession.KEY_date)));
                session.setSession_role(cursor.getInt(cursor.getColumnIndex(TypeSession.KEY_role)));
                session.setSession_ecdh_private_key(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhpriv)));
                session.setSession_ecdh_public_key(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhpub)));
                session.setSession_ecdh_partner_public_key(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhpubpart)));
                session.setSession_ecdh_partner_digital_signature(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhds)));
                session.setSession_ecdh_partner_validity(cursor.getInt(cursor.getColumnIndex(TypeSession.KEY_ecdhvalid)));
                session.setSession_ecdh_shared_secret(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhsecret)));
                session.setSession_ecdh_aes_key(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_aeskey)));
                session.setSession_num_message(cursor.getInt(cursor.getColumnIndex(TypeSession.KEY_nummessage)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return session;
    }

    public TypeSession getSession(String phonenum) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeSession.KEY_ID + ", "
                + TypeSession.KEY_phone + ", "
                + TypeSession.KEY_name + ", "
                + TypeSession.KEY_valid + ", "
                + TypeSession.KEY_date + ", "
                + TypeSession.KEY_role + ", "
                + TypeSession.KEY_ecdhpriv + ", "
                + TypeSession.KEY_ecdhpub + ", "
                + TypeSession.KEY_ecdhpubpart + ", "
                + TypeSession.KEY_ecdhds + ", "
                + TypeSession.KEY_ecdhvalid + ", "
                + TypeSession.KEY_ecdhsecret + ", "
                + TypeSession.KEY_aeskey + ", "
                + TypeSession.KEY_nummessage + " FROM "
                + TypeSession.TABLE + " WHERE "
                + TypeSession.KEY_phone + " = ?";
        TypeSession session = new TypeSession();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(phonenum)});
        if (cursor.moveToFirst()) {
            do {
                session.setPhone_number(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_phone)));
                session.setName(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_name)));
                session.setSession_validity(cursor.getInt(cursor.getColumnIndex(TypeSession.KEY_valid)));
                session.setSession_handshake_date(cursor.getLong(cursor.getColumnIndex(TypeSession.KEY_date)));
                session.setSession_role(cursor.getInt(cursor.getColumnIndex(TypeSession.KEY_role)));
                session.setSession_ecdh_private_key(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhpriv)));
                session.setSession_ecdh_public_key(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhpub)));
                session.setSession_ecdh_partner_public_key(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhpubpart)));
                session.setSession_ecdh_partner_digital_signature(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhds)));
                session.setSession_ecdh_partner_validity(cursor.getInt(cursor.getColumnIndex(TypeSession.KEY_ecdhvalid)));
                session.setSession_ecdh_shared_secret(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhsecret)));
                session.setSession_ecdh_aes_key(cursor.getString(cursor.getColumnIndex(TypeSession.KEY_aeskey)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return session;
    }

    //endregion
    //region GET ID Method
    public int getSessionId(String phonenum) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeSession.KEY_ID + " FROM "
                + TypeSession.TABLE + " WHERE "
                + TypeSession.KEY_phone + " = ?";

        int session_id = 0;
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(phonenum)});
        if (cursor.moveToFirst()) {
            do {
                session_id = cursor.getInt(cursor.getColumnIndex(TypeSession.KEY_ID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return session_id;
    }

    //endregion
    //region GET LIST ITEM Method
    public ArrayList<HashMap<String, String>> getSessionList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeSession.KEY_ID + ", "
                + TypeSession.KEY_phone + ", "
                + TypeSession.KEY_name + ", "
                + TypeSession.KEY_valid + ", "
                + TypeSession.KEY_date + ", "
                + TypeSession.KEY_role + ", "
                + TypeSession.KEY_ecdhpriv + ", "
                + TypeSession.KEY_ecdhpub + ", "
                + TypeSession.KEY_ecdhpubpart + ", "
                + TypeSession.KEY_ecdhds + ", "
                + TypeSession.KEY_ecdhvalid + ", "
                + TypeSession.KEY_ecdhsecret + ", "
                + TypeSession.KEY_aeskey + ", "
                + TypeSession.KEY_nummessage + " FROM "
                + TypeSession.TABLE;

        ArrayList<HashMap<String, String>> sessionList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> session = new HashMap<>();
                session.put(TypeSession.KEY_ID, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ID)));
                session.put(TypeSession.KEY_phone, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_phone)));
                session.put(TypeSession.KEY_name, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_name)));
                session.put(TypeSession.KEY_valid, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_valid)));
                session.put(TypeSession.KEY_date, String.valueOf(cursor.getLong(cursor.getColumnIndex(TypeSession.KEY_date))));
                session.put(TypeSession.KEY_role, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_role)));
                session.put(TypeSession.KEY_ecdhpriv, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhpriv)));
                session.put(TypeSession.KEY_ecdhpub, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhpub)));
                session.put(TypeSession.KEY_ecdhpubpart, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhpubpart)));
                session.put(TypeSession.KEY_ecdhds, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhds)));
                session.put(TypeSession.KEY_ecdhvalid, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhvalid)));
                session.put(TypeSession.KEY_ecdhsecret, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_ecdhsecret)));
                session.put(TypeSession.KEY_aeskey, cursor.getString(cursor.getColumnIndex(TypeSession.KEY_aeskey)));
                sessionList.add(session);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return sessionList;
    }
    //endregion
}
