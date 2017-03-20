package com.denebasecas.basearquitecture.utils.permissions;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Deneb Chorny on 05-07-2016.
 */
public class PermissionManager {
    private static final String TAG = PermissionManager.class.getSimpleName();

    //----------------------------------------------------------------------------------------------
    // Variables
    //----------------------------------------------------------------------------------------------

    // Loads manifest's permission
    private static List<String> manifestPermissions;

    // To avoid multiple execution of requestPermissions
    private static long mLastPermissionRequestTime = 0;

    //----------------------------------------------------------------------------------------------
    // Permission Result Listener
    //----------------------------------------------------------------------------------------------

    public interface OnPermissionResultListener {
        void onRequestPermissionsResult(final PermissionResult result);
    }

    //----------------------------------------------------------------------------------------------
    // Constants (Group of permissions to be check)
    //----------------------------------------------------------------------------------------------

    public static final String[] PERMISSION_LOCATION
            = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    public static final String[] PERMISSION_AUDIO = new String[]{Manifest.permission.RECORD_AUDIO};

    public static final String[] PERMISSION_VIDEOCALL = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO};

    public static final String[] PERMISSION_CAMERA = new String[]{Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String[] PERMISSION_EXTERNAL_STORAGE
            = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};

    public static final String[] PERMISSION_READ_WRITE_SMS
            = new String[]{Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_SMS};

    //----------------------------------------------------------------------------------------------
    // Context Permission Methods
    //----------------------------------------------------------------------------------------------

    public static boolean hasPermissions(Context context, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context,
                    permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    //----------------------------------------------------------------------------------------------
    // Activity Permission Methods
    //----------------------------------------------------------------------------------------------

    public static boolean hasPermissions(Activity activity, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity,
                    permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasPermissionsOrRequest(@NonNull Activity activity,
                                                  @NonNull int requestCode,
                                                  String... permissions) {

        List<String> denied = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity,
                    permission) != PackageManager.PERMISSION_GRANTED) {
                denied.add(permission);
            }
        }

        if (denied.size() > 0) {
            requestPermission(activity, requestCode, denied.toArray(new String[denied.size()]));
            return false;
        }
        return true;
    }

    public static void requestPermission(@NonNull Activity activity,
                                         int requestCode,
                                         String... permissions) {

        if (!isClickable()) return;

        if (permissions == null || permissions.length == 0) {
            exception(activity,
                    "You have to pass at least one permission to do a request permission");
            return;
        }

        if (!isPermissionDeclared(activity, permissions)) return;

        ActivityCompat.requestPermissions(activity,
                permissions,
                requestCode);
    }

    public static void onRequestPermissionsResult(@NonNull Activity activity,
                                                  int requestCode,
                                                  @NonNull String[] permissions,
                                                  @NonNull int[] grantResults,
                                                  @NonNull OnPermissionResultListener listener) {

        listener.onRequestPermissionsResult(PermissionResult.getResult(activity,
                requestCode, permissions, grantResults));
    }

    public static PermissionResult onRequestPermissionsResult(@NonNull Activity activity,
                                                              int requestCode,
                                                              @NonNull String[] permissions,
                                                              @NonNull int[] grantResults) {

        return PermissionResult.getResult(activity, requestCode, permissions, grantResults);
    }

    //----------------------------------------------------------------------------------------------
    // Fragment Permission Methods
    //----------------------------------------------------------------------------------------------

    public static boolean hasPermissions(Fragment fragment, String... permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(fragment.getContext(),
                    permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static boolean hasPermissionsOrRequest(Fragment fragment, int requestCode,
                                                  String... permissions) {

        if (!isClickable()) return false;

        List<String> denied = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(fragment.getContext(),
                    permission) != PackageManager.PERMISSION_GRANTED) {
                denied.add(permission);
            }
        }

        if (denied.size() > 0) {
            fragment.requestPermissions(denied.toArray(new String[denied.size()]), requestCode);
            return false;
        }
        return true;
    }

    public static void requestPermission(@NonNull Fragment fragment, int requestCode,
                                         String... permissions) {

        if (!isClickable()) return;

        if (permissions == null || permissions.length == 0) {
            exception(fragment.getContext(), "You have to pass at least one permission to do a request permission");
            return;
        }

        if (!isPermissionDeclared(fragment.getContext(), permissions)) return;

        fragment.requestPermissions(permissions, requestCode);
    }

    public static void onRequestPermissionsResult(@NonNull Fragment fragment, int requestCode,
                                                  @NonNull String[] permissions,
                                                  @NonNull int[] grantResults,
                                                  @NonNull OnPermissionResultListener listener) {

        listener.onRequestPermissionsResult(PermissionResult.getResult(fragment.getActivity(),
                requestCode, permissions, grantResults));
    }

    public static PermissionResult onRequestPermissionsResult(@NonNull Fragment fragment, int requestCode,
                                                              @NonNull String[] permissions,
                                                              @NonNull int[] grantResults) {

        return PermissionResult.getResult(fragment.getActivity(), requestCode, permissions, grantResults);
    }

    //----------------------------------------------------------------------------------------------
    // Permissions validation Methods
    //----------------------------------------------------------------------------------------------

    private static boolean existManifestPermission(Context context, String permission) {
        return initManifestPermissions(context) && manifestPermissions.contains(permission);
    }

    private static boolean initManifestPermissions(Context context) {
        if (manifestPermissions == null) {
            try {
                PackageInfo packageInfo = context
                        .getPackageManager()
                        .getPackageInfo(context.getPackageName()
                                , PackageManager.GET_PERMISSIONS);

                if (packageInfo != null) {
                    String[] requestedPermissions = packageInfo.requestedPermissions;
                    if (requestedPermissions != null && requestedPermissions.length > 0) {
                        manifestPermissions = new ArrayList<>();
                        manifestPermissions = Arrays.asList(requestedPermissions);
                        return true;
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.e(context.getClass().getSimpleName(), "Error: " + Log.getStackTraceString(e));
            }
            return false;
        }
        return true;
    }

    public static boolean isPermissionDeclared(@NonNull Context context, @NonNull String[] permissions) {
        if (permissions.length == 0) return false;

        String pending = "";
        int totalPending = 0;
        for (String permission : permissions) {
            if (TextUtils.isEmpty(permission)) {
                exception(context, "An empty string is not a valid permission");
                return false;
            }
            if (!existManifestPermission(context, permission)) {
                if (totalPending == 0) {
                    pending = permission;
                } else {
                    pending = String.format("%s, %s", pending, permission);
                }
                totalPending++;
            }
        }

        if (totalPending > 0) {
            exception(context
                    , String.format("%s: %s."
                            , "Before you request permissions, you must declare in the manifest"
                            , pending));
            return false;
        }
        return true;
    }

    private static void exception(Context context, String message) {
        if (context != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            Log.e(context.getClass().getSimpleName(), message);
        }
        throw new IllegalArgumentException(message);
    }


    private static boolean isClickable() {
        if (System.currentTimeMillis() - mLastPermissionRequestTime < 1000) {
            return false;
        }
        mLastPermissionRequestTime = System.currentTimeMillis();
        return true;
    }
}
