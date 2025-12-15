package com.pat.ulampinoy.User;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private final SharedPreferences sharedPreferences;

    private final SharedPreferences.Editor editor;

    private static final String PREF_NAME = "LoginSession";

    private static final String KEY_IS_LOGGED_IN = "isLoggedIn";

    private static final String KEY_USER_EMAIL = "userEmail";

    public SessionManager(Context context) {

        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        editor = sharedPreferences.edit();

    }

    public void createLoginSession(String email) {

        editor.putBoolean(KEY_IS_LOGGED_IN, true);

        editor.putString(KEY_USER_EMAIL, email);

        editor.apply();

    }

    public boolean isLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void logout() {

        editor.clear();

        editor.apply();

    }

    public String getUserEmail() {

        return sharedPreferences.getString(KEY_USER_EMAIL, null);
    }
}
