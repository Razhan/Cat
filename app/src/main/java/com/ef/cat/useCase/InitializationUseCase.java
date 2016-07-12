package com.ef.cat.usecase;

import com.ef.cat.data.model.News;
import com.ef.cat.data.repostory.CatRepository;
import com.ran.delta.domain.annotation.UseCaseFunction;
import com.ran.delta.domain.usecase.UseCase;
import com.ran.delta.utils.MiscUtils;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

public class InitializationUseCase extends UseCase<News> {

    private CatRepository catRepository;

    @Inject
    public InitializationUseCase(CatRepository repository) {
        this.catRepository = repository;
    }

    @UseCaseFunction(name = "getNews")
    public Observable<News> getNews() {
        return catRepository.getRestfulService().getNews();
    }

    @UseCaseFunction(name = "unzip")
    public Observable<Boolean> unzip(String folder, String zipFile, String targetDirectory) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    MiscUtils.unzip(folder, zipFile, targetDirectory);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
