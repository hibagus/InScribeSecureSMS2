package digitalquantuminc.inscribesecuresms.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import digitalquantuminc.inscribesecuresms.DataType.TypeContact;

/**
 * Created by Bagus Hanindhito on 28/06/2017.
 */

public class contactDBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "isms.db";

    public contactDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_CONTACT = "CREATE TABLE " + TypeContact.TABLE + "("
                + TypeContact.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TypeContact.KEY_phone + " TEXT, "
                + TypeContact.KEY_name + " TEXT, "
                + TypeContact.KEY_date + " INTEGER, "
                + TypeContact.KEY_rsapub + " TEXT)";

        db.execSQL(CREATE_TABLE_CONTACT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TypeContact.TABLE);
        onCreate(db);
    }
}
