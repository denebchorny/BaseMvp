package com.denebasecas.basearquitecture.utils;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.denebasecas.basearquitecture.BaseApplication;


/**
 * Created Deneb Chorny (denebchorny@gmail.com)
 */

public class Broadcasts {

    // ---------------------------------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------------------------------

    private Broadcasts() {
        // This utility class is not publicly instantiable
    }

    // ---------------------------------------------------------------------------------------------
    // Methods
    // ---------------------------------------------------------------------------------------------

    public static void sendBroadcast(String action, @Nullable Bundle extras) {
        Intent intent = new Intent();
        intent.setAction(action);

        if (extras != null) {
            intent.putExtras(extras);
        }

        BaseApplication.getAppContext().sendBroadcast(intent);
    }
}
