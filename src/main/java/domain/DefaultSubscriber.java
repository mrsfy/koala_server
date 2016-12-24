package domain;

import rx.Subscriber;

/**
 * Created by mrsfy on 01-Dec-16.
 */
public class DefaultSubscriber<T> extends Subscriber<T> {
    @Override
    public void onCompleted() {

    }

    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void onNext(T t) {

    }
}
