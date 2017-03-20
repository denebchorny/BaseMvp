package com.denebasecas.basearquitecture.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.denebasecas.basearquitecture.BaseApplication;

/**
 * Created by Deneb Chorny (denebchorny@gmail.com).
 */

public class GooglePlayUtils {
    private static final String TAG = BaseApplication.class.getSimpleName();

    // ---------------------------------------------------------------------------------------------
    // Constants
    // ---------------------------------------------------------------------------------------------

    public static void goToPlayStore(Context context) {
        final String appPackageName = context.getPackageName(); // getPackageName() from Context or Activity object

        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + appPackageName)));

        } catch (android.content.ActivityNotFoundException e) {
            DLog.printStackTrace(TAG, e);

            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
