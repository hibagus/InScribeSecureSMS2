package digitalquantuminc.inscribesecuresms.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;

import digitalquantuminc.inscribesecuresms.DataBase.contactDBHelper;
import digitalquantuminc.inscribesecuresms.DataType.TypeContact;

/**
 * Created by Bagus Hanindhito on 29/06/2017.
 */

public class contactRepository {

    private contactDBHelper dbHelper;

    public contactRepository(Context context) {
        dbHelper = new contactDBHelper(context);
    }

    public int insert(TypeContact contact) {
        // Prepare Values to be inserted
        ContentValues values = new ContentValues();
        values.put(TypeContact.KEY_phone, contact.getPhone_number());
        values.put(TypeContact.KEY_name, contact.getContact_name());
        values.put(TypeContact.KEY_date, contact.getAcquisition_date());
        values.put(TypeContact.KEY_rsapub, contact.getRsa_publickey());

        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting row
        long contact_id = db.insert(TypeContact.TABLE, null, values);
        // Closing DB
        db.close();

        return (int) contact_id;
    }

    public void delete(int contact_id) {
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Delete Entry
        db.delete(TypeContact.TABLE, TypeContact.KEY_ID + " = ?", new String[]{String.valueOf(contact_id)});
        // Closing DB
        db.close();
    }

    public void delete(String phonenum) {
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Delete Entry
        db.delete(TypeContact.TABLE, TypeContact.KEY_phone + " = ?", new String[]{String.valueOf(phonenum)});
        // Closing DB
        db.close();
    }

    public void update(TypeContact contact, String old_phonenum) {
        // Prepare Values to be updated
        ContentValues values = new ContentValues();
        values.put(TypeContact.KEY_phone, contact.getPhone_number());
        values.put(TypeContact.KEY_name, contact.getContact_name());
        values.put(TypeContact.KEY_date, contact.getAcquisition_date());
        values.put(TypeContact.KEY_rsapub, contact.getRsa_publickey());
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Update Entry
        db.update(TypeContact.TABLE, values, TypeContact.KEY_phone + " = ?", new String[]{String.valueOf(old_phonenum)});
    }

    public TypeContact getContact(int contact_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeContact.KEY_ID + ", "
                + TypeContact.KEY_name + ", "
                + TypeContact.KEY_phone + ", "
                + TypeContact.KEY_date + ", "
                + TypeContact.KEY_rsapub + " FROM "
                + TypeContact.TABLE + " WHERE "
                + TypeContact.KEY_ID + " = ?";

        TypeContact contact = new TypeContact();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(contact_id)});
        if (cursor.moveToFirst()) {
            do {
                contact.setPhone_number(cursor.getString(cursor.getColumnIndex(TypeContact.KEY_phone)));
                contact.setContact_name(cursor.getString(cursor.getColumnIndex(TypeContact.KEY_name)));
                contact.setAcquisition_date(cursor.getLong(cursor.getColumnIndex(TypeContact.KEY_date)));
                contact.setRsa_publickey(cursor.getString(cursor.getColumnIndex(TypeContact.KEY_rsapub)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contact;
    }

    public TypeContact getContact(String phonenum) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeContact.KEY_ID + ", "
                + TypeContact.KEY_name + ", "
                + TypeContact.KEY_phone + ", "
                + TypeContact.KEY_date + ", "
                + TypeContact.KEY_rsapub + " FROM "
                + TypeContact.TABLE + " WHERE "
                + TypeContact.KEY_phone + " = ?";

        TypeContact contact = new TypeContact();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(phonenum)});
        if (cursor.moveToFirst()) {
            do {
                contact.setPhone_number(cursor.getString(cursor.getColumnIndex(TypeContact.KEY_phone)));
                contact.setContact_name(cursor.getString(cursor.getColumnIndex(TypeContact.KEY_name)));
                contact.setAcquisition_date(cursor.getLong(cursor.getColumnIndex(TypeContact.KEY_date)));
                contact.setRsa_publickey(cursor.getString(cursor.getColumnIndex(TypeContact.KEY_rsapub)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contact;
    }

    public int getContactId(String phonenum) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeContact.KEY_ID + " FROM "
                + TypeContact.TABLE + " WHERE "
                + TypeContact.KEY_phone + " = ?";

        int contact_id = 0;
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(phonenum)});
        if (cursor.moveToFirst()) {
            do {
                contact_id = cursor.getInt(cursor.getColumnIndex(TypeContact.KEY_ID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contact_id;
    }

    public ArrayList<HashMap<String, String>> getContactList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeContact.KEY_ID + ", "
                + TypeContact.KEY_name + ", "
                + TypeContact.KEY_phone + ", "
                + TypeContact.KEY_date + ", "
                + TypeContact.KEY_rsapub + " FROM "
                + TypeContact.TABLE;

        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
                contact.put(TypeContact.KEY_ID, cursor.getString(cursor.getColumnIndex(TypeContact.KEY_ID)));
                contact.put(TypeContact.KEY_phone, cursor.getString(cursor.getColumnIndex(TypeContact.KEY_phone)));
                contact.put(TypeContact.KEY_name, cursor.getString(cursor.getColumnIndex(TypeContact.KEY_name)));
                contact.put(TypeContact.KEY_date, String.valueOf(cursor.getLong(cursor.getColumnIndex(TypeContact.KEY_date))));
                contact.put(TypeContact.KEY_rsapub, cursor.getString(cursor.getColumnIndex(TypeContact.KEY_rsapub)));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    public ArrayList<HashMap<String, String>> getContactListSorted() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeContact.KEY_ID + ", "
                + TypeContact.KEY_name + ", "
                + TypeContact.KEY_phone + ", "
                + TypeContact.KEY_date + ", "
                + TypeContact.KEY_rsapub + " FROM "
                + TypeContact.TABLE + " ORDER BY "
                + TypeContact.KEY_name + " ASC ";

        ArrayList<HashMap<String, String>> contactList = new ArrayList<HashMap<String, String>>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> contact = new HashMap<String, String>();
                contact.put(TypeContact.KEY_ID, cursor.getString(cursor.getColumnIndex(TypeContact.KEY_ID)));
                contact.put(TypeContact.KEY_phone, cursor.getString(cursor.getColumnIndex(TypeContact.KEY_phone)));
                contact.put(TypeContact.KEY_name, cursor.getString(cursor.getColumnIndex(TypeContact.KEY_name)));
                contact.put(TypeContact.KEY_date, String.valueOf(cursor.getLong(cursor.getColumnIndex(TypeContact.KEY_date))));
                contact.put(TypeContact.KEY_rsapub, cursor.getString(cursor.getColumnIndex(TypeContact.KEY_rsapub)));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return contactList;
    }

    public void DropTable() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TypeContact.TABLE);
    }

    public void CreateTable() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String CREATE_TABLE_CONTACT = "CREATE TABLE IF NOT EXISTS " + TypeContact.TABLE + "("
                + TypeContact.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TypeContact.KEY_phone + " TEXT, "
                + TypeContact.KEY_name + " TEXT, "
                + TypeContact.KEY_date + " INTEGER, "
                + TypeContact.KEY_rsapub + " TEXT)";

        db.execSQL(CREATE_TABLE_CONTACT);
    }
}
