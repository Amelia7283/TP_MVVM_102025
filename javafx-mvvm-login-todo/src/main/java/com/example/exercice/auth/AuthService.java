package com.example.exercice.auth;

import java.util.Map;

public class AuthService {
    private final Map<String,String> users = Map.of(
        "alice", "secret",
        "bob",   "password",
            "charlie",   "1234"
    );
    public boolean authenticate(String username, String password){
        if (username == null || password == null) return false;
        String expected = users.get(username.trim().toLowerCase());
        return expected != null && expected.equals(password);
    }
}
