package com.example.app.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.app.data.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class AppDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "app.db";
    private static final int DATABASE_VERSION = 1;

    // Table for Reservations
    private static final String TABLE_RESERVATIONS = "reservations";
    private static final String COL_RESERVATION_ID = "id";
    private static final String COL_GUEST_NAME = "guest_name";
    private static final String COL_GUEST_EMAIL = "guest_email";
    private static final String COL_DATE = "date";
    private static final String COL_TIME = "time";
    private static final String COL_PARTY_SIZE = "party_size";
    private static final String COL_NOTES = "notes";
    private static final String COL_STATUS = "status";

    private static final String CREATE_TABLE_RESERVATIONS = "CREATE TABLE "
            + TABLE_RESERVATIONS + "("
            + COL_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_GUEST_NAME + " TEXT,"
            + COL_GUEST_EMAIL + " TEXT,"
            + COL_DATE + " TEXT,"
            + COL_TIME + " TEXT,"
            + COL_PARTY_SIZE + " INTEGER,"
            + COL_NOTES + " TEXT,"
            + COL_STATUS + " TEXT" + ")";

    public AppDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESERVATIONS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESERVATIONS);
        onCreate(db);
    }

    public long insertReservation(Reservation reservation) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_GUEST_NAME, reservation.getGuestName());
        values.put(COL_GUEST_EMAIL, reservation.getGuestEmail());
        values.put(COL_DATE, reservation.getDate());
        values.put(COL_TIME, reservation.getTime());
        values.put(COL_PARTY_SIZE, reservation.getPartySize());
        values.put(COL_NOTES, reservation.getNotes());
        values.put(COL_STATUS, reservation.getStatus());

        long id = db.insert(TABLE_RESERVATIONS, null, values);
        db.close();
        return id;
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> reservations = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_RESERVATIONS, null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(cursor.getColumnIndexOrThrow(COL_RESERVATION_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_GUEST_NAME));
                String email = cursor.getString(cursor.getColumnIndexOrThrow(COL_GUEST_EMAIL));
                String date = cursor.getString(cursor.getColumnIndexOrThrow(COL_DATE));
                String time = cursor.getString(cursor.getColumnIndexOrThrow(COL_TIME));
                int partySize = cursor.getInt(cursor.getColumnIndexOrThrow(COL_PARTY_SIZE));
                String notes = cursor.getString(cursor.getColumnIndexOrThrow(COL_NOTES));
                String status = cursor.getString(cursor.getColumnIndexOrThrow(COL_STATUS));
                reservations.add(new Reservation(id, name, email, date, time, partySize, notes, status));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return reservations;
    }

    public int deleteReservation(long id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_RESERVATIONS, COL_RESERVATION_ID + "=?", new String[]{String.valueOf(id)});
    }

    public int updateReservationStatus(long id, String status) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COL_STATUS, status);
        return db.update(TABLE_RESERVATIONS, cv, COL_RESERVATION_ID + "=?", new String[]{String.valueOf(id)});
    }

    public String getDbPath() {
        return getReadableDatabase().getPath();
    }
}
