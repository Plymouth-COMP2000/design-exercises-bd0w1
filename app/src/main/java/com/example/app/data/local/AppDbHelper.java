package com.example.app.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app.data.model.MenuItem;
import com.example.app.data.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class AppDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db";
    private static final int DB_VERSION = 2;

    // Menu Table
    public static final String TBL_MENU = "menu_items";
    public static final String COL_MENU_ID = "_id";
    public static final String COL_MENU_NAME = "name";
    public static final String COL_MENU_DESC = "description";
    public static final String COL_MENU_PRICE = "price";
    public static final String COL_MENU_AVAILABLE = "available";

    // Reservations Table
    private static final String TBL_RES = "reservations";
    private static final String COL_RES_ID = "id";
    private static final String COL_RES_GUEST_NAME = "guest_name";
    private static final String COL_RES_GUEST_EMAIL = "guest_email";
    private static final String COL_RES_DATE = "date";
    private static final String COL_RES_TIME = "time";
    private static final String COL_RES_PARTY_SIZE = "party_size";
    private static final String COL_RES_NOTES = "notes";
    private static final String COL_RES_STATUS = "status";

    public AppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMenu = "CREATE TABLE " + TBL_MENU + " ("
                + COL_MENU_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_MENU_NAME + " TEXT NOT NULL, "
                + COL_MENU_DESC + " TEXT, "
                + COL_MENU_PRICE + " REAL NOT NULL, "
                + COL_MENU_AVAILABLE + " INTEGER NOT NULL DEFAULT 1"
                + ");";

        String createRes = "CREATE TABLE " + TBL_RES + " ("
                + COL_RES_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_RES_GUEST_NAME + " TEXT NOT NULL, "
                + COL_RES_GUEST_EMAIL + " TEXT NOT NULL, "
                + COL_RES_DATE + " TEXT NOT NULL, "
                + COL_RES_TIME + " TEXT NOT NULL, "
                + COL_RES_PARTY_SIZE + " INTEGER NOT NULL, "
                + COL_RES_NOTES + " TEXT, "
                + COL_RES_STATUS + " TEXT NOT NULL DEFAULT 'PENDING'"
                + ");";

        db.execSQL(createMenu);
        db.execSQL(createRes);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_RES);
        onCreate(db);
    }

    // --- Reservation methods ---

    public long insertReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RES_GUEST_NAME, reservation.getGuestName());
        values.put(COL_RES_GUEST_EMAIL, reservation.getGuestEmail());
        values.put(COL_RES_DATE, reservation.getDate());
        values.put(COL_RES_TIME, reservation.getTime());
        values.put(COL_RES_PARTY_SIZE, reservation.getPartySize());
        values.put(COL_RES_NOTES, reservation.getNotes());
        values.put(COL_RES_STATUS, reservation.getStatus());

        long id = db.insert(TBL_RES, null, values);
        db.close();
        return id;
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                TBL_RES,
                null,
                null,
                null,
                null,
                null,
                COL_RES_DATE + " ASC"
        );

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_RES_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_RES_GUEST_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_RES_GUEST_EMAIL));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_RES_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COL_RES_TIME));
                int partySize = cursor.getInt(cursor.getColumnIndexOrThrow(COL_RES_PARTY_SIZE));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(COL_RES_NOTES));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COL_RES_STATUS));
                reservations.add(new Reservation(id, name, email, date, time, partySize, notes, status));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return reservations;
    }

    public int deleteReservation(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TBL_RES, COL_RES_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int updateReservationStatus(long id, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_RES_STATUS, status);
        return db.update(TBL_RES, cv, COL_RES_ID + "=?", new String[]{String.valueOf(id)});
    }

    // --- Menu Item methods ---

    public long insertMenuItem(MenuItem item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_MENU_NAME, item.getName());
        values.put(COL_MENU_DESC, item.getDescription());
        values.put(COL_MENU_PRICE, item.getPrice());
        values.put(COL_MENU_AVAILABLE, item.isAvailable() ? 1 : 0);

        long id = db.insert(TBL_MENU, null, values);
        db.close();
        return id;
    }

    public List<MenuItem> getAllMenuItems() {
        SQLiteDatabase db = getReadableDatabase();
        List<MenuItem> items = new ArrayList<>();

        Cursor c = db.query(
                TBL_MENU,
                null,
                null,
                null,
                null,
                null,
                COL_MENU_NAME + " ASC"
        );

        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndexOrThrow(COL_MENU_ID));
            String name = c.getString(c.getColumnIndexOrThrow(COL_MENU_NAME));
            String desc = c.getString(c.getColumnIndexOrThrow(COL_MENU_DESC));
            double price = c.getDouble(c.getColumnIndexOrThrow(COL_MENU_PRICE));
            boolean available = c.getInt(c.getColumnIndexOrThrow(COL_MENU_AVAILABLE)) == 1;

            items.add(new MenuItem(id, name, desc, price, available));
        }

        c.close();
        return items;
    }

    public int deleteMenuItem(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TBL_MENU, COL_MENU_ID + "=?", new String[]{String.valueOf(id)});
    }

    public String getDbPath() {
        return getReadableDatabase().getPath();
    }
}
