package com.ran.delta.presentation;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {

    public static final String STATUS_BAR = "set_status_bar";
    public static final String FULL_SCREEN = "set_full_screen";
    public static final String ROTATE_SCREEN = "set_rotate_screen";
    public static final String BACK_EXIT = "set_back_exit";

    protected final String TAG = this.getClass().getSimpleName();

    private boolean isTranslucentStatusBar = false;
    private boolean isAllowFullScreen = true;
    private boolean isAllowScreenRotate = false;
    private boolean isDoubleBackExit = false;

    private boolean doubleBackToExitPressedOnce = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initParms(getIntent().getExtras());

        if (isAllowFullScreen) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (isTranslucentStatusBar) {
            translucentStatusBar();
        }

        setContentView(bindLayout());
        ButterKnife.bind(this);

        if (!isAllowScreenRotate) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        initView();
    }

    private void translucentStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    public void initParms(Bundle bundle) {
        if (bundle == null) {
            return;
        }

        isTranslucentStatusBar = bundle.getBoolean(STATUS_BAR, false);
        isAllowFullScreen = bundle.getBoolean(FULL_SCREEN, true);
        isAllowScreenRotate = bundle.getBoolean(ROTATE_SCREEN, false);
        isDoubleBackExit = bundle.getBoolean(BACK_EXIT, false);
    }

    public abstract int bindLayout();

    public abstract void initView();

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    public void setDoubleBackExit(boolean isBackExit) {
        this.isDoubleBackExit = isBackExit;
    }

    @Override
    public void onBackPressed() {

        if (!isDoubleBackExit && doubleBackToExitPressedOnce) {
            finish();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    public void showMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
