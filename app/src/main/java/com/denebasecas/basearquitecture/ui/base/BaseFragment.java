package com.denebasecas.basearquitecture.ui.base;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;

import com.denebasecas.basearquitecture.utils.permissions.PermissionManager;
import com.denebasecas.basearquitecture.utils.permissions.PermissionResult;

/**
 * Created by Deneb Chorny (denebchorny@gmail.com)
 */
public abstract class BaseFragment extends Fragment implements IFragmentBaseView,
        PermissionManager.OnPermissionResultListener {

    private IBaseActivityView mListener;

    @Override
    public void showToast(String message) {
        mListener.showToast(message);
    }

    @Override
    public void showToast(int id) {
        mListener.showToast(id);
    }

    @Override
    public void showProgressDialog(String message) {
        mListener.showProgress(message);
    }

    @Override
    public void showProgressDialog(@StringRes int stringResId) {
        mListener.showProgress(stringResId);
    }

    @Override
    public void hideProgress() {
        mListener.hideProgress();
    }

    @Override
    public void showRationalDialog(PermissionResult permissionResult, int title, int message,
                                   int possitiveButton, int negativeButton) {
        mListener.showRationalDialog(permissionResult, title, message, possitiveButton, negativeButton);
    }

    @Override
    public void hideRationalDialog() {
        mListener.hideRationalDialog();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IBaseActivityView) {
            mListener = (IBaseActivityView) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroy() {
        hideRationalDialog();
        super.onDestroy();
    }

    @Override
    public boolean onBackPressed() {
        return true;
    }


    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------
    // Permission Mehtods
    // ---------------------------------------------------------------------------------------------
    // ---------------------------------------------------------------------------------------------

    @Override
    public boolean hasPermissions(String... permissions) {
        return PermissionManager.hasPermissions(this, permissions);
    }

    @Override
    public void requestPermission(int requestCode, String... permissions) {
        PermissionManager.requestPermission(this, requestCode, permissions);
    }

    @Override
    public void requestPermission(Activity activity, int requestCode, String... permissions) {
        PermissionManager.requestPermission(activity, requestCode, permissions);
    }

    @Override
    public boolean hasPermissionsOrRequest(int requestCode, String... permissions) {
        return PermissionManager.hasPermissionsOrRequest(this, requestCode, permissions);
    }

    @Override
    public boolean hasPermissionsOrRequest(Activity activity, int requestCode, String... permissions) {
        return PermissionManager.hasPermissionsOrRequest(activity, requestCode, permissions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults, this);
    }

    @Override
    public void onRequestPermissionsResult(final PermissionResult result) {

    }
}
