package com.ef.cat.presenter;

import android.util.Log;

import com.ef.cat.Constant;
import com.ef.cat.data.model.News;
import com.ef.cat.view.SplashView;
import com.ran.delta.domain.usecase.UseCase;
import com.ran.delta.presentation.presenter.MvpBasePresenter;
import com.ran.delta.utils.FileUtils;

import java.io.File;
import java.io.FileOutputStream;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.ResponseBody;

public class SplashPresenter extends MvpBasePresenter<SplashView> {

    private final UseCase useCase;

    @Inject
    public SplashPresenter(@Named("Initialization") UseCase useCase) {
        this.useCase = useCase;
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
                .useCaseArgs(Constant.APP_FOLDER, Constant.RESOURCE_ZIP_FILE_NAME)
                .onSuccess(bool -> {
                    Log.d("", "");
                })
                .onError(e -> {
                    e.printStackTrace();
                })
                .build();
    }

    public void downloadResourceFile() {
        useCase.new Builder<ResponseBody>()
                .useCaseFunction("download")
                .onSuccess(responseBody -> {
                    writeFileToSD(responseBody);
                })
                .onError(e -> {
                    e.printStackTrace();
                })
                .build();
    }

    private boolean writeFileToSD(ResponseBody responseBody) {
        String path = FileUtils.getFolderPath(Constant.APP_FOLDER);
        File file = new File(path + Constant.RESOURCE_ZIP_FILE_NAME);

        try {
            if (!(file.exists() && file.delete())) {
                return false;
            }

            if (file.createNewFile()) {
                return false;
            }

            FileOutputStream stream = new FileOutputStream(file);
            byte[] buf = responseBody.bytes();
            stream.write(buf);
            stream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void unsubscribe() {
        useCase.unsubscribe();
    }
}
