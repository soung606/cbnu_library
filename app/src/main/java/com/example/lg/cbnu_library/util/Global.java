package com.example.lg.cbnu_library.util;

import com.example.lg.cbnu_library.model.User;

public class Global {
    private static Global instance;

    private Global() {

    }

    public static Global getInstance() {
        if (instance == null) instance = new Global();
        return instance;
    }

    String ipAddr = "http://192.168.200.153:8000/";

    public String getIpAddr() {
        return ipAddr;
    }

    User user = new User(1, "test@test.com", "1234", "test", 0);

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
