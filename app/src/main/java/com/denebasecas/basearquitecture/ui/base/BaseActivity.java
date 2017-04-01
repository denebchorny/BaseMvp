package com.denebasecas.basearquitecture.ui.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.denebasecas.basearquitecture.R;
import com.denebasecas.basearquitecture.utils.KeyboardUtils;
import com.denebasecas.basearquitecture.utils.NetworkUtils;
import com.denebasecas.basearquitecture.utils.permission.PermissionManager;
import com.denebasecas.basearquitecture.utils.permission.PermissionResult;

import butterknife.Unbinder;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


/**
 * Created Deneb Chorny (denebchorny@gmail.com)
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseActivityView,
        PermissionManager.OnPermissionResultListener {

    private static final String TAG = BaseActivity.class.getSimpleName();

    // ---------------------------------------------------------------------------------------------
    // Variables
    // ---------------------------------------------------------------------------------------------

    private boolean needShowDialog;
    private ProgressDialog mProgressDialog;

    private Unbinder mUnBinder;

    // ---------------------------------------------------------------------------------------------
    // Lifecycle
    // ---------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onDestroy() {
        hideProgress();
        hideRationalDialog();

        if (mUnBinder != null) {
            mUnBinder.unbind();
        }
        super.onDestroy();
    }

    // ---------------------------------------------------------------------------------------------
    // Contexts
    // ---------------------------------------------------------------------------------------------

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public FragmentActivity getActivity() {
        return this;
    }

    @Override
    public Activity getAppCompatActivity() {
        return this;
    }

    // ---------------------------------------------------------------------------------------------
    // Dialogs & Messages
    // ---------------------------------------------------------------------------------------------

    @Override
    public void showToast(final String s) {
        if(getActivity() == null) return;
        runOnUiThread(
                new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BaseActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void showToast(@StringRes final int s) {
        if(getActivity() == null) return;
        runOnUiThread
                (new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(BaseActivity.this, s, Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void showRationalDialog(final PermissionResult permissionResult, final int title, final int message,
                                   final int possitiveButton, final int negativeButton) {
        /*hideRationalDialog();

        if (getActivity() != null) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mRationalDialog = RationalDialog.newInstance(getContext(), permissionResult,
                            title, message, possitiveButton, negativeButton);

                    needShowDialog = true;
                }
            });
        }*/
    }

    @Override
    public void hideRationalDialog() {
        /*if (mRationalDialog != null && mRationalDialog.isShowing()) {
            mRationalDialog.dismiss();
            mRationalDialog = null;
        }
        needShowDialog = false;*/
    }

    @Override
    public void showLoading() {
        hideLoading();
        //mProgressDialog = CommonUtils.showLoadingDialog(this);
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public void onError(String message) {
        if (!TextUtils.isEmpty(message)) {
            showSnackBar(message);
        } else {
            showSnackBar(getString(R.string.all_error));
        }
    }

    @Override
    public void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_SHORT);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView
                .findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(ContextCompat.getColor(this, R.color.white));
        snackbar.show();
    }

    @Override
    public void onError(@StringRes int resId) {
        onError(getString(resId));
    }

    //----------------------------------------------------------------------------------------------
    // Methods
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean isNetworkConnected() {
        return NetworkUtils.isNetworkConnected(getApplicationContext());
    }

    public void hideKeyboard() {
        KeyboardUtils.hideSoftInput(this);
    }

    @Override
    public void openActivityOnTokenExpire() {
       // startActivity(LoginActivity.getStartIntent(this));
        finish();
    }

    public void setUnBinder(Unbinder unBinder) {
        mUnBinder = unBinder;
    }

    //----------------------------------------------------------------------------------------------
    // Permission Methods
    //----------------------------------------------------------------------------------------------

    @Override
    public boolean hasPermissions(String... permissions) {
        return PermissionManager.hasPermissions(this, permissions);
    }

    @Override
    public void requestPermission(int requestCode, String... permissions) {
        PermissionManager.requestPermission(this, requestCode, permissions);
    }

    @Override
    public boolean hasPermissionsOrRequest(int requestCode, String... permissions) {
        return PermissionManager.hasPermissionsOrRequest(this, requestCode, permissions);
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
