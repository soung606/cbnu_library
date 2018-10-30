package com.example.lg.cbnu_library.model;

import java.io.Serializable;

public class Library implements Serializable {
    private int id;
    private String name;
    private int seatCount;

    public Library(int id, String name, int seatCount) {
        this.id = id;
        this.name = name;
        this.seatCount = seatCount;
    }

    @Override
    public String toString() {
        return id + ":" + name + "/" + seatCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeatCount() {
        return seatCount;
    }

    public void setSeatCount(int seatCount) {
        this.seatCount = seatCount;
    }
}
