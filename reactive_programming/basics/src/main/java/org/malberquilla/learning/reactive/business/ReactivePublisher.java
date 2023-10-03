package org.malberquilla.learning.reactive.business;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class ReactivePublisher extends SubmissionPublisher<Integer>
    implements Flow.Processor<Integer, Integer> {

    private Flow.Subscription subscription;

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    @Override
    public void onNext(Integer item) {
        this.submit(item);
        log.info("MSG published: {}", item);
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable error) {
        log.error(error);
    }

    @Override
    public void onComplete() {
        log.info("The publisher is completed");
    }
}
