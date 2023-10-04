package org.malberquilla.temperature.converter;

public class Main {
    public static void main(String[] args) {
        try (TemperatureSensor sensor = new TemperatureSensor()) {
            sensor.start();
            sensor.asPublisher().subscribe(new TemperatureDisplay(new CelsiusConverter()));
            sensor.asPublisher().subscribe(new TemperatureDisplay(new FahrenheitConverter()));
        }
    }
}
