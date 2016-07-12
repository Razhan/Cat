package com.ef.cat.ui.activity;

import android.support.annotation.NonNull;

import com.ef.cat.CatApplication;
import com.ef.cat.Constant;
import com.ef.cat.R;
import com.ef.cat.injector.components.DaggerInitializationComponent;
import com.ef.cat.injector.components.InitializationComponent;
import com.ef.cat.presenter.SplashPresenter;
import com.ef.cat.view.SplashView;
import com.ran.delta.presentation.ui.activity.MvpActivity;
import com.ran.delta.utils.MiscUtils;

import butterknife.OnClick;

public class SplashActivity extends MvpActivity<SplashView, SplashPresenter> implements SplashView {

    private InitializationComponent initComponent;

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

    @Override
    public int bindLayout() {
        return R.layout.activity_splash;
    }


    @OnClick(R.id.tv)
    public void onClick() {
//        presenter.getNews();
        try {
            MiscUtils.unzip(Constant.FOLDER, "hub4wsj_sc_8k.zip", "unzip");
        } catch (Exception e) {

        }

    }
}
