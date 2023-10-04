package org.malberquilla.temperature.converter;

public class FahrenheitConverter implements TemperatureConverter {
    @Override
    public double convert(double temperature) {
        return (temperature - 32) / 1.8;
    }
}
