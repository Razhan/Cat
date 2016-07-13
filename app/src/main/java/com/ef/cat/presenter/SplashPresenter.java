package com.ef.cat.presenter;

import android.util.Log;

import com.ef.cat.Constant;
import com.ef.cat.data.model.News;
import com.ef.cat.view.SplashView;
import com.ran.delta.domain.usecase.UseCase;
import com.ran.delta.presentation.presenter.MvpBasePresenter;

import javax.inject.Inject;
import javax.inject.Named;

public class SplashPresenter extends MvpBasePresenter<SplashView> {

    private final UseCase useCase;

    @Inject
    public SplashPresenter(@Named("Initialization") UseCase newsUseCase) {
        this.useCase = newsUseCase;
    }

    public void getNews() {
        useCase.new Builder<News>()
                .useCaseFunction("getNews")
                .onSuccess(news -> {
                    news.getCount();
                })
                .onError(e -> {
                    e.printStackTrace();
                })
                .build();
    }

    public void unzip() {
        useCase.new Builder<Boolean>()
                .useCaseArgs(Constant.APP_FOLDER, "AWS.zip", Constant.UNZIP_FOLDER)
                .onSuccess(bool -> {
                    Log.d("", "");
                })
                .onError(e -> {
                    e.printStackTrace();
                })
                .build();
    }

    @Override
    protected void unsubscribe() {
        useCase.unsubscribe();
    }
}