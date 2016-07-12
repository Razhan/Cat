package com.ef.cat.injector.modules;

import android.app.Application;
import android.content.Context;

import com.ef.cat.ErrorMessageDeterminer;
import com.ef.cat.data.repostory.RestfulService;
import com.ran.delta.domain.exception.ErrorMessageFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return this.application;
    }

    @Provides
    @Singleton
    RestfulService provideRestfulService() {
        return RestfulService.Creator.service();
    }

    @Provides
    @Singleton
    ErrorMessageFactory provideErrorMessageFactory() {
        return new ErrorMessageDeterminer();
    }

}
