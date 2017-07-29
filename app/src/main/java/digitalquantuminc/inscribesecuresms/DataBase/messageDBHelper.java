package digitalquantuminc.inscribesecuresms.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import digitalquantuminc.inscribesecuresms.DataType.TypeMessage;

/**
 * Created by Ulfah Nadiya on 24/07/2017.
 * Create database for saving message format
 */

public class messageDBHelper extends SQLiteOpenHelper {
    //region Global Variable
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "isms.db";

    //endregion
    //region Constructor
    public messageDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //region Override Method
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_MESSAGE = "CREATE TABLE " + TypeMessage.TABLE + "("
                + TypeMessage.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TypeMessage.KEY_direction + " INTEGER, "
                + TypeMessage.KEY_messagetype + " INTEGER, "
                + TypeMessage.KEY_address + " TEXT, "
                + TypeMessage.KEY_timestamp + " INTEGER, "
                + TypeMessage.KEY_encodedcontent + " TEXT, "
                + TypeMessage.KEY_plaincontent + " TEXT)";

        db.execSQL(CREATE_TABLE_MESSAGE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TypeMessage.TABLE);
        onCreate(db);
    }
    //endregion
}
