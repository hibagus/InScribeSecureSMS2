package digitalquantuminc.inscribesecuresms.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import digitalquantuminc.inscribesecuresms.DataBase.messageDBHelper;
import digitalquantuminc.inscribesecuresms.DataType.TypeMessage;

/**
 * Created by Bagus Hanindhito on 24/07/2017.
 */

public class messageRepository {
    //region Global Variable
    private messageDBHelper dbHelper;
    //endregion

    //region Constructor
    public messageRepository(Context context)
    {
        dbHelper = new messageDBHelper(context);
    }
    //endregion
    //region INSERT Method
    public int insert(TypeMessage message) {
        // Prepare Values to be inserted
        ContentValues values = new ContentValues();
        values.put(TypeMessage.KEY_direction, message.getDirection());
        values.put(TypeMessage.KEY_messagetype, message.getMessagetype());
        values.put(TypeMessage.KEY_address, message.getAddress());
        values.put(TypeMessage.KEY_timestamp, message.getTimestamp());
        values.put(TypeMessage.KEY_encodedcontent, message.getEncodedcontent());
        values.put(TypeMessage.KEY_plaincontent, message.getPlaincontent());
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting row
        long message_id = db.insert(TypeMessage.TABLE, null, values);
        // Closing DB
        db.close();
        return (int) message_id;
    }

    //endregion
    //region DELETE Method
    public void delete(int message_id) {
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Delete Entry
        db.delete(TypeMessage.TABLE, TypeMessage.KEY_ID + " = ?", new String[]{String.valueOf(message_id)});
        // Closing DB
        db.close();
    }

    public void delete(Long timestamp) {
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Delete Entry
        db.delete(TypeMessage.TABLE, TypeMessage.KEY_timestamp + " = ?", new String[]{String.valueOf(timestamp)});
        // Closing DB
        db.close();
    }

    public void delete(String phonenum) {
        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Delete Entry
        db.delete(TypeMessage.TABLE, TypeMessage.KEY_address + " = ?", new String[]{phonenum});
        // Closing DB
        db.close();
    }

    //endregion
    //region UPDATE Method
    public void update(TypeMessage message, Long timestamp) {
        // Prepare Values to be inserted
        ContentValues values = new ContentValues();
        values.put(TypeMessage.KEY_direction, message.getDirection());
        values.put(TypeMessage.KEY_messagetype, message.getMessagetype());
        values.put(TypeMessage.KEY_address, message.getAddress());
        values.put(TypeMessage.KEY_timestamp, message.getTimestamp());
        values.put(TypeMessage.KEY_encodedcontent, message.getEncodedcontent());
        values.put(TypeMessage.KEY_plaincontent, message.getPlaincontent());

        // Open connection to write the DB
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Inserting row
        db.update(TypeMessage.TABLE, values, TypeMessage.KEY_timestamp + " = ?", new String[]{String.valueOf(timestamp)});
        // Closing DB
        db.close();
    }

    //endregion

