package org.malberquilla.learning.reactive;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;
import java.util.stream.IntStream;

import org.malberquilla.learning.reactive.business.ReactivePublisher;
import org.malberquilla.learning.reactive.business.ReactiveSubscriber;

public class ReactiveStreamsMain {

    public static void main(String[] args) {

        // Publisher
        try (SubmissionPublisher<Integer> publisher = new SubmissionPublisher<>()) {

            // Processor
            Flow.Processor<Integer, Integer> processor = new ReactivePublisher();

            // Subscriber
            Flow.Subscriber<Integer> subscriber = new ReactiveSubscriber();

            publisher.subscribe(processor);
            processor.subscribe(subscriber);

            // The publisher will send 10 numbers
            IntStream.range(0, 10)
                .forEach(item -> {
                    publisher.submit(item);
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
        }
    }
}
