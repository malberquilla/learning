package org.malberquilla.temperature.converter;

import java.text.DecimalFormat;
import java.util.concurrent.Flow;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class TemperatureDisplay implements Flow.Subscriber<Double> {

    private final TemperatureConverter converter;

    private Flow.Subscription subscription;

    public TemperatureDisplay(TemperatureConverter converter) {
        this.converter = converter;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    @Override
    public void onNext(Double temperature) {
        double converted = converter.convert(temperature);
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        log.info("{}{}", decimalFormat.format(converted),
            converter instanceof CelsiusConverter ? "F" : "C");
        this.subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error(throwable);
    }

    @Override
    public void onComplete() {
        log.info("Done");
    }
}
