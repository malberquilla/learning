package org.malberquilla.temperature.converter;

import java.util.Random;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.Flow;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SubmissionPublisher;
import java.util.concurrent.TimeUnit;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TemperatureSensor extends SubmissionPublisher<Double> {

    private final SubmissionPublisher<Double> publisher;
    private final Random random;
    private ScheduledExecutorService scheduledExecutorService;

    public TemperatureSensor() {
        this.publisher = new SubmissionPublisher<>();
        this.random = new Random();
    }

    public Flow.Publisher<Double> asPublisher() {
        return publisher;
    }

    public void start() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService
            .scheduleAtFixedRate(new TemperatureGeneratorTask(), 500, 500, TimeUnit.MILLISECONDS);
    }

    public void stop() {
        publisher.close();
        scheduledExecutorService.shutdown();
        try {
            if (!scheduledExecutorService.awaitTermination(550, TimeUnit.MILLISECONDS)) {
                scheduledExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduledExecutorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    class TemperatureGeneratorTask extends TimerTask {

        @Override
        public void run() {
            double temperature = random.nextDouble() * 100;
            publisher.submit(temperature);
        }
    }
}
