
package com.denebasecas.basearquitecture.utils;

import android.text.TextUtils;
import android.util.Log;

import com.denebasecas.basearquitecture.BuildConfig;

/**
 * Created Deneb Chorny (denebchorny@gmail.com)
 */

public class DLog {

    // ---------------------------------------------------------------------------------------------
    // Standar Log
    // ---------------------------------------------------------------------------------------------

    public static int v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            return Log.v(tag, msg); //NON-NLS
        }
        return 0;
    }

    public static int v(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            return Log.v(tag, msg, tr); //NON-NLS
        }
        return 0;
    }

    public static int d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            return Log.d(tag, msg); //NON-NLS
        }
        return 0;
    }

    public static int d(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            return Log.d(tag, msg, tr); //NON-NLS
        }
        return 0;
    }

    public static int i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            return Log.i(tag, msg); //NON-NLS
        }
        return 0;
    }

    public static int i(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            return Log.i(tag, msg, tr); //NON-NLS
        }
        return 0;
    }

    public static int w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            return Log.w(tag, msg); //NON-NLS
        }
        return 0;
    }

    public static int w(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            return Log.w(tag, msg, tr); //NON-NLS
        }
        return 0;
    }

    public static int w(String tag, Throwable tr) {
        if (BuildConfig.DEBUG) {
            return Log.w(tag, tr); //NON-NLS
        }
        return 0;
    }

    public static int e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            return Log.e(tag, msg); //NON-NLS
        }
        return 0;
    }

    public static int e(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            return Log.e(tag, msg, tr); //NON-NLS
        }
        return 0;
    }

    public static int wtf(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            return Log.wtf(tag, msg); //NON-NLS
        }
        return 0;
    }

    public static int wtf(String tag, Throwable tr) {
        if (BuildConfig.DEBUG) {
            return Log.wtf(tag, tr); //NON-NLS
        }
        return 0;
    }

    public static int wtf(String tag, String msg, Throwable tr) {
        if (BuildConfig.DEBUG) {
            return Log.wtf(tag, msg, tr); //NON-NLS
        }
        return 0;
    }

    public static String getStackTraceString(Throwable tr) {
        if (BuildConfig.DEBUG) {
            return Log.getStackTraceString(tr);
        }
        return "No Debug Mode";
    }

    public static int println(int priority, String tag, String msg) {
        if (BuildConfig.DEBUG) {
            return Log.println(priority, tag, msg); //NON-NLS
        }
        return 0;
    }

    // ---------------------------------------------------------------------------------------------
    // Error Handlers
    // ---------------------------------------------------------------------------------------------

    public static void printStackTrace(String tag, Exception exception) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, "Error: " + ((exception != null)
                    ? Log.getStackTraceString(exception)
                    : " Unknown Error")); //NON-NLS
        }
    }

    public static void printStackTrace(String tag, String message, Exception exception) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, (TextUtils.isEmpty(message) ? "-->" : message) + ": " + ((exception != null)
                    ? Log.getStackTraceString(exception)
                    : " Unknown Error")); //NON-NLS
        }
    }

    public static void printStackTrace(String tag, Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, "Error: " + ((throwable != null)
                    ? Log.getStackTraceString(throwable)
                    : " Unknown Error")); //NON-NLS
        }
    }

    public static void printStackTrace(String tag, String message, Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, (TextUtils.isEmpty(message) ? "-->" : message) + ": "
                    + ((throwable != null)
                    ? Log.getStackTraceString(throwable)
                    : " Unknown Error")); //NON-NLS
        }
    }

    // I made this method it but not tested
    public static void printStackTraceFull(String tag, Exception exception) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, (TextUtils.isEmpty(exception.getMessage())
                    ? "-->"
                    : exception.getMessage()) + ": ", exception); //NON-NLS
        }
    }
}
