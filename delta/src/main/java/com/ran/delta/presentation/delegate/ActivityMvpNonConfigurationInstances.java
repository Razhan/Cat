package com.ran.delta.presentation.delegate;

import com.ran.delta.presentation.MvpPresenter;
import com.ran.delta.view.MvpView;

class ActivityMvpNonConfigurationInstances<V extends MvpView, P extends MvpPresenter<V>> {

    P presenter;

    Object configurationInstance;

    ActivityMvpNonConfigurationInstances(P presenter, Object configurationInstance) {
        this.presenter = presenter;
        this.configurationInstance = configurationInstance;
    }
}
