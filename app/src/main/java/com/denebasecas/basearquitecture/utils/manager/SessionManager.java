package com.denebasecas.basearquitecture.utils.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Deneb Chorny (Deneb Chorny)
 */
public class SessionManager {

    // ---------------------------------------------------------------------------------------------
    // Constants
    // ---------------------------------------------------------------------------------------------

    private static final String TAG = SessionManager.class.getSimpleName();

    // ---------------------------------------------------------------------------------------------
    // Variables
    // ---------------------------------------------------------------------------------------------

    private SharedPreferences.Editor mEditor;
    private static SessionManager mInstance;

    // ---------------------------------------------------------------------------------------------
    // Methods
    // ---------------------------------------------------------------------------------------------

    private SessionManager(Context context) {
        /*setModoTest(false);
        mPrefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);*/
    }

    public static void init(Context context) {
        if (mInstance == null) {
            mInstance = new SessionManager(context);
        }
    }

}