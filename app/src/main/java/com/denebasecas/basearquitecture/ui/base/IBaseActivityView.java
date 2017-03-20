package com.denebasecas.basearquitecture.ui.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;

import com.denebasecas.basearquitecture.utils.permissions.PermissionResult;


/**
 * Created Deneb Chorny (denebchorny@gmail.com)
 */
public interface IBaseActivityView {

    Context getContext();

    FragmentActivity getActivity();

    Activity getAppCompatActivity();

    void onError(String message);

    void showSnackBar(String message);

    void onError(@StringRes int resId);

    void openActivityOnTokenExpire();

    boolean isNetworkConnected();

    void showToast(String s);

    void showToast(@StringRes int s);

    void showLoading();

    void hideLoading();

    void showProgress(String s);

    void showProgress(@StringRes int stringResId);

    void showLoadingFullScreen(@StringRes int titleResId);

    void showLoadingFullScreen(String title, String message);

    void hideLoadingFullScreen();

    void hideProgress();

    void finish();

    Context getApplicationContext();

    boolean hasPermissions(String... permissions);

    void requestPermission(int requestCode, String... permissions);

    boolean hasPermissionsOrRequest(int requestCode, String... permissions);

    void showRationalDialog(PermissionResult permissionResult, int title, int message,
                            int possitiveButton, int negativeButton);

    void hideRationalDialog();

}
