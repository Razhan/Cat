package com.ran.delta.presentation.ui.view;

import android.content.Context;

public interface IBaseView {

    void showProgress(boolean flag, String message);

    void showProgress(String message);

    void showProgress();

    void showProgress(boolean flag);

    void hideProgress();

    void showToast(int resId);

    void showToast(String msg);

    Context getContext();
}