package sg.edu.rp.webservices.test1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locations.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_LOCATION = "location";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_LATITUDE = "latitude";
    private static final String COLUMN_LONGITUDE = "longitude";
    private static final String COLUMN_LOC_NAME = "loc_name";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createLocationTableSql = "CREATE TABLE " + TABLE_LOCATION + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_LATITUDE + " REAL,"
                + COLUMN_LONGITUDE + " REAL,"
                + COLUMN_LOC_NAME + " TEXT )";

        ContentValues values = new ContentValues();
        values.put(COLUMN_LATITUDE, 1.2541);
        values.put(COLUMN_LONGITUDE, 103.8237);
        values.put(COLUMN_LOC_NAME, "USS");

        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_LATITUDE, 1.2784);
        values2.put(COLUMN_LONGITUDE, 103.7992);
        values2.put(COLUMN_LOC_NAME, "HortPark");

        ContentValues values3 = new ContentValues();
        values3.put(COLUMN_LATITUDE, 1.4044);
        values3.put(COLUMN_LONGITUDE, 103.793);
        values3.put(COLUMN_LOC_NAME, "Singapore Zoo");

        ContentValues values4 = new ContentValues();
        values4.put(COLUMN_LATITUDE, 1.4617);
        values4.put(COLUMN_LONGITUDE, 103.8368);
        values4.put(COLUMN_LOC_NAME, "Sembawang Park");

        ContentValues values5 = new ContentValues();
        values5.put(COLUMN_LATITUDE, 1.3188);
        values5.put(COLUMN_LONGITUDE, 103.7063);
        values5.put(COLUMN_LOC_NAME, "Jurong Bird Park");

        db.execSQL(createLocationTableSql);
        db.insert(TABLE_LOCATION, null, values);
        db.insert(TABLE_LOCATION, null, values2);
        db.insert(TABLE_LOCATION, null, values3);
        db.insert(TABLE_LOCATION, null, values4);
        db.insert(TABLE_LOCATION, null, values5);
        Log.i("info", "created tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION);
        onCreate(db);
    }


    public long insertLocation(Double latitude, Double longitude, String loc_name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LATITUDE, latitude);
        values.put(COLUMN_LONGITUDE, longitude);
        values.put(COLUMN_LOC_NAME, loc_name);
        long result = db.insert(TABLE_LOCATION, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result);
        return result;
    }

    public int updateLocation(Location data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_LATITUDE, data.getLatitude());
        values.put(COLUMN_LONGITUDE, data.getLongitude());
        values.put(COLUMN_LOC_NAME, data.getLoc_name());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_LOCATION, values, condition, args);
        db.close();
        return result;
    }

//    public ArrayList<String> getLocation() {
//        ArrayList<String> locations = new ArrayList<String>();
//        String selectQuery = "SELECT " + COLUMN_ID + "," + COLUMN_LATITUDE + "," + COLUMN_LONGITUDE + "," + COLUMN_LOC_NAME + " FROM " + TABLE_LOCATION;
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(selectQuery, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                locations.add(cursor.getString(0));
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//
//        return locations;
//    }

    public ArrayList<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<Location>();
        String selectQuery = "SELECT " + COLUMN_ID + "," + COLUMN_LATITUDE + "," + COLUMN_LONGITUDE + "," + COLUMN_LOC_NAME + " FROM " + TABLE_LOCATION;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                Double latitude = Double.valueOf(cursor.getString(1));
                Double longitude = Double.valueOf(cursor.getString(2));
                String loc_name = cursor.getString(3);
                Location obj = new Location(id, latitude, longitude, loc_name);
                locations.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return locations;
    }

}
