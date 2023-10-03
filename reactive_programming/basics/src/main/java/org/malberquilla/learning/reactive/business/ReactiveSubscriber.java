package org.malberquilla.learning.reactive.business;

import java.util.concurrent.Flow;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ReactiveSubscriber implements Flow.Subscriber<Integer> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(Integer o) {
        log.info("MSG received: {}", o);
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable error) {
        log.error(error);
    }

    @Override
    public void onComplete() {
        log.info("Subscriber complete");
    }
}
