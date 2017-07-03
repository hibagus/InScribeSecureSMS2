package digitalquantuminc.inscribesecuresms.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import digitalquantuminc.inscribesecuresms.DataType.TypeSession;

/**
 * Created by Bagus Hanindhito on 28/06/2017.
 * This class is handle SQLite Database for the Session Database.
 */

public class sessionDBHelper extends SQLiteOpenHelper {
    //region Global Variable
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "isms.db";

    //endregion
    //region Constructor
    public sessionDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //endregion
    //region Override Method
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PROFILE = "CREATE TABLE " + TypeSession.TABLE + "("
                + TypeSession.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TypeSession.KEY_phone + " TEXT, "
                + TypeSession.KEY_valid + " INTEGER, "
                + TypeSession.KEY_date + " INTEGER, "
                + TypeSession.KEY_role + " INTEGER, "
                + TypeSession.KEY_ecdhpub + "TEXT,"
                + TypeSession.KEY_ecdhpriv + "TEXT, "
                + TypeSession.KEY_ecdhpubpart + "TEXT, "
                + TypeSession.KEY_ecdhds + "TEXT, "
                + TypeSession.KEY_ecdhvalid + "TEXT, "
                + TypeSession.KEY_ecdhsecret + "TEXT, "
                + TypeSession.KEY_aeskey + "TEXT, "
                + TypeSession.KEY_nummessage + "INTEGER)";

        db.execSQL(CREATE_TABLE_PROFILE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TypeSession.TABLE);
        onCreate(db);
    }
    //endregion
}
