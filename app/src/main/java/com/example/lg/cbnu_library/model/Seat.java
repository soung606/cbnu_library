package com.example.lg.cbnu_library.model;

import java.io.Serializable;

public class Seat implements Serializable {
    private int id;
    private int libraryNum;
    private int seatNum;
    private boolean isEmpty;
    private String beaconId;

    public Seat(int id, int libraryNum, int seatNum, boolean isEmpty, String beaconId) {
        this.id = id;
        this.libraryNum = libraryNum;
        this.seatNum = seatNum;
        this.isEmpty = isEmpty;
        this.beaconId = beaconId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLibraryNum() {
        return libraryNum;
    }

    public void setLibraryNum(int libraryNum) {
        this.libraryNum = libraryNum;
    }

    public int getSeatNum() {
        return seatNum;
    }

    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public String getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String beaconId) {
        this.beaconId = beaconId;
    }
}
