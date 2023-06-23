package com.example.GymTrackingApp;

import org.apache.juli.logging.Log;

public class LogInVm {

    public String username;
    public String password;
    public boolean succeeded;
    public long id;

    public LogInVm(String theUsername, String thePassword) {
        username = theUsername;
        password = thePassword;
    }

    public LogInVm(int theId) {
        id = theId;
    }

    public LogInVm() {

    }

}
