package com.ran.delta.domain.usecase;

import java.lang.reflect.Method;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.internal.util.InternalObservableUtils;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public abstract class UseCase<T> {

    private String methodName;
    private Object[] methodArgs;

    private Action1<T> onSuccessCallback;
    private Action1<Throwable> onErrorCallback;
    private Action0 onCompleteCallback;

    private CompositeSubscription compositeSubscription;

    public UseCase() {
        compositeSubscription = new CompositeSubscription();
        reset();
    }

    private void reset() {
        methodArgs = null;
        methodName = null;
        onSuccessCallback = Actions.empty();
        onErrorCallback = InternalObservableUtils.ERROR_NOT_IMPLEMENTED;
        onCompleteCallback = Actions.empty();
    }

    @SuppressWarnings("unchecked")
    protected Observable<T> buildUseCaseObservable() {
        Method methodToInvoke = UseCaseFilter.filter(this, methodName, methodArgs);
        try {
            return (Observable<T>) methodToInvoke.invoke(this, methodArgs);
        } catch (Exception e) {
            return Observable.empty();
        }
    }

    public void execute() {
        Subscription subscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccessCallback, onErrorCallback, onCompleteCallback);

        compositeSubscription.add(subscription);
    }

    public void unsubscribe() {
        if (!compositeSubscription.isUnsubscribed()) {
            compositeSubscription.unsubscribe();
        }
    }

    public Builder builder() {
        return new Builder();
    }

    public final class Builder {
        private Builder() {
            reset();
        }

        public Builder useCaseFunction(String name) {
            methodName = name;
            return this;
        }

        public Builder useCaseArgs(Object... args) {
            methodArgs = args;
            return this;
        }

        public Builder onSuccess(Action1<T> successCallback) {
            if (successCallback == null) {
                throw new IllegalArgumentException(
                        "OnSuccessCallback is null. You can not invoke it with" + " null callback.");
            }

            UseCase.this.onSuccessCallback = successCallback;
            return this;
        }

        public Builder onComplete(Action0 completeCallback) {
            UseCase.this.onCompleteCallback = completeCallback;
            return this;
        }

        public Builder onError(Action1<Throwable> errorCallback) {
            UseCase.this.onErrorCallback = errorCallback;
            return this;
        }

        public void build() {
            execute();
        }
    }

}
