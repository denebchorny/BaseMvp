package com.denebasecas.basearquitecture.ui.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;

import com.denebasecas.basearquitecture.utils.permission.PermissionResult;

/**
 * Created by Deneb Chorny (denebchorny@gmail.com)
 */
public interface IFragmentBaseView {

    void showToast(String message);

    void showToast(int id);

    void showShortToast(String s);

    void showShortToast(@StringRes int s);

    void showProgressDialog(String message);

    void showProgressDialog(@StringRes int stringResId);

    void hideProgress();

    boolean onBackPressed();

    Context getContext();

    FragmentActivity getActivity();

    boolean hasPermissions(String... permissions);

    void requestPermission(int requestCode, String... permissions);

    void requestPermission(Activity activity, int requestCode, String... permissions);

    boolean hasPermissionsOrRequest(int requestCode, String... permissions);

    boolean hasPermissionsOrRequest(Activity activity, int requestCode, String... permissions);

    void showRationalDialog(PermissionResult permissionResult, int title, int message,
                            int positiveButton, int negativeButton);

    void hideRationalDialog();

}
