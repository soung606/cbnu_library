package com.example.lg.cbnu_library;

public class Beacon {
    String name;
    String address;
    String rssi;

    public Beacon(String name, String address, int rssi) {
        this.name = name;
        this.address = address;
        this.rssi = String.valueOf(rssi);
    }

    @Override
    public int hashCode() {
        return address.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Beacon)) return false;
        return address.equals(((Beacon)obj).address);
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getRssi() {
        return rssi;
    }
}
