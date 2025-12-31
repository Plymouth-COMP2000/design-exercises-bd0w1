package com.example.app.data.model;

public class Reservation {
    private long id;
    private String guestName;
    private String guestEmail;
    private String date;     // keep as String for now
    private String time;     // keep as String for now
    private int partySize;
    private String notes;
    private String status;   // PENDING/APPROVED/CANCELLED

    public Reservation(long id, String guestName, String guestEmail, String date, String time,
                       int partySize, String notes, String status) {
        this.id = id;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.date = date;
        this.time = time;
        this.partySize = partySize;
        this.notes = notes;
        this.status = status;
    }

    public Reservation(String guestName, String guestEmail, String date, String time,
                       int partySize, String notes, String status) {
        this(-1, guestName, guestEmail, date, time, partySize, notes, status);
    }

    public long getId() { return id; }
    public String getGuestName() { return guestName; }
    public String getGuestEmail() { return guestEmail; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public int getPartySize() { return partySize; }
    public String getNotes() { return notes; }
    public String getStatus() { return status; }
    public void setId(long id) { this.id = id; }
}
