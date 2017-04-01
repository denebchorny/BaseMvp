package com.denebasecas.basearquitecture.utils.permission;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by Deneb Chorny (denebchorny@gmail.com).
 */

public class PermissionResult implements Parcelable {

    //----------------------------------------------------------------------------------------------
    // Variables
    //----------------------------------------------------------------------------------------------

    private int requestCode;

    private boolean isAllGranted;
    private boolean isAllDenied;
    private boolean isAllNeverShowAgain;
    private boolean hasSomeNeverShowAgain;
    private boolean hasSomeGranted;
    private boolean hasSomeDenied;

    private List<String> permissions;
    private List<String> granted;
    private List<String> denied;
    private List<String> shouldShowRequestRationale;

    //----------------------------------------------------------------------------------------------
    // Constructors
    //----------------------------------------------------------------------------------------------

    private PermissionResult(Activity activity, int requestCode, @NonNull String[] permissions,
                             @NonNull int[] grantResults) {
        this.permissions = new ArrayList<>();
        granted = new ArrayList<>();
        denied = new ArrayList<>();
        shouldShowRequestRationale = new ArrayList<>();
        this.requestCode = requestCode;
        this.permissions = Arrays.asList(permissions);

        validate(activity, grantResults);
    }

    public static PermissionResult getResult(Activity activity,
                                             int requestCode,
                                             @NonNull String[] permissions,
                                             @NonNull int[] grantResults) {
        return new PermissionResult(activity, requestCode, permissions, grantResults);
    }

    //----------------------------------------------------------------------------------------------
    // Getter & Setters Methods
    //----------------------------------------------------------------------------------------------

    public int getRequestCode() {
        return requestCode;
    }

    public boolean isAllGranted() {
        return isAllGranted;
    }

    public boolean isAllDenied() {
        return isAllDenied;
    }

    public boolean isAllNeverShowAgain() {
        return isAllNeverShowAgain;
    }

    public boolean hasSomeGranted() {
        return hasSomeGranted;
    }

    public boolean hasSomeDenied() {
        return hasSomeDenied;
    }

    public boolean hasSomeNeverShowAgain() {
        return hasSomeNeverShowAgain;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public List<String> getGranted() {
        return granted;
    }

    public List<String> getDenied() {
        return denied;
    }

    public List<String> getPermissionsShouldShowRationale() {
        return shouldShowRequestRationale;
    }


    //----------------------------------------------------------------------------------------------
    // Methods
    //----------------------------------------------------------------------------------------------

    private void validate(Activity activity, @NonNull int[] grantResults) {
        for (int i = 0; i < permissions.size(); i++) {
            String permission = permissions.get(i);
            int grantResult = grantResults[i];
            if (grantResult == PackageManager.PERMISSION_GRANTED) {
                hasSomeGranted = true;
                granted.add(permission);
            } else {
                hasSomeDenied = true;
                denied.add(permission);

                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                    hasSomeNeverShowAgain = true;
                    shouldShowRequestRationale.add(permission);
                }
            }
        }

        if (this.permissions.size() == granted.size()) {
            isAllGranted = true;
        } else if (this.permissions.size() == denied.size()) {
            isAllDenied = true;
        }
        if (this.permissions.size() == shouldShowRequestRationale.size()) {
            isAllNeverShowAgain = true;
        }
    }

    //----------------------------------------------------------------------------------------------
    // Parcelable Methods
    //----------------------------------------------------------------------------------------------

    protected PermissionResult(Parcel in) {
        requestCode = in.readInt();
        isAllGranted = in.readByte() != 0x00;
        isAllDenied = in.readByte() != 0x00;
        isAllNeverShowAgain = in.readByte() != 0x00;
        hasSomeNeverShowAgain = in.readByte() != 0x00;
        hasSomeGranted = in.readByte() != 0x00;
        hasSomeDenied = in.readByte() != 0x00;
        if (in.readByte() == 0x01) {
            permissions = new ArrayList<String>();
            in.readList(permissions, String.class.getClassLoader());
        } else {
            permissions = null;
        }
        if (in.readByte() == 0x01) {
            granted = new ArrayList<String>();
            in.readList(granted, String.class.getClassLoader());
        } else {
            granted = null;
        }
        if (in.readByte() == 0x01) {
            denied = new ArrayList<String>();
            in.readList(denied, String.class.getClassLoader());
        } else {
            denied = null;
        }
        if (in.readByte() == 0x01) {
            shouldShowRequestRationale = new ArrayList<String>();
            in.readList(shouldShowRequestRationale, String.class.getClassLoader());
        } else {
            shouldShowRequestRationale = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(requestCode);
        dest.writeByte((byte) (isAllGranted ? 0x01 : 0x00));
        dest.writeByte((byte) (isAllDenied ? 0x01 : 0x00));
        dest.writeByte((byte) (isAllNeverShowAgain ? 0x01 : 0x00));
        dest.writeByte((byte) (hasSomeNeverShowAgain ? 0x01 : 0x00));
        dest.writeByte((byte) (hasSomeGranted ? 0x01 : 0x00));
        dest.writeByte((byte) (hasSomeDenied ? 0x01 : 0x00));
        if (permissions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(permissions);
        }
        if (granted == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(granted);
        }
        if (denied == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(denied);
        }
        if (shouldShowRequestRationale == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(shouldShowRequestRationale);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<PermissionResult> CREATOR = new Creator<PermissionResult>() {
        @Override
        public PermissionResult createFromParcel(Parcel in) {
            return new PermissionResult(in);
        }

        @Override
        public PermissionResult[] newArray(int size) {
            return new PermissionResult[size];
        }
    };
}