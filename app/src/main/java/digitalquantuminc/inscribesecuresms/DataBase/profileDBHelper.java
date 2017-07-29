package digitalquantuminc.inscribesecuresms.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import digitalquantuminc.inscribesecuresms.DataType.TypeProfile;

/**
 * Created by Fariz Azmi Pratama on 28/06/2017.
 * Create database for saving profile update
 */

public class profileDBHelper extends SQLiteOpenHelper {
    //region Global Variable
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "isms.db";

    //endregion
    //region Constructor
    public profileDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //endregion
    //region Override Method
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE_PROFILE = "CREATE TABLE " + TypeProfile.TABLE + "("
                + TypeProfile.KEY_ID + " INTEGER, "
                + TypeProfile.KEY_phone + " TEXT, "
                + TypeProfile.KEY_name + " TEXT, "
                + TypeProfile.KEY_date + " INTEGER, "
                + TypeProfile.KEY_rsapub + " TEXT, "
                + TypeProfile.KEY_rsapriv + " TEXT, "
                + TypeProfile.KEY_lastsync + " INTEGER)";

        db.execSQL(CREATE_TABLE_PROFILE);
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

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TypeProfile.TABLE);
        onCreate(db);
    }
    //endregion
}
