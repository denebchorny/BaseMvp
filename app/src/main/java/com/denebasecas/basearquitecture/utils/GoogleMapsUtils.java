package com.denebasecas.basearquitecture.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;


/**
 * Created by Deneb Chorny (denebchorny@gmail.com).
 */

public class GoogleMapsUtils {

    // ---------------------------------------------------------------------------------------------
    // Constructors
    // ---------------------------------------------------------------------------------------------

    private GoogleMapsUtils() {
        // This utility class is not publicly instantiable
    }

    // ---------------------------------------------------------------------------------------------
    // Methods
    // ---------------------------------------------------------------------------------------------

    public static void goTo(@NonNull Context context, String name, double lat1, double lng1, double lat2, double lng2) {
        if (isGoogleMapsInstalled(context)) {
            String direction = String.format("geo:<%s>,<%s>?q=<%s>,<%s>(%s)",
                    name,
                    String.valueOf(lat1),
                    String.valueOf(lng1),
                    String.valueOf(lat2),
                    String.valueOf(lng2));

            Intent intent = new Intent(Intent.ACTION_VIEW
                    , Uri.parse(direction));

            context.startActivity(intent);
        }
    }

    public static boolean isGoogleMapsInstalled(@NonNull Context context) {
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo("com.google.android.apps.maps", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
