package com.ef.cat.ui.activity;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.ef.cat.CatApplication;
import com.ef.cat.R;
import com.ef.cat.injector.components.DaggerInitializationComponent;
import com.ef.cat.injector.components.InitializationComponent;
import com.ef.cat.presenter.SplashPresenter;
import com.ef.cat.view.SplashView;
import com.ran.delta.presentation.ui.activity.MvpActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class SplashActivity extends MvpActivity<SplashView, SplashPresenter> implements SplashView {

    @BindView(R.id.splash_bottom_bar)
    LinearLayout bottomBar;

    //    @Inject SystemText systemText;
    private InitializationComponent initComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setAllowFullScreen(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initDependencyInjector() {
        initComponent = DaggerInitializationComponent
                .builder()
                .applicationComponent(((CatApplication) getApplication()).getApplicationComponent())
                .build();
        initComponent.inject(this);
    }

    @NonNull
    @Override
    public SplashPresenter createPresenter() {
        return initComponent.presenter();
    }

    @OnClick(R.id.tv)
    public void onClick() {
        presenter.unzip();
//        MiscUtils.getSystemLanguage(this);
//        String ss = systemText.getSystemText("app_preview_name");

//        presenter.downloadResourceFile();
    }

    private void showBottomBar() {
        bottomBar.setVisibility(View.VISIBLE);
        ObjectAnimator animator = ObjectAnimator.ofFloat(bottomBar, "translationY", bottomBar.getHeight(), 0);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.setDuration(300).start();
    }

}