    //region GET ITEM Method
    public TypeMessage getMessage(int message_id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeMessage.KEY_ID + ", "
                + TypeMessage.KEY_direction + ", "
                + TypeMessage.KEY_messagetype + ", "
                + TypeMessage.KEY_address + ", "
                + TypeMessage.KEY_timestamp + ", "
                + TypeMessage.KEY_encodedcontent + ", "
                + TypeMessage.KEY_plaincontent + " FROM "
                + TypeMessage.TABLE + " WHERE "
                + TypeMessage.KEY_ID + " = ?";
        TypeMessage message = new TypeMessage();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(message_id)});
        if (cursor.moveToFirst()) {
            do {
                message.setDirection(cursor.getInt(cursor.getColumnIndex(TypeMessage.KEY_direction)));
                message.setMessagetype(cursor.getInt(cursor.getColumnIndex(TypeMessage.KEY_messagetype)));
                message.setAddress(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_address)));
                message.setTimestamp(cursor.getLong(cursor.getColumnIndex(TypeMessage.KEY_timestamp)));
                message.setEncodedcontent(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_encodedcontent)));
                message.setPlaincontent(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_plaincontent)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return message;
    }

    public TypeMessage getMessagebyTimeStamp(long timestamp) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeMessage.KEY_ID + ", "
                + TypeMessage.KEY_direction + ", "
                + TypeMessage.KEY_messagetype + ", "
                + TypeMessage.KEY_address + ", "
                + TypeMessage.KEY_timestamp + ", "
                + TypeMessage.KEY_encodedcontent + ", "
                + TypeMessage.KEY_plaincontent + " FROM "
                + TypeMessage.TABLE + " WHERE "
                + TypeMessage.KEY_timestamp + " = ?";
        TypeMessage message = new TypeMessage();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(timestamp)});
        if (cursor.moveToFirst()) {
            do {
                message.setDirection(cursor.getInt(cursor.getColumnIndex(TypeMessage.KEY_direction)));
                message.setMessagetype(cursor.getInt(cursor.getColumnIndex(TypeMessage.KEY_messagetype)));
                message.setAddress(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_address)));
                message.setTimestamp(cursor.getLong(cursor.getColumnIndex(TypeMessage.KEY_timestamp)));
                message.setEncodedcontent(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_encodedcontent)));
                message.setPlaincontent(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_plaincontent)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return message;
    }

    public TypeMessage getMessagebyAddressSorted(String address) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeMessage.KEY_ID + ", "
                + TypeMessage.KEY_direction + ", "
                + TypeMessage.KEY_messagetype + ", "
                + TypeMessage.KEY_address + ", "
                + TypeMessage.KEY_timestamp + ", "
                + TypeMessage.KEY_encodedcontent + ", "
                + TypeMessage.KEY_plaincontent + " FROM "
                + TypeMessage.TABLE + " WHERE "
                + TypeMessage.KEY_address + " = ? ORDER BY "
                + TypeMessage.KEY_timestamp + " ASC ";

        TypeMessage message = new TypeMessage();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{address});
        if (cursor.moveToFirst()) {
            do {
                message.setDirection(cursor.getInt(cursor.getColumnIndex(TypeMessage.KEY_direction)));
                message.setMessagetype(cursor.getInt(cursor.getColumnIndex(TypeMessage.KEY_messagetype)));
                message.setAddress(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_address)));
                message.setTimestamp(cursor.getLong(cursor.getColumnIndex(TypeMessage.KEY_timestamp)));
                message.setEncodedcontent(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_encodedcontent)));
                message.setPlaincontent(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_plaincontent)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return message;
    }

    public TypeMessage getNewestMessagebyAddress(String address) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeMessage.KEY_ID + ", "
                + TypeMessage.KEY_direction + ", "
                + TypeMessage.KEY_messagetype + ", "
                + TypeMessage.KEY_address + ", "
                + TypeMessage.KEY_timestamp + ", "
                + TypeMessage.KEY_encodedcontent + ", "
                + TypeMessage.KEY_plaincontent + " FROM "
                + TypeMessage.TABLE + " WHERE "
                + TypeMessage.KEY_address + " = ? GROUP BY "
                + TypeMessage.KEY_address + " HAVING "
                + "MAX(" +TypeMessage.KEY_timestamp + " ) ";

        TypeMessage message = new TypeMessage();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{address});
        if (cursor.moveToFirst()) {
            do {
                message.setDirection(cursor.getInt(cursor.getColumnIndex(TypeMessage.KEY_direction)));
                message.setMessagetype(cursor.getInt(cursor.getColumnIndex(TypeMessage.KEY_messagetype)));
                message.setAddress(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_address)));
                message.setTimestamp(cursor.getLong(cursor.getColumnIndex(TypeMessage.KEY_timestamp)));
                message.setEncodedcontent(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_encodedcontent)));
                message.setPlaincontent(cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_plaincontent)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return message;
    }

    //endregion
    //region GET ID Method
    public int getMessageId(long timestamp) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeMessage.KEY_ID + " FROM "
                + TypeMessage.TABLE + " WHERE "
                + TypeMessage.KEY_timestamp + " = ?";

        int message_id = 0;
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(timestamp)});
        if (cursor.moveToFirst()) {
            do {
                message_id = cursor.getInt(cursor.getColumnIndex(TypeMessage.KEY_ID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return message_id;
    }

    //endregion
    //region GET LIST ITEM Method
    public ArrayList<HashMap<String, String>> getMessageList() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeMessage.KEY_ID + ", "
                + TypeMessage.KEY_direction + ", "
                + TypeMessage.KEY_messagetype + ", "
                + TypeMessage.KEY_address + ", "
                + TypeMessage.KEY_timestamp + ", "
                + TypeMessage.KEY_encodedcontent + ", "
                + TypeMessage.KEY_plaincontent + " FROM "
                + TypeMessage.TABLE;

        ArrayList<HashMap<String, String>> messageList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> message = new HashMap<>();
                message.put(TypeMessage.KEY_ID, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_ID)));
                message.put(TypeMessage.KEY_direction, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_direction)));
                message.put(TypeMessage.KEY_messagetype, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_messagetype)));
                message.put(TypeMessage.KEY_address, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_address)));
                message.put(TypeMessage.KEY_timestamp, String.valueOf(cursor.getLong(cursor.getColumnIndex(TypeMessage.KEY_timestamp))));
                message.put(TypeMessage.KEY_encodedcontent, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_encodedcontent)));
                message.put(TypeMessage.KEY_plaincontent, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_plaincontent)));
                messageList.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return messageList;
    }

    public ArrayList<HashMap<String, String>> getMessageListSorted() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeMessage.KEY_ID + ", "
                + TypeMessage.KEY_direction + ", "
                + TypeMessage.KEY_messagetype + ", "
                + TypeMessage.KEY_address + ", "
                + TypeMessage.KEY_timestamp + ", "
                + TypeMessage.KEY_encodedcontent + ", "
                + TypeMessage.KEY_plaincontent + " FROM "
                + TypeMessage.TABLE+ " ORDER BY "
                + TypeMessage.KEY_timestamp + " ASC ";


        ArrayList<HashMap<String, String>> messageList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> message = new HashMap<>();
                message.put(TypeMessage.KEY_ID, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_ID)));
                message.put(TypeMessage.KEY_direction, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_direction)));
                message.put(TypeMessage.KEY_messagetype, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_messagetype)));
                message.put(TypeMessage.KEY_address, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_address)));
                message.put(TypeMessage.KEY_timestamp, String.valueOf(cursor.getLong(cursor.getColumnIndex(TypeMessage.KEY_timestamp))));
                message.put(TypeMessage.KEY_encodedcontent, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_encodedcontent)));
                message.put(TypeMessage.KEY_plaincontent, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_plaincontent)));
                messageList.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return messageList;
    }



    public ArrayList<HashMap<String, String>> getMessageListbyAddressSorted(String Address) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeMessage.KEY_ID + ", "
                + TypeMessage.KEY_direction + ", "
                + TypeMessage.KEY_messagetype + ", "
                + TypeMessage.KEY_address + ", "
                + TypeMessage.KEY_timestamp + ", "
                + TypeMessage.KEY_encodedcontent + ", "
                + TypeMessage.KEY_plaincontent + " FROM "
                + TypeMessage.TABLE + " WHERE "
                + TypeMessage.KEY_address + " = ? ORDER BY "
                + TypeMessage.KEY_timestamp + " DESC ";

        ArrayList<HashMap<String, String>> messageList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, new String[] {Address});
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> message = new HashMap<>();
                message.put(TypeMessage.KEY_ID, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_ID)));
                message.put(TypeMessage.KEY_direction, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_direction)));
                message.put(TypeMessage.KEY_messagetype, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_messagetype)));
                message.put(TypeMessage.KEY_address, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_address)));
                message.put(TypeMessage.KEY_timestamp, String.valueOf(cursor.getLong(cursor.getColumnIndex(TypeMessage.KEY_timestamp))));
                message.put(TypeMessage.KEY_encodedcontent, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_encodedcontent)));
                message.put(TypeMessage.KEY_plaincontent, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_plaincontent)));
                messageList.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return messageList;
    }

    public ArrayList<HashMap<String, String>> getNewestMessageListinEachAddressSorted() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT "
                + TypeMessage.KEY_ID + ", "
                + TypeMessage.KEY_direction + ", "
                + TypeMessage.KEY_messagetype + ", "
                + TypeMessage.KEY_address + ", "
                + TypeMessage.KEY_timestamp + ", "
                + TypeMessage.KEY_encodedcontent + ", "
                + TypeMessage.KEY_plaincontent + " FROM "
                + TypeMessage.TABLE + " GROUP BY "
                + TypeMessage.KEY_address + " HAVING "
                + "MAX(" + TypeMessage.KEY_timestamp + ") ORDER BY "
                + TypeMessage.KEY_timestamp + " DESC ";

        ArrayList<HashMap<String, String>> messageList = new ArrayList<>();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> message = new HashMap<>();
                message.put(TypeMessage.KEY_ID, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_ID)));
                message.put(TypeMessage.KEY_direction, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_direction)));
                message.put(TypeMessage.KEY_messagetype, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_messagetype)));
                message.put(TypeMessage.KEY_address, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_address)));
                message.put(TypeMessage.KEY_timestamp, String.valueOf(cursor.getLong(cursor.getColumnIndex(TypeMessage.KEY_timestamp))));
                message.put(TypeMessage.KEY_encodedcontent, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_encodedcontent)));
                message.put(TypeMessage.KEY_plaincontent, cursor.getString(cursor.getColumnIndex(TypeMessage.KEY_plaincontent)));
                messageList.add(message);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return messageList;
    }

    //region TABLE Method
    public void DropTable() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TypeMessage.TABLE);
        db.close();
    }

    public void CreateTable() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String CREATE_TABLE_MESSAGE = "CREATE TABLE IF NOT EXISTS " + TypeMessage.TABLE + " ( "
                + TypeMessage.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TypeMessage.KEY_direction + " INTEGER, "
                + TypeMessage.KEY_messagetype + " INTEGER, "
                + TypeMessage.KEY_address + " TEXT, "
                + TypeMessage.KEY_timestamp + " INTEGER, "
                + TypeMessage.KEY_encodedcontent + " TEXT, "
                + TypeMessage.KEY_plaincontent + " TEXT)";

        db.execSQL(CREATE_TABLE_MESSAGE);
        db.close();
    }


}
