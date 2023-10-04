package org.malberquilla.temperature.converter;

public class CelsiusConverter implements TemperatureConverter {
    @Override
    public double convert(double temperature) {
        return temperature * 1.8 + 32;
    }
}
