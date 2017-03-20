
package com.denebasecas.basearquitecture;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.denebasecas.basearquitecture.utils.DLog;

import java.io.File;
import java.util.List;


/**
 * Created by Deneb Chorny (denebchorny@gmail.com)
 */

public class BaseApplication extends Application {

    // ---------------------------------------------------------------------------------------------
    // Constants
    // ---------------------------------------------------------------------------------------------

    private static final String TAG = BaseApplication.class.getSimpleName();

    // ---------------------------------------------------------------------------------------------
    // Variables
    // ---------------------------------------------------------------------------------------------

    private static BaseApplication instance;
    private static int status;

    // ---------------------------------------------------------------------------------------------
    // Local Classes
    // ---------------------------------------------------------------------------------------------

    private interface StatusApp {
        int BACKGROUND = 0;
        int FOREGROUND = 1;
    }

    // ---------------------------------------------------------------------------------------------
    // Lifecycle
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        status = StatusApp.FOREGROUND;

        if (BuildConfig.DEBUG) {
            registerActivityLifecycleCallbacks(activityLifecyleCallbacks);
        }
    }

    public synchronized static void onResumeActivity() {
        if (status == StatusApp.BACKGROUND) {
            // Do what you want on every activity when is on resume. Example: Check Location permission
        }
        status = StatusApp.FOREGROUND;
    }

    @Override
    public synchronized void onTrimMemory(int level) {
        // TRIM_MEMORY_UI_HIDDEN means app goes to background
        if ((level == TRIM_MEMORY_UI_HIDDEN)) {
            status = StatusApp.BACKGROUND;
        }
        super.onTrimMemory(level);
    }


    // ---------------------------------------------------------------------------------------------
    // Methods
    // ---------------------------------------------------------------------------------------------

    public static BaseApplication get() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    public static String getVersionName() {
        try {
            return (instance.getApplicationContext().getPackageManager()
                    .getPackageInfo(instance.getApplicationContext().getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            DLog.printStackTrace(TAG, e);
            return "";
        }
    }

    public static boolean isAppInForeground() {
        if (instance.getApplicationContext() != null) {
            ActivityManager activityManager = (ActivityManager) instance
                    .getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);

            List<ActivityManager.RunningAppProcessInfo> appProcesses
                    = activityManager.getRunningAppProcesses();

            if (appProcesses != null && appProcesses.size() > 0) {
                for (ActivityManager.RunningAppProcessInfo aux : appProcesses) {
                    if (aux.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                            && aux.processName.equals(instance.getApplicationContext().getPackageName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static void clearApplicationData() {
        deleteCache();
    }

    private static void deleteCache() {
        try {
            File dir = getAppContext().getCacheDir();
            deleteDir(dir);
            Log.d(TAG, "deleteCache"); //NON-NLS
        } catch (Exception e) {
            Log.e(TAG, "deleteCache: ", e); //NON-NLS
        }
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String aChildren : children) {
                boolean success = deleteDir(new File(dir, aChildren));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else {
            return dir != null && dir.isFile() && dir.delete();
        }
    }

    // ---------------------------------------------------------------------------------------------
    // Activities lifecycle listeners
    // ---------------------------------------------------------------------------------------------

    private ActivityLifecycleCallbacks activityLifecyleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            DLog.v(TAG, activity.getClass().getPackage().getName());
        }

        @Override
        public void onActivityStarted(Activity activity) {
            DLog.v(TAG, activity.getClass().getPackage().getName());
        }

        @Override
        public void onActivityResumed(Activity activity) {
            DLog.v(TAG, activity.getClass().getPackage().getName());
        }

        @Override
        public void onActivityPaused(Activity activity) {
            DLog.v(TAG, activity.getClass().getPackage().getName());
        }

        @Override
        public void onActivityStopped(Activity activity) {
            DLog.v(TAG, activity.getClass().getPackage().getName());
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            DLog.v(TAG, activity.getClass().getPackage().getName());
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            DLog.v(TAG, activity.getClass().getPackage().getName());
        }
    };
}
