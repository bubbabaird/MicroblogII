package com.theironyard.charlotte;

import java.util.ArrayList;

public class User {
    String name;
    ArrayList<Message> messages = new ArrayList<>();
    // the constructor for "User".
    public User(String name) {
        this.name = name;
    }
}
