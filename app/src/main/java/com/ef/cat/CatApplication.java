package com.ef.cat;

import android.app.Application;

import com.ef.cat.injector.components.ApplicationComponent;
import com.ef.cat.injector.components.DaggerApplicationComponent;
import com.ef.cat.injector.modules.ApplicationModule;

public class CatApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}